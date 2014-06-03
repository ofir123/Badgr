package nl.stefankohler.stash.badgr.achievements;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The Thanks For Coming in Achievement.
 * Granted by the first commit to Stash.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Achievement
public class ThanksForComingIn extends AbstractAchievement {

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "thanksforcoming.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        return true;
    }

}
