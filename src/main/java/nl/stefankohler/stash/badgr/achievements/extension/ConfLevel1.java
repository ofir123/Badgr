package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

@Achievement
public class ConfLevel1 extends ExtensionBasedAchievement {
    private static final Integer COUNTING_LIMIT = 5;
    private static final String[] extensions = { "conf", "xml", "properties" };

    @Override
    public String[] getExtensions() {
        return extensions;
    }

    public String getExtension() {
        return extensions[0];
    }

    @Override
    public String getBadge() {
        return "conflevel1.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }
}