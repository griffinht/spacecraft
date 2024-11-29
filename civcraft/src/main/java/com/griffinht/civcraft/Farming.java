package com.griffinht.civcraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.MoistureChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class Farming implements Listener {
    private final Map<Block, Timestamp> crops = new HashMap<>();

    private static final long GROW_TIME = 1000 * 60 * 60 * 4; // 4 hours

    public Farming(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        System.out.println("Enabling farming");
    }

    @EventHandler
    public void onMoistureChange(MoistureChangeEvent event) {
        if (true) return;

        event.getBlock().getWorld().getPlayers().forEach(player -> {
            player.sendMessage("moisture changed todo disable this " + event.getBlock().getLocation() + event.getBlock().getType());
        });
    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent event) {
        if (event.getBlock().getType().equals(Material.WHEAT)) {
            Timestamp timestamp = crops.get(event.getBlock());
            if (timestamp == null) {
                crops.put(event.getBlock(), new Timestamp(System.currentTimeMillis()));
                event.setCancelled(true);
                return;
            }

            long GROW_TIME = 1000 * 60 * 60 * 4;
            if (System.currentTimeMillis() - timestamp.getTime() > GROW_TIME) {
                return;
            }

            // todo check if its max then expire it
            //crops.remove(event.getBlock());
            return;
        }
        
        // Existing debug message
        System.out.println("block grew todo disable this " + event.getBlock().getLocation() + event.getBlock().getType());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!isHoe(event.getItem())) return;
        
        Block block = event.getClickedBlock();
        if (block == null || block.getType() != Material.WHEAT) return;

        Timestamp plantTime = crops.get(block);
        if (plantTime == null) {
            event.getPlayer().sendMessage("This crop hasn't been tracked yet.");
            return;
        }

        long timeElapsed = System.currentTimeMillis() - plantTime.getTime();
        long timeRemaining = GROW_TIME - timeElapsed;
        
        if (timeRemaining <= 0) {
            event.getPlayer().sendMessage("This crop is ready to grow!");
        } else {
            long minutesRemaining = timeRemaining / (1000 * 60);
            event.getPlayer().sendMessage(String.format("Time until next growth: %d minutes", minutesRemaining));
        }
    }

    private boolean isHoe(ItemStack item) {
        if (item == null) return false;
        return item.getType().name().endsWith("_HOE");
    }
}
