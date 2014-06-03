package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The C++ Achievement.
 * Granted after adding an X number of cpp files to Stash.
 * 
 * @author Ofir Brukner
 * @since 2.1
 */
@Achievement
public class CppLevel3 extends CppLevel1 {

    private static final Integer COUNTING_LIMIT = Integer.valueOf(1000);

    @Override
    public String getBadge() {
        return "cpplevel3.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
