package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;
import nl.stefankohler.stash.badgr.DefaultAchievementManager;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.stash.content.Change;
import com.atlassian.stash.content.ChangeType;

@Achievement
public class ReadMeMarkdown extends ExtensionBasedAchievement {

    private final static Logger LOG = LoggerFactory.getLogger(ReadMeMarkdown.class);

    @Override
    public String getExtension() {
        return "md";
    }

    @Override
    public String getBadge() {
        return "readmewriter.png";
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Change change = (Change) subject;
        String changeExtension = change.getPath().getExtension();
        if (change.getType().equals(ChangeType.ADD) && StringUtils.isNotEmpty(changeExtension)) {
            if (change.getPath().getName().toLowerCase().startsWith("readme")) {
                boolean answer = changeExtension.equals(getExtension());
                LOG.debug("added readme: " + answer);
                return answer;
            }
        }
        return false;
    }

}