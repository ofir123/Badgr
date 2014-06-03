package nl.stefankohler.stash.badgr.achievements;

import java.util.regex.Pattern;

import nl.stefankohler.stash.badgr.Achievement;

import org.apache.commons.lang.StringUtils;

import com.atlassian.stash.content.Changeset;

/**
 * The Badgr Badgr Badgr Achievement.
 * Granted if you mention Badgr in a commit message.
 * 
 * @author Stefan Kohler
 * @since 1.1
 */
@Achievement
public class BadgrBadgrBadgr extends AbstractAchievement {

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "badgrbadgrbadgr.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        if (StringUtils.isNotBlank(changeset.getMessage())) {
            Pattern pattern = Pattern.compile("\\bBadgr\\b", Pattern.CASE_INSENSITIVE);
            return pattern.matcher(changeset.getMessage()).find();
        }
        return false;
    }

}
