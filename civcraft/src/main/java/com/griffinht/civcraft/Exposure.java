package com.griffinht.civcraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Exposure implements Listener {
    BukkitTask runnable;
    
    public void register(SpaceCraft spaceCraft) {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getWorlds().get(0).getPlayers()) {
                    applyExposure(player);
                }
            }
        }.runTaskTimer(spaceCraft, 0, 20); // 20 ticks = 1 second
    }

    public void deregister() {
        if (runnable != null) {
            runnable.cancel();
        }
    }
    
    private static void applyExposure(Player player) {
        if (!isInColdBiome(player)) {
            return;
        }

        if (!player.hasPotionEffect(PotionEffectType.HUNGER)) {
            if (isArtificalDark(player)) {
                player.sendMessage("you are feeling cold from lack of artifical light - try to somewhere brighter");
                applyPain(player);
                return;
            }
        } else {
            if (!isArtificalDark(player)) {
                player.sendMessage("you found light and warmth");
                removePain(player);
                return;
            }
        }
    }

    private static boolean isInColdBiome(Player player) {
        return player.getLocation().getBlock().getTemperature() < 0.15;
    }

    private static boolean isArtificalDark(Player player) {
        int blockLight = player.getLocation().getBlock().getLightFromBlocks();
        return blockLight < 8;
    }

    private static void applyPain(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, -1, 0));
    }

    private static void removePain(Player player) {
        player.removePotionEffect(PotionEffectType.HUNGER);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        removePain(event.getPlayer());
    }
}
