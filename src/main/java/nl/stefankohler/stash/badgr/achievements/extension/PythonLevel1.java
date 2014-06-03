package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The PythonLevel1 Achievement.
 * Granted after adding a 25 Python source files
 * to Stash.
 * 
 * @author Adam Parkin
 */
@Achievement
public class PythonLevel1 extends ExtensionBasedAchievement {

	private static final Integer COUNTING_LIMIT = 25;

    @Override
    public String getBadge() {
        return "pythonlevel1.png";
    }

    @Override
    public String getExtension() {
        return "py";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
