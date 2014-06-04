package nl.stefankohler.stash.badgr.listener;

import nl.stefankohler.stash.badgr.AchievementManager;
import nl.stefankohler.stash.badgr.StashUserMetaDataService;
import nl.stefankohler.stash.badgr.achievements.Achievement;
import nl.stefankohler.stash.badgr.achievements.BlindCommitter;
import nl.stefankohler.stash.badgr.achievements.PushMaster;
import nl.stefankohler.stash.badgr.model.StashUserMetaData;

import com.atlassian.event.api.EventListener;
import com.atlassian.stash.content.Changeset;
import com.atlassian.stash.event.RepositoryEvent;
import com.atlassian.stash.event.RepositoryPullEvent;
import com.atlassian.stash.event.RepositoryPushEvent;
import com.atlassian.stash.commit.CommitService;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.SecurityService;
import com.atlassian.stash.util.Operation;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequestImpl;

/**
 * 
 * @author Stefan Kohler
 * @since 1.1
 */
public class RepositoryEventListener {

    private final AchievementManager achievementManager;
    private final CommitService commitService;
    private final SecurityService securityService;
    private final StashUserMetaDataService metaDataService;

    public RepositoryEventListener(AchievementManager achievementManager, CommitService commitService, SecurityService securityService,
            StashUserMetaDataService metaDataService) {
        this.achievementManager = achievementManager;
        this.commitService = commitService;
        this.securityService = securityService;
        this.metaDataService = metaDataService;
    }

    /**
     * @param event The event to listen for.
     */
    @EventListener
    public void onRepositoryPushed(RepositoryPushEvent event) {
        if (event.getUser() == null) {
            return;
        }
        metaDataService.store(event.getUser(), StashUserMetaData.LAST_PUSH_KEY, event.getDate().toString());

        // don't loop over EVENT type achievements but trigger them one-by-one. 
        // Hard to say atm which event should trigger the achievement.
        checkAchievement(achievementManager.getAchievement(PushMaster.class), event);
        checkAchievement(achievementManager.getAchievement(BlindCommitter.class), event);
    }

    /**
     * @param event The event to listen for.
     */
    @EventListener
    public void onRepositoryPulled(RepositoryPullEvent event) {
        if (event.getUser() == null) {
            return;
        }
        metaDataService.store(event.getUser(), StashUserMetaData.LAST_PULL_KEY, event.getDate().toString());
    }

    private void checkAchievement(Achievement achievement, RepositoryEvent event) {
        if (achievement.isConditionMet(event)) {
            Integer count = achievementManager.addCounting(achievement, event.getUser(), 1);
            if (count.equals(achievement.getCountingLimit())) {
                grantAchievment(achievement, event);
            }
        }
    }

    private void grantAchievment(Achievement achievement, RepositoryEvent event) {
        Changeset changeset = getLatestChangeset(event.getRepository());
        if (changeset != null) {
            achievementManager.grantAchievement(achievement, event.getUser(), changeset);
        }
    }

    private Changeset getLatestChangeset(final Repository repository) {
        Page<Changeset> changesets = securityService.doWithPermission("Retrieve last changeset in repository for badgr", Permission.REPO_READ,
                new Operation<Page<Changeset>, RuntimeException>() {
                    public Page<Changeset> perform() {
                        return commitService.getChangesets(repository, null, null, new PageRequestImpl(0, 1));
                    }
                });
        for (Changeset changeset : changesets.getValues()) {
            return changeset;
        }
        return null;
    }
}