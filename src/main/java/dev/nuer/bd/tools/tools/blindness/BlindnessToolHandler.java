package dev.nuer.bd.tools.tools.blindness;

import dev.nuer.bd.tools.tools.Tool;
import dev.nuer.bd.tools.utils.PlayerCooldownUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlindnessToolHandler {

    public static void onEvent(Player player, Tool tool) {
        if (PlayerCooldownUtil.isOnCooldown(player, "blindness")) {
            PlayerCooldownUtil.getCooldownRemaining(player, "blindness", true);
            return;
        }
        PlayerCooldownUtil.setPlayerOnCooldown(player, tool.getDelay(), "blindness");
        for (Entity e : player.getNearbyEntities(tool.getElement(), tool.getElement(), tool.getElement())) {
            if (!(e instanceof Player)) continue;
            ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 5));
        }
    }
}