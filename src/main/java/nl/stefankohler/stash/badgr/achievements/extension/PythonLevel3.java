package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The PythonLevel3 Achievement.
 * Granted after adding 100 Python source files
 * to Stash.
 * 
 * @author Adam Parkin
 */
@Achievement
public class PythonLevel3 extends PythonLevel1 {

	private static final Integer COUNTING_LIMIT = Integer.valueOf(1000);

    @Override
    public String getBadge() {
        return "pythonlevel3.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
