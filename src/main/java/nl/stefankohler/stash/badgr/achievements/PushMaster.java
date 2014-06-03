package nl.stefankohler.stash.badgr.achievements;

import nl.stefankohler.stash.badgr.Achievement;

import com.atlassian.stash.event.StashEvent;

/**
 * The PushMaster Achievement.
 * Granted if you did over 1000 pushed to Stash
 * 
 * @author Stefan Kohler
 * @since 1.1
 */
@Achievement
public class PushMaster extends AbstractAchievement {

    private static final Integer COUNTING_LIMIT = 1000;

    @Override
    public AchievementType getType() {
        return AchievementType.EVENT;
    }

    @Override
    public String getBadge() {
        return "pushmaster.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        return (subject instanceof StashEvent);
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
