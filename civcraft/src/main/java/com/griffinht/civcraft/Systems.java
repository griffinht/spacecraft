package com.griffinht.civcraft;

import java.io.File;
import java.sql.SQLException;

import org.bukkit.plugin.Plugin;

public class Systems {
    private static final class Running {
        private boolean running = true;

        synchronized void wait_() throws InterruptedException {
            while (running) {
                System.out.println("Running: running...");
                wait();
            }
            System.out.println("Running: stopped");
        }
        
        synchronized void stop() {
            System.out.println("Running: sending stop signal");
            running = false;
            notifyAll();
            System.out.println("Running: sent stop signal");
        }
    }

    private Running running = new Running();
    private Thread thread;

    public void start(Plugin plugin) {
        thread = new Thread(() -> {
            try {
                new Farming(plugin);
                plugin.getServer().getWorlds().forEach(world -> {
                    world.getPlayers().forEach(player -> {
                        File jarFile = new File("/Users/griffin/git/spacecraft/civcraft/target/civcraft-1.0-SNAPSHOT.jar");
                        long secondsAgo = (System.currentTimeMillis() - jarFile.lastModified()) / 1000;
                        player.sendMessage("hello welcome to the jungle - JAR last modified: " + secondsAgo + " seconds ago");
                    });
                });
                running.wait_();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        System.out.println("Systems: started");
    }

    public void stop() {
        System.out.println("Systems: stopping");
        running.stop();
        try {
            System.out.println("Systems: stopped");
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
