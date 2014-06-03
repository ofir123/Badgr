package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The Style Is Eternal Achievement.
 * Granted after adding an X number of CSS files
 * to Stash.
 * 
 * @author Stefan Kohler
 * @since 1.1
 */
@Achievement
public class StyleIsEternal extends ExtensionBasedAchievement {

    private static final Integer COUNTING_LIMIT = 71;

    @Override
    public String getExtension() {
        return "css";
    }

    @Override
    public String getBadge() {
        return "styleiseternal.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
