package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The PythonLevel5 Achievement.
 * Granted after adding 5000 Python source files
 * to Stash.
 * 
 * @author Adam Parkin
 */
@Achievement
public class PythonLevel5 extends PythonLevel1 {

	private static final Integer COUNTING_LIMIT = Integer.valueOf(10000);

    @Override
    public String getBadge() {
        return "pythonlevel5.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
