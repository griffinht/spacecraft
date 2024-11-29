package com.griffinht.civcraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Debug {
    private static BukkitTask playerStatsTask;

    public static void startPlayerStatsDebug(Plugin plugin) {
        // Send a message to all players for debug
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("§6Debug: Starting player stats debug.");
        }
        // Cancel any existing task
        if (playerStatsTask != null) {
            playerStatsTask.cancel();
        }

        // Schedule new task to run every 10 seconds (200 ticks)
        playerStatsTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(String.format(
                    "§6Debug Stats:\n§7Hunger: §f%d\n§7Exhaustion: §f%.2f\n§7Absorption: §f%.1f",
                    player.getFoodLevel(),
                    player.getExhaustion(),
                    player.getAbsorptionAmount()
                ));
            }
        }, 0L, 200L);
    }

    public static void stopPlayerStatsDebug() {
        if (playerStatsTask != null) {
            playerStatsTask.cancel();
            playerStatsTask = null;
        }
    }
}
