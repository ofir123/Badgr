package nl.stefankohler.stash.badgr.achievements.extension;

import nl.stefankohler.stash.badgr.Achievement;

/**
 * The Verilog Achievement.
 * Granted after adding an X number of verilog files to Stash.
 * 
 * @author Ofir Brukner
 * @since 2.1
 */
 
@Achievement
public class VerilogLevel1 extends ExtensionBasedAchievement {

    private static final Integer COUNTING_LIMIT = 25;
	private static final String[] extensions = { "vh", "v" };

    @Override
    public String getBadge() {
        return "veriloglevel1.png";
    }

    @Override
    public String getExtension() {
        return extensions[0];
    }
	
	@Override
    public String[] getExtensions() {
        return extensions;
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }

}
