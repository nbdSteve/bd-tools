package dev.nuer.bd.tools.cmd;

import dev.nuer.bd.tools.BdTools;
import dev.nuer.bd.tools.managers.FileManager;
import dev.nuer.bd.tools.managers.ToolManager;
import dev.nuer.bd.tools.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BdtCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!sender.hasPermission("bd-tools.help")) {
                MessageUtil.message("messages", "permission-debug", (Player) sender,
                        "{node}", "bd-tools.help");
                return true;
            }
            if (sender instanceof Player) {
                MessageUtil.message("messages", "help", (Player) sender);
            } else {
                BdTools.log.warning("The help message can only be viewed in game.");
            }
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help")) {
                if (!sender.hasPermission("bd-tools.give")) {
                    MessageUtil.message("messages", "permission-debug", (Player) sender,
                            "{node}", "bd-tools.give");
                    return true;
                }
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "help", (Player) sender);
                } else {
                    BdTools.log.warning("The help message can only be viewed in game.");
                }
            }
            if (args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("bd-tools.give")) {
                    MessageUtil.message("messages", "permission-debug", (Player) sender,
                            "{node}", "bd-tools.give");
                    return true;
                }
                FileManager.reload();
                ToolManager.reload();
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "reload", (Player) sender);
                } else {
                    BdTools.log.warning("You have successfully reloaded all configuration files & internal maps.");
                }
            }
        } else if (args.length == 4) {
            if (!(args[0].equalsIgnoreCase("g") || args[0].equalsIgnoreCase("give"))) {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "command-debug", (Player) sender,
                            "{reason}", args[0] + " is not a valid command argument");
                } else {
                    BdTools.log.warning(args[0] + " is not a valid command argument.");
                }
                return true;
            }
            if (!sender.hasPermission("bd-tools.give")) {
                MessageUtil.message("messages", "permission-debug", (Player) sender,
                        "{node}", "bd-tools.give");
                return true;
            }
            Player player;
            int configID;
            try {
                player = Bukkit.getPlayer(args[1]);
                if (!Bukkit.getOnlinePlayers().contains(player)) {
                    if (sender instanceof Player) {
                        MessageUtil.message("messages", "command-debug", (Player) sender,
                                "{reason}", "That player is not online, player must be online");
                    } else {
                        BdTools.log.warning("That player is not online, player must be online.");
                    }
                    return true;
                }
            } catch (Exception e) {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "command-debug", (Player) sender,
                            "{reason}", "The name you entered is not a valid player");
                } else {
                    BdTools.log.warning("The name you entered is not a valid player.");
                }
                return true;
            }
            try {
                configID = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "command-debug", (Player) sender,
                            "{reason}", "The configuration ID you entered is not a number, it must be an integer");
                } else {
                    BdTools.log.warning("The configuration ID you entered is not a number, it must be an integer.");
                }
                return true;
            }
            if (configID <= 0 || configID > FileManager.get("config").getInt("number-of-tools")) {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "command-debug", (Player) sender,
                            "{reason}", "The configuration ID you entered is not mapped to a tool please try a different number");
                } else {
                    BdTools.log.warning("The configuration ID you entered is not mapped to a tool please try a different number.");
                }
                return true;
            }
            String[] material = args[3].split(":");
            try {
                Material.getMaterial(material[0].toUpperCase());
            } catch (Exception e) {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "command-debug", (Player) sender,
                            "{reason}", "The material you entered is in valid, please make sure it is named according to Bukkit names");
                } else {
                    BdTools.log.warning("The material you entered is in valid, please make sure it is named according to Bukkit names.");
                }
                return true;
            }
            try {
                Short.parseShort(material[1]);
            } catch (Exception e) {
                if (sender instanceof Player) {
                    MessageUtil.message("messages", "command-debug", (Player) sender,
                            "{reason}", "The item data value you entered is invalid, follow this syntax: <material>:<damage_value>");
                } else {
                    BdTools.log.warning("The item data value you entered is invalid, follow this syntax: <material>:<damage_value>.");
                }
                return true;
            }
            ToolManager.getToolByID(configID).givePlayer(player, material[0], material[1]);
        }
        return true;
    }
}
