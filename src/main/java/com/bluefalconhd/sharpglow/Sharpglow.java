package com.bluefalconhd.sharpglow;

import org.bukkit.plugin.java.JavaPlugin;

public final class Sharpglow extends JavaPlugin {

    private TemporaryEnchantmentManager tempEnchantManager;

    @Override
    public void onEnable() {
        // log startup message
        getLogger().info("Sharpglow has been enabled!");

        tempEnchantManager = new TemporaryEnchantmentManager(this);

        // Register the MilkCancelListener class as a listener
        getServer().getPluginManager().registerEvents(new MilkCancelListener(), this);
        // Register the PlayerKillDeathListener class as a listener
        getServer().getPluginManager().registerEvents(new PlayerKillDeathListener(), this);
        //
    }

    @Override
    public void onDisable() {
        // log shutdown message
        getLogger().info("Sharpglow has been disabled!");
    }

    public static TemporaryEnchantmentManager getTemporaryEnchantmentManager() {
        return tempEnchantManager;
    }
}
