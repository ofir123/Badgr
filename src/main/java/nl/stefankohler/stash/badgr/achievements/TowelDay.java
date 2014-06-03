package nl.stefankohler.stash.badgr.achievements;

import nl.stefankohler.stash.badgr.Achievement;

import org.joda.time.DateTime;

import com.atlassian.stash.content.Changeset;

/**
 * The Towelday Achievement.
 * Granted is a commit is made during TowelDay, 25th of May
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Achievement
public class TowelDay extends AbstractAchievement {

    private static final int TOWELDAY_DAY = 25;
    private static final int TOWELDAY_MONTH = 5;

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "towelday.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        DateTime timestamp = new DateTime(changeset.getAuthorTimestamp());

        return (timestamp.getMonthOfYear() == TOWELDAY_MONTH && timestamp.getDayOfMonth() == TOWELDAY_DAY);
    }
}
