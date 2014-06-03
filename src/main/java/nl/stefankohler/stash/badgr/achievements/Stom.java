package nl.stefankohler.stash.badgr.achievements;

import java.util.regex.Pattern;

import nl.stefankohler.stash.badgr.Achievement;

import org.apache.commons.lang.StringUtils;

import com.atlassian.stash.content.Changeset;

/**
 * The Stom Achievement.
 * Granted if you use Stom in a commit message.
 * 
 * @author Ofir Brukner
 * @since 2.1
 */
@Achievement
public class Stom extends AbstractAchievement {

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "stom.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        if (StringUtils.isNotBlank(changeset.getMessage())) {
            Pattern pattern = Pattern.compile("\\bStom\\b", Pattern.CASE_INSENSITIVE);
            return pattern.matcher(changeset.getMessage()).find();
        }
        return false;
    }

}
