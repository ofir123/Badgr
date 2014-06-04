package nl.stefankohler.stash.badgr.achievements;

import nl.stefankohler.stash.badgr.Achievement;
import org.joda.time.DateTime;
import com.atlassian.stash.content.Changeset;

/**
 * The Talk Like a Pirate Day Achievement.
 * Granted when a commit is made during Talk Like a Pirate Day, 19th of September
 * 
 * @author Chad Hill
 * @since 1.0
 */
@Achievement
public class PirateDay extends AbstractAchievement {

    private static final int PIRATEDAY_DAY = 19;
    private static final int PIRATEDAY_MONTH = 9;

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "pirateday.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        DateTime timestamp = new DateTime(changeset.getAuthorTimestamp());

        return (timestamp.getMonthOfYear() == PIRATEDAY_MONTH && timestamp.getDayOfMonth() == PIRATEDAY_DAY);
    }
}