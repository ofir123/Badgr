package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The JadeDynasty Achievement.
 * Granted after adding an X number of jade files to Stash.
 * 
 * @author Ofir Brukner
 * @since 2.1
 */
@Achievement
public class JadeDynasty extends ExtensionBasedAchievement {

    private static final Integer COUNTING_LIMIT = 200;

    @Override
    public String getBadge() {
        return "jadefileachievement.png";
    }

    @Override
    public String getExtension() {
        return "jade";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }
}
