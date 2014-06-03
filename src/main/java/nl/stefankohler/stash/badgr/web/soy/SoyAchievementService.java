package nl.stefankohler.stash.badgr.web.soy;

import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.java.ao.Query;
import nl.stefankohler.stash.badgr.AchievementManager;
import nl.stefankohler.stash.badgr.achievements.Achievement;
import nl.stefankohler.stash.badgr.model.AoAchievement;
import nl.stefankohler.stash.badgr.model.AoCount;
import nl.stefankohler.stash.badgr.model.DummyChangeset;

import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.webresource.WebResourceUrlProvider;
import com.atlassian.stash.content.Changeset;
import com.atlassian.stash.history.HistoryService;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.repository.RepositoryService;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;
import com.atlassian.stash.util.PageRequestImpl;
import com.atlassian.stash.util.PageUtils;

/**
 * SoyAchievementService for getting Achievements for displaying.<br>
 * SoyAchievements are a combination between a regular {@link Achievement} and if the Achievement is achieved. <br>
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
public class SoyAchievementService {

    private final AchievementManager achievementManager;
    private final ActiveObjects ao;
    private final HistoryService historyService;
    private final RepositoryService repositoryService;

    private final WebResourceUrlProvider urlProvider;

    @Autowired
    public SoyAchievementService(AchievementManager achievementManager, ActiveObjects ao, HistoryService historyService, RepositoryService repositoryService,
            WebResourceUrlProvider urlProvider) {
        this.achievementManager = achievementManager;
        this.ao = ao;
        this.historyService = historyService;
        this.repositoryService = repositoryService;
        this.urlProvider = urlProvider;
    }

    /**
     * Returns all Achievements in Badgr for displaying purposes. If an Achievement
     * is earned the changeset which triggered the event is not null.
     * @param user the user
     * @param pageRequest the page request
     * @return a page of achievements
     */
    public Page<? extends SoyAchievement> findSoyAchievementsForUser(@Nonnull final StashUser user, @Nullable PageRequest pageRequest) {
        checkNotNull(user, "User must not be null");
        checkNotNull(user.getEmailAddress(), "User email must not be null");

        List<Achievement> achievements = achievementManager.getAchievements();
        List<SoyAchievement> soyAchievements = new ArrayList<SoyAchievement>();
        for (Achievement achievement : achievements) {
            SoyAchievement soyAchievement = buildSoyAchievement(achievement, user);
            soyAchievements.add(soyAchievement);
        }

        final PageRequest limitedPageRequest = pageRequest == null ? new PageRequestImpl(0, achievements.size()) : pageRequest
                .buildRestrictedPageRequest(achievements.size());
        return (Page<? extends SoyAchievement>) PageUtils.createPage(soyAchievements, limitedPageRequest);
    }

    private SoyAchievement buildSoyAchievement(Achievement achievement, StashUser user) {
        Changeset changeset = null;
        Repository repository = null;

        Query query = Query.select().where("CODE = ? AND EMAIL = ?", achievement.getCode(), user.getEmailAddress()).limit(1);
        AoAchievement[] aoAchievements = ao.find(AoAchievement.class, query);

        if (aoAchievements.length > 0) {
            repository = repositoryService.getById(aoAchievements[0].getRepository());
            // reposity can be null (removed from stash), return dummy changeset
            if (repository == null) {
                changeset = new DummyChangeset(aoAchievements[0].getChangesetId());
            } else {
                changeset = historyService.getChangeset(repository, aoAchievements[0].getChangesetId());
            }
        }

        AoCount aoCount = achievementManager.getCounting(achievement, user);
        Integer count = (aoCount == null) ? 0 : aoCount.getAmount();

        return new SoyAchievement(achievement, changeset, count, repository, urlProvider);
    }
}
