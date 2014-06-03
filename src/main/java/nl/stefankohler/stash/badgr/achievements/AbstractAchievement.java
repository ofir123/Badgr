package nl.stefankohler.stash.badgr.achievements;

import java.util.Locale;

/**
 * Abstract Implementation of an Achievement.
 * Takes care of the common implementation of an Achievement like<br>
 * - Code<br>
 * - Name<br>
 * - Description
 * 
 * @author stefankohler
 * @since 1.0
 */
public abstract class AbstractAchievement implements Achievement {

    @Override
    public String getCode() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getName() {
        return "achievement." + getCode().toLowerCase(Locale.getDefault()) + ".name";
    }

    @Override
    public String getDescription() {
        return "achievement." + getCode().toLowerCase(Locale.getDefault()) + ".description";
    }

    @Override
    public String getBadge() {
        return "default.png";
    }

    @Override
    public Integer getCountingLimit() {
        return 1;
    }
}
