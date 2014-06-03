package nl.stefankohler.stash.badgr.achievements;

import nl.stefankohler.stash.badgr.Achievement;

import org.joda.time.DateTime;

import com.atlassian.stash.content.Changeset;

/**
 * The Tuesday XO Achievement.
 * Granted after 5 commits made during XO time (Tuesday at 12:00-14:00).
 * 
 * @author Ofir Brukner
 * @since 2.1
 */
@Achievement
public class TuesdayXO extends AbstractAchievement {

    private static final Integer COUNTING_LIMIT = 5;
    
    // Week starts on Sunday.
    private static final int XO_DAY = 2;
    private static final int XO_HOUR_START = 12;
    private static final int XO_HOUR_END = 14;

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "tuesdayxo.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        DateTime timestamp = new DateTime(changeset.getAuthorTimestamp());
        
        return (timestamp.getDayOfWeek() == XO_DAY && 
        		timestamp.getHourOfDay() >= XO_HOUR_START &&
        		timestamp.getHourOfDay() <= XO_HOUR_END);
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
