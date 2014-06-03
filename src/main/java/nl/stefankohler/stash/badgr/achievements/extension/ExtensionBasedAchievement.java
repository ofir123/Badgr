package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.achievements.AbstractAchievement;

import org.apache.commons.lang.StringUtils;

import com.atlassian.stash.content.Change;
import com.atlassian.stash.content.ChangeType;

public abstract class ExtensionBasedAchievement extends AbstractAchievement {

    @Override
    public AchievementType getType() {
        return AchievementType.CHANGE;
    }

    @Override
    public boolean isConditionMet(Object subject) {
        Change change = (Change) subject;
        if (change.getType().equals(ChangeType.ADD) && StringUtils.isNotEmpty(change.getPath().getExtension())) {
            return change.getPath().getExtension().endsWith(getExtension());
        }

        return false;
    }

    /**
     * @return the file extension for the Achievement to check on.
     */
    public abstract String getExtension();

}
