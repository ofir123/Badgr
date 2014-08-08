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
public class VerilogLevel4 extends VerilogLevel1 {

    private static final Integer COUNTING_LIMIT = 5000;

    @Override
    public String getBadge() {
        return "veriloglevel4.png";
    }

    @Override
    public Integer getCountingLimit() {
        return COUNTING_LIMIT;
    }
}
