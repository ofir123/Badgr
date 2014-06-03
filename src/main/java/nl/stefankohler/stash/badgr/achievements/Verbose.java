package nl.stefankohler.stash.badgr.achievements;

import nl.stefankohler.stash.badgr.Achievement;

import org.apache.commons.lang.StringUtils;

import com.atlassian.stash.content.Changeset;

/**
 * The Verbose Achievement.
 * Granted when a commit message has over the defined
 * max length characters.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Achievement
public class Verbose extends AbstractAchievement {

    private static final int MAX_MESSAGE_LENGTH = 720;

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "verbose.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        if (StringUtils.isNotBlank(changeset.getMessage())) {
            return changeset.getMessage().length() > MAX_MESSAGE_LENGTH;
        }
        return false;
    }

}
