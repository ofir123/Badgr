package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

@Achievement
public class ConfLevel5 extends ConfLevel1 {

    private static final Integer COUNTING_LIMIT = 1000;

    @Override
    public String getBadge() {
        return "conflevel5.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }
}