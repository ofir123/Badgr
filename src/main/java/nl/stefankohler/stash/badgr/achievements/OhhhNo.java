package nl.stefankohler.stash.badgr.achievements;

import java.util.regex.Pattern;

import nl.stefankohler.stash.badgr.Achievement;

import org.apache.commons.lang.StringUtils;

import com.atlassian.stash.content.Changeset;

/**
 * The Ohhh No Achievement.
 * Granted if you use Ohhh No in a commit message.
 * 
 * @author Ofir Brukner
 * @since 2.1
 */
@Achievement
public class OhhhNo extends AbstractAchievement {

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "ohhhno.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;
        if (StringUtils.isNotBlank(changeset.getMessage())) {
            Pattern pattern = Pattern.compile("\\bOhhh No\\b", Pattern.CASE_INSENSITIVE);
            return pattern.matcher(changeset.getMessage()).find();
        }
        return false;
    }

}
