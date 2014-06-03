package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The Webmaster Achievement.
 * Granted if you checked in over 200 HTML files to Stash.
 * 
 * @author Ofir Brukner
 * @since 2.1
 */
@Achievement
public class Webmaster extends ExtensionBasedAchievement {

    private static final Integer COUNTING_LIMIT = 200;

    @Override
    public String getBadge() {
        return "htmlfileachievement.png";
    }

    @Override
    public String getExtension() {
        return "html";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }
}
