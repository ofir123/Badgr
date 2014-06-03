package nl.stefankohler.stash.badgr;

import java.util.List;

import nl.stefankohler.stash.badgr.achievements.Achievement;
import nl.stefankohler.stash.badgr.model.AoCount;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.stash.content.Changeset;
import com.atlassian.stash.user.Person;

/**
 * The AchievementManager registers the available Achievements
 * and controls the access to those Achievements. <br />
 * Granting and Counting should also be handled via the AchievementManager.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Transactional
public interface AchievementManager {

    /**
     * Register the achievement to the AchievementManager.
     * @param achievement Any achievement that implemented the {@link Achievement} interface.
     */
    void register(Achievement achievement);

    /**
     * @param code An unique Achievement Code
     * @return Returns a single Achievement based upon Code, return <code>null</code> if
     *         achievement can't be found.
     */
    Achievement getAchievement(String code);

    /**
     * @param clazz the achievement class to get.
     * @return Returns a single Achievement based upon class, return <code>null</code> if
     *         achievement isn't registered.
     */
    Achievement getAchievement(Class<? extends Achievement> clazz);

    /**
     * @return an ummuteablelist with all {@link Achievement}.
     */
    List<Achievement> getAchievements();

    /**
     * @param type AchievementType to filter the achievements on.
     * @return an ummuteablelist with the {@link Achievement} of the given type.
     */
    List<Achievement> getAchievements(Achievement.AchievementType type);

    /**
     * @param email The email adres of the user who has been granted the achievements
     * @return an ummuteablelist with {@link Achievement} achieved by the user
     *         identified with the given email address.
     */
    List<Achievement> getAchievementsForEmail(String email);

    /**
     * Grant the achievement to the email address of the committer, assuming the commiter
     * is a known user in Stash.
     * 
     * @param achievement Achievement to grant
     * @param person Person to grant the achievement to
     * @param changeset Changeset which triggered the achievement.
     */
    void grantAchievement(Achievement achievement, Person person, Changeset changeset);

    /**
     * Adds a certain amount to the Achievement counter to keep track how many times an
     * achievements condition is met. Counting can be used for achievements where the
     * condition should be met a couple of times before granting the Achievement to the
     * user.
     * 
     * @param achievement The Achievement to track the counting for
     * @param person The Person who triggered the Achievement
     * @param add the amount to add to the Counter, defaults to 1
     * @return The result of adding the amount to the counter.
     */
    Integer addCounting(Achievement achievement, Person person, Integer add);

    /**
     * @param achievement the {@link Achievement} to get the counting for.
     * @param person the Person to get the counting for
     * @return the AoCount if any, <code>null</code> otherwise.
     */
    AoCount getCounting(Achievement achievement, Person person);

}
