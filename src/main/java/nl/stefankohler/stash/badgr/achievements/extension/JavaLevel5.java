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
public class JavaLevel5 extends JavaLevel1 {

    private static final Integer COUNTING_LIMIT = Integer.valueOf(10000);

    @Override
    public String getBadge() {
        return "javalevel5.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
