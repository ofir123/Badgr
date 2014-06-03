package nl.stefankohler.stash.badgr.achievements;

/**
 * Achievement interface.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
public interface Achievement {

    /**
     * AchievementType are being used by the indexer
     * to specify which object the Achievement need to
     * check for it's condition.
     */
    enum AchievementType {
        CHANGE, CHANGESET, EVENT
    }

    /**
     * @return Should return a unique code, used for mapping achieved Achievements
     *         to the original achievements.
     */
    String getCode();

    /**
     * @return Should return the name key for this Achievement. The key is used
     *         for internationalization.
     */
    String getName();

    /**
     * @return Should return the description key for this Achievement. The key is
     *         used for internationalization.
     */
    String getDescription();

    /**
     * @return the filename of the badge to display this Achievement.
     *         The file should be in the <code>src/resources/badges</code> directory.
     */
    String getBadge();

    /**
     * @return The {@link AchievementType} for the Achievement. Used for identifying
     *         it's input object.
     */
    AchievementType getType();

    /**
     * @param subject The object the test the condition against.
     * @return <code>true</code> if the condition is met, <code>false</code> otherwise.
     */
    boolean isConditionMet(Object subject);

    /**
     * @return The number of times a condition has to be met before granting the Achievment.
     */
    Integer getCountingLimit();

}
