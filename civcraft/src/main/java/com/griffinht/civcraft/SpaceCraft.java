package com.griffinht.civcraft;

import org.bukkit.plugin.java.JavaPlugin;

public class SpaceCraft extends JavaPlugin {
    Exposure cold = new Exposure();
    Systems systems = new Systems();

    @Override
    public void onEnable() {
        systems.start(this);

        getLogger().info("onEnable is called!");
        getServer().getPluginManager().registerEvents(new Respawn(), this);
        getServer().getPluginManager().registerEvents(new Exposure(), this);
        
        cold.register(this);
        Debug.startPlayerStatsDebug(this);
    }

    @Override
    public void onDisable() {
        systems.stop();
        getLogger().info("onDisable is called!");
        Debug.stopPlayerStatsDebug();
        cold.deregister();
    }
}