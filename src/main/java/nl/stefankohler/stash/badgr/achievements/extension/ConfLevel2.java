package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

@Achievement
public class ConfLevel2 extends ConfLevel1 {

    private static final Integer COUNTING_LIMIT = 20;

    @Override
    public String getBadge() {
        return "conflevel2.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }
}