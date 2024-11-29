package com.griffinht.civcraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;

/*
 * change default food level to 6 instead of starting with full bars
            // todo disable natural regen???
 */
public class Respawn implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // todo handle first time joins? just let them be satiated lol
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("aasd");
        event.getPlayer().getServer().getScheduler().runTaskLater(SpaceCraft.getPlugin(SpaceCraft.class), 
        () -> {
            player.setFoodLevel(6);
            player.setHealth(10);
        }, 1);
    }
}
