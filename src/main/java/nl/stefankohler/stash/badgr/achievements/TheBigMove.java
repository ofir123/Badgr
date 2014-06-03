package nl.stefankohler.stash.badgr.achievements;

import nl.stefankohler.stash.badgr.Achievement;

import com.atlassian.stash.content.Change;
import com.atlassian.stash.content.ChangeType;

/**
 * The Big Move Achievement.
 * Granted after 100 movements of source code.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Achievement
public class TheBigMove extends AbstractAchievement {

    private static final Integer COUNTING_LIMIT = 100;

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGE;
    }

    @Override
    public String getBadge() {
        return "thebigmove.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Change change = (Change) subject;
        return ChangeType.MOVE.equals(change.getType());
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
