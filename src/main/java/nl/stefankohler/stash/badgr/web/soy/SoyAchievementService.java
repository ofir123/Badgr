package nl.stefankohler.stash.badgr.web.soy;

import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
import com.atlassian.stash.commit.CommitService;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.repository.RepositoryService;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
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
    private final CommitService commitService;
    private final RepositoryService repositoryService;
    private final UserService userService;

    private final WebResourceUrlProvider urlProvider;

    @Autowired
    public SoyAchievementService(AchievementManager achievementManager, ActiveObjects ao, CommitService commitService, RepositoryService repositoryService, UserService userService, WebResourceUrlProvider urlProvider) {
        this.achievementManager = achievementManager;
        this.ao = ao;
        this.commitService = commitService;
        this.repositoryService = repositoryService;
        this.userService = userService;
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
    
    public Page<? extends Achievement> findAllSoyAchievements(@Nullable PageRequest pageRequest) {
        List<Achievement> achievements = achievementManager.getAchievements();
        
        final PageRequest limitedPageRequest = pageRequest == null ? new PageRequestImpl(0, achievements.size()) : pageRequest
                .buildRestrictedPageRequest(achievements.size());
        return (Page<? extends Achievement>) PageUtils.createPage(achievements, limitedPageRequest);
    }
    

    public Page<? extends SoyAchievement> findEmailsForAchievement(@Nonnull final String achievementCode, 
        @Nullable PageRequest pageRequest) {
            
        checkNotNull(achievementCode, "achievementCode must not be null");
        
        Achievement achievement = achievementManager.getAchievement(achievementCode);
        
        // sparse achievement selects full values, including email
        Query query = Query.select().where("CODE = ?", achievement.getCode());
        AoAchievement[] aoAchievements = ao.find(AoAchievement.class, query);
        
        List<SoyAchievement> achievers = new ArrayList<SoyAchievement>(aoAchievements.length);
        for (AoAchievement aoAchievement : aoAchievements) {
            
            String email = aoAchievement.getEmail();
            StashUser user = userService.findUserByNameOrEmail(email);
            if (user == null) {
                // user not in the system anymore, fell off LDAP I guess
                continue;
            }
            
            Repository repository = repositoryService.getById(aoAchievement.getRepository());
            
            Changeset changeset = null;
            if (repository == null) {
                changeset = new DummyChangeset(aoAchievement.getChangesetId());
            } else {
                changeset = commitService.getChangeset(repository, aoAchievement.getChangesetId());
            }
            
            AoCount aoCount = achievementManager.getCounting(achievement, user);
            Integer count = (aoCount == null) ? 0 : aoCount.getAmount();
            
            SoyAchievement soyAchievement = new SoyAchievement(achievement, changeset, count, repository, urlProvider);
            soyAchievement.setUserEmail(email);
            achievers.add(soyAchievement);
        }
        
        PageRequest limitedPageRequest = null;
        if (pageRequest == null) {
            limitedPageRequest = new PageRequestImpl(0, achievers.size());
        } else {
            limitedPageRequest = pageRequest.buildRestrictedPageRequest(achievers.size());
        }
        
        return (Page<? extends SoyAchievement>) PageUtils.createPage(achievers, limitedPageRequest);
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
                changeset = commitService.getChangeset(repository, aoAchievements[0].getChangesetId());
            }
        }

        AoCount aoCount = achievementManager.getCounting(achievement, user);
        Integer count = (aoCount == null) ? 0 : aoCount.getAmount();

        return new SoyAchievement(achievement, changeset, count, repository, urlProvider);
    }

    
}