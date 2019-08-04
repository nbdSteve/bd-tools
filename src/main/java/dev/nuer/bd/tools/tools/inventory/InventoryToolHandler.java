package dev.nuer.bd.tools.tools.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryToolHandler implements Listener {
    public static Map<UUID, Inventory> playersViewing = new HashMap<>();

    public static void onEvent(Player player, Player damager) {
        damager.openInventory(player.getInventory());
        playersViewing.put(damager.getUniqueId(), player.getInventory());
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {
        UUID puid = event.getPlayer().getUniqueId();
        if (!playersViewing.containsKey(puid)) return;
        if (event.getInventory().equals(playersViewing.get(puid))) playersViewing.remove(puid);
    }

    @EventHandler
    public void blockClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(playersViewing.get(event.getWhoClicked().getUniqueId()))) return;
        event.setCancelled(true);
    }
}
