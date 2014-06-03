package nl.stefankohler.stash.badgr.web.conditions;

import java.util.Map;

import com.atlassian.plugin.PluginAccessor;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;

/**
 * Only display this section if other plugins with the same websection aren't enabled.
 * Not the most ideal solution, but manageable for now.
 * 
 * @author stefan
 */
public class DisplayAddonWebSection implements Condition {

    private final PluginAccessor pluginAccessor;

    public DisplayAddonWebSection(PluginAccessor pluginAccessor) {
        this.pluginAccessor = pluginAccessor;
    }

    @Override
    public void init(Map<String, String> arg0) throws PluginParseException {
        // nothing to init.

    }

    @Override
    public boolean shouldDisplay(Map<String, Object> arg0) {
        boolean pluginEnabled = pluginAccessor.isPluginEnabled("nl.stefankohler.stash.stash-notification-plugin");
        return !pluginEnabled;
    }

}
