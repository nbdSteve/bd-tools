package dev.nuer.bd.tools.tools;

import dev.nuer.bd.tools.utils.CommandExecutionUtil;
import dev.nuer.bd.tools.utils.PlayerCooldownUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class JailToolHandler {

    public static void onEvent(Player player, Player damager, Tool tool) {
        ArrayList<ItemStack> items = null;
        if (PlayerCooldownUtil.isOnCooldown(damager, "jail")) {
            PlayerCooldownUtil.getCooldownRemaining(damager, "jail", true);
            return;
        }
        for (ItemStack stack : player.getInventory().getContents()) {
            for (Material material : tool.getBannedItems().keySet()) {
                if (stack == null || stack.getType().equals(Material.AIR)) continue;
                if (stack.getType().equals(material) && stack.getDurability() == tool.getBannedItems().get(material)) {
                    if (items == null) items = new ArrayList<>();
                    player.getInventory().remove(stack);
                    damager.getInventory().addItem(stack);
                }
            }
        }
        if (items == null) return;
        PlayerCooldownUtil.setPlayerOnCooldown(damager, tool.getDelay(), "jail");
        CommandExecutionUtil.execute(tool, player);
    }
}