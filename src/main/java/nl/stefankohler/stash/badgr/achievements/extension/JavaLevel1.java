package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The JavaLevel Achievement.
 * Granted after adding an X number of JAVA files
 * to Stash.
 * 
 * @author Stefan Kohler
 * @since 1.0
 */
@Achievement
public class JavaLevel1 extends ExtensionBasedAchievement {

    private static final Integer COUNTING_LIMIT = 25;

    @Override
    public String getBadge() {
        return "javalevel1.png";
    }

    @Override
    public String getExtension() {
        return "java";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
