package dev.nuer.bd.tools.utils;

import dev.nuer.bd.tools.BdTools;
import dev.nuer.bd.tools.managers.FileManager;
import dev.nuer.bd.tools.support.actionbarapi.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 * Class that handles all player tool cooldowns for the plugin
 */
public class PlayerCooldownUtil {
    //Store the players who are on the blindness tool cooldown
    private static HashMap<UUID, Integer> playersOnInventoryCooldown = new HashMap<>();
    private static HashMap<UUID, Integer> playersOnBlindnessCooldown = new HashMap<>();
    private static HashMap<UUID, Integer> playersOnJailCooldown = new HashMap<>();

    /**
     * Sets the respective player on the respective cooldown
     *
     * @param player           Player, the player to set on cooldown
     * @param delayInSeconds   Integer, the length of cooldown
     * @param cooldownToolType String, the tool type
     */
    public static void setPlayerOnCooldown(Player player, int delayInSeconds, String cooldownToolType) {
        if (delayInSeconds < 0) {
            return;
        }
        //Add the player to the appropriate cooldown map
        getCooldownMap(cooldownToolType).put(player.getUniqueId(), delayInSeconds);
        new BukkitRunnable() {
            int timer = delayInSeconds;

            @Override
            public void run() {
                if (timer < delayInSeconds && timer > 0) {
                    getCooldownMap(cooldownToolType).remove(player.getUniqueId());
                    getCooldownMap(cooldownToolType).put(player.getUniqueId(), timer);
                    getCooldownRemaining(player, cooldownToolType, true);
                } else if (timer == 0) {
                    getCooldownMap(cooldownToolType).remove(player.getUniqueId());
                    this.cancel();
                }
                timer--;
            }
        }.runTaskTimer(BdTools.instance, 0L, 20L);
    }

    /**
     * Check if the player is on a tool cooldown
     *
     * @param player           Player, the player to check
     * @param cooldownToolType String, the type of tool for the cooldown
     * @return boolean
     */
    public static boolean isOnCooldown(Player player, String cooldownToolType) {
        if (getCooldownMap(cooldownToolType).get(player.getUniqueId()) != null) {
            return true;
        }
        return false;
    }

    /**
     * Gets the players remaining cooldown in seconds
     *
     * @param player             Player, the player  to query
     * @param cooldownToolType   String, the type of cooldown
     * @param sendPlayerResponse boolean, if a message will be sent to the player
     * @return
     */
    public static int getCooldownRemaining(Player player, String cooldownToolType, boolean sendPlayerResponse) {
        if (sendPlayerResponse) {
            if (FileManager.get("config").getBoolean("cooldown-action-bar-enabled")) {
                String message = FileManager.get("messages").getString("cooldown-action-bar")
                        .replace("{time}", String.valueOf(getCooldownMap(cooldownToolType).get(player.getUniqueId())))
                                .replace("{tool-type}", cooldownToolType + " tool");
                ActionBarAPI.sendActionBar(player, ColorUtil.colorize(message));
            } else {
                MessageUtil.message("messages", "tool-cooldown", Bukkit.getPlayer(player.getUniqueId())
                , "{time}", String.valueOf(getCooldownMap(cooldownToolType).get(player.getUniqueId())));
            }
        }
        return getCooldownMap(cooldownToolType).get(player.getUniqueId());
    }

    /**
     * Gets the cooldown map for a tool
     *
     * @param cooldownToolType String, the type of tool for the cooldown
     * @return HashMap<UUID, Integer>
     */
    public static HashMap<UUID, Integer> getCooldownMap(String cooldownToolType) {
        if (cooldownToolType.equalsIgnoreCase("inventory")) return playersOnInventoryCooldown;
        if (cooldownToolType.equalsIgnoreCase("blindness")) return playersOnBlindnessCooldown;
        if (cooldownToolType.equalsIgnoreCase("jail")) return playersOnJailCooldown;
        return null;
    }
}