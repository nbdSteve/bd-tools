package dev.nuer.bd.tools.managers;

import dev.nuer.bd.tools.BdTools;
import dev.nuer.bd.tools.cmd.BdtCmd;
import dev.nuer.bd.tools.tools.InventoryToolHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {

    /**
     * Loads the files into the file manager
     *
     * @param fileManager FileManager, the plugins file manager
     */
    public static void setupFiles(FileManager fileManager) {
        fileManager.add("config", "bd-tools.yml");
        fileManager.add("messages", "messages.yml");
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(Plugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new ToolManager(), instance);
        pm.registerEvents(new InventoryToolHandler(), instance);
    }

    public static void registerCommands(BdTools instance) {
        instance.getCommand("bd-tools").setExecutor(new BdtCmd());
    }
}
