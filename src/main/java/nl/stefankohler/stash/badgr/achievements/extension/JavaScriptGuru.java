package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The JavaScriptGuru Achievement.
 * Granted after adding X number of JavaScript files.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Achievement
public class JavaScriptGuru extends ExtensionBasedAchievement {

    private static final Integer COUNTING_LIMIT = 200;

    @Override
    public String getBadge() {
        return "jsfileachievement.png";
    }

    @Override
    public String getExtension() {
        return "js";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }
}
