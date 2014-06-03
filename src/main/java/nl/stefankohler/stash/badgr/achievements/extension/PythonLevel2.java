package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The PythonLevel2 Achievement.
 * Granted after adding 100 Python source files
 * to Stash.
 * 
 * @author Adam Parkin
 */
@Achievement
public class PythonLevel2 extends PythonLevel1 {

	private static final Integer COUNTING_LIMIT = Integer.valueOf(100);

    @Override
    public String getBadge() {
        return "pythonlevel2.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
