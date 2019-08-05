package dev.nuer.bd.tools.managers;

import dev.nuer.bd.tools.BdTools;
import dev.nuer.bd.tools.support.nbtapi.NBTItem;
import dev.nuer.bd.tools.tools.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class ToolManager implements Listener {
    private static HashMap<Integer, Tool> tools;

    public ToolManager() {
        reload();
    }

    public static HashMap<Integer, Tool> getTools() {
        return tools;
    }

    public static Tool getToolByID(int configID) {
        return tools.get(configID);
    }

    public static void reload() {
        tools = new HashMap<>();
        for (int i = 1; i <= FileManager.get("config").getInt("number-of-tools"); i++) {
            Tool tool = new Tool(i,
                    ToolType.valueOf(FileManager.get("config").getString("tools." + i + ".type").toUpperCase()),
                    FileManager.get("config").getInt("tools." + i + ".reuse-delay"),
                    FileManager.get("config").getStringList("tools." + i + ".commands"));
            tools.put(i, tool);
        }
        BdTools.log.info("Successfully loaded all tools into internal configuration.");
    }

    @EventHandler
    public void toolEvent(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
        Player damager = (Player) event.getDamager();
        Player player = (Player) event.getEntity();
        if (damager.getItemInHand().getType().equals(Material.AIR)
                || !damager.getItemInHand().hasItemMeta()
                || !damager.getItemInHand().getItemMeta().hasLore()) return;
        NBTItem item = new NBTItem(damager.getItemInHand());
        try {
            item.getBoolean("bd-tool");
        } catch (Exception e) {
            return;
        }
        event.setCancelled(true);
        Tool tool = getToolByID(item.getInteger("bd-tools.config-id"));
        switch (item.getObject("bd-tools.type", ToolType.class)) {
            case INVENTORY:
                InventoryToolHandler.onEvent(player, damager, tool);
                break;
            case BLINDNESS:
                BlindnessToolHandler.onEvent(damager, tool);
                break;
            case JAIL:
                JailToolHandler.onEvent(player, damager, tool);
                break;
            default:
                break;
        }
    }
}
