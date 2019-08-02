package dev.nuer.bd.tools;

import dev.nuer.bd.tools.managers.FileManager;
import dev.nuer.bd.tools.managers.SetupManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class BdTools extends JavaPlugin {
    public static BdTools instance;
    public static Logger log;
    public static String version = "0.0.2.1";

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        log = getLogger();
        SetupManager.setupFiles(new FileManager(instance));
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        log.info("Thanks for getting private development! If you find any bugs please contact nbdSteve#0583 on discord.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log.info("Thanks for getting private development! If you find any bugs please contact nbdSteve#0583 on discord.");
    }
}
