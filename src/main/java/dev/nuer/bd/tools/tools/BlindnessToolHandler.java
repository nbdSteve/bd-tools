package dev.nuer.bd.tools.tools;

import dev.nuer.bd.tools.tools.Tool;
import dev.nuer.bd.tools.utils.CommandExecutionUtil;
import dev.nuer.bd.tools.utils.PlayerCooldownUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
        CommandExecutionUtil.execute(tool, player);
        int radius = tool.getEffectAttributes().get(0);
        int duration = tool.getEffectAttributes().get(1);
        int amplifier = tool.getEffectAttributes().get(2);
        for (Entity e : player.getNearbyEntities(radius, radius, radius)) {
            if (!e.getType().equals(EntityType.PLAYER)) continue;
            ((Player) e).removePotionEffect(PotionEffectType.BLINDNESS);
            ((Player) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, amplifier));
        }
    }
}