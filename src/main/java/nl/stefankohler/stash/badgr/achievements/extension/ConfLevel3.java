package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

@Achievement
public class ConfLevel3 extends ConfLevel1 {

    private static final Integer COUNTING_LIMIT = 100;

    @Override
    public String getBadge() {
        return "conflevel3.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }
}