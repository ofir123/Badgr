package nl.stefankohler.stash.badgr.achievements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.stefankohler.stash.badgr.Achievement;

import org.apache.commons.lang.StringUtils;

import com.atlassian.stash.content.Changeset;

/**
 * The PardonMyFrench Achievement.
 * Granted if you swear in your commit messages.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Achievement
public class PardonMyFrench extends AbstractAchievement {

    private static final Integer COUNTING_LIMIT = 10;

    static final String[] BLACK_LIST = { "fuck", "fucking", "shit", "god", "hell", "jesus", "christ", "jesuschrist", "bitch", "ass", "damn", "piss", "pissed",
            "goddamn", "bloody", "crap", "dick" };

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGESET;
    }

    @Override
    public String getBadge() {
        return "pardonmyfrench.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Changeset changeset = (Changeset) subject;

        if (StringUtils.isNotBlank(changeset.getMessage())) {
            for (int i = 0; i < BLACK_LIST.length; i++) {
                Pattern pattern = Pattern.compile("\\b" + BLACK_LIST[i] + "\\b", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(changeset.getMessage());
                if (matcher.find()) return true;
            }
        }
        return false;
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
