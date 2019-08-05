package dev.nuer.bd.tools.utils;

import dev.nuer.bd.tools.BdTools;
import dev.nuer.bd.tools.tools.Tool;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandExecutionUtil {

    public static void execute(Tool tool, Player player) {
        for (String command : tool.getCommands()) {
            Bukkit.getScheduler().runTask(BdTools.instance, () -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
            });
        }
    }
}