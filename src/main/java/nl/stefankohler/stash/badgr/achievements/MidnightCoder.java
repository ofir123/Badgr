package nl.stefankohler.stash.badgr.achievements;

import nl.stefankohler.stash.badgr.Achievement;

import org.joda.time.LocalDateTime;

import com.atlassian.stash.content.Changeset;

/**
 * the Midnight Code Achievement
 * Granted if a commit is done between the defined MIN
 * and MAX hours of the day.<br>
 * <br>
 * default: between 2 and 5 AM
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Achievement
public class MidnightCoder extends AbstractAchievement {

    private static final Integer COUNTING_LIMIT = 5;

    private static final int BETWEEN_MIN = 2;
    private static final int BETWEEN_MAX = 5;

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "midnightcoder.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        LocalDateTime timestamp = new LocalDateTime(changeset.getAuthorTimestamp());

        LocalDateTime min = timestamp.withHourOfDay(BETWEEN_MIN).withMinuteOfHour(0);
        LocalDateTime max = timestamp.withHourOfDay(BETWEEN_MAX).withMinuteOfHour(0);

        return (timestamp.isAfter(min) && timestamp.isBefore(max));
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
