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
public class JavaLevel3 extends JavaLevel1 {

    private static final Integer COUNTING_LIMIT = Integer.valueOf(1000);

    @Override
    public String getBadge() {
        return "javalevel3.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
