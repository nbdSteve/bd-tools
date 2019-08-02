package dev.nuer.bd.tools.utils;

import dev.nuer.bd.tools.BdTools;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class SecondCountdownUtil {
    private static HashMap<UUID, Integer> timers;

    public static void countdown() {
        timers = new HashMap<>();
        Bukkit.getScheduler().runTaskTimerAsynchronously(BdTools.instance, () -> {
            for (UUID uuid : timers.keySet()) {
                if (timers.get(uuid) <= 0) {
                    timers.remove(uuid);
                } else {
                    timers.put(uuid, timers.get(uuid) - 1);
                }
            }
        }, 0L, 20L);
    }

    public static void addTimer(UUID uuid, int time) {
        timers.put(uuid, time);
    }

    public static void removeTimer(UUID uuid) {
        timers.remove(uuid);
    }

    public static boolean containsTimer(UUID uuid) {
        return timers.containsKey(uuid);
    }
}
