package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The StyleHero Achievement.
 * Granted if you checked in over 152 stylus files to Stash.
 * 
 * @author Ofir Brukner
 * @since 2.1
 */
@Achievement
public class StyleHero extends ExtensionBasedAchievement {

    private static final Integer COUNTING_LIMIT = 152;

    @Override
    public String getBadge() {
        return "stylehero.png";
    }

    @Override
    public String getExtension() {
        return "styl";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
