package nl.stefankohler.stash.badgr.achievements;

import nl.stefankohler.stash.badgr.Achievement;

import org.apache.commons.lang.StringUtils;

import com.atlassian.stash.content.Changeset;

/**
 * The NothingToSay Achievement.
 * Granted if you commit to Stash without a commit message.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Achievement
public class NothingToSay extends AbstractAchievement {

    private static final Integer COUNTING_LIMIT = 10;

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "nothingtosay.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        return StringUtils.isBlank(changeset.getMessage());
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }
}
