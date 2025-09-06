package com.plugin.endervaults;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public class EnderVaults extends JavaPlugin {

    private static EnderVaults instance;
    
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerCommands();
        registerEvents();
        setupEconomy();
        getLogger().info("EnderVaults v" + getDescription().getVersion() + " by " + getDescription().getAuthors().get(0) + " has been enabled!");
        getLogger().info("Supporting Minecraft versions 1.8 - 1.21.8");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("EnderVaults has been disabled!");
    }
    
    private void registerCommands() {
        this.getCommand("echest").setExecutor(new EnderChestCommand());
    }
    
    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new VaultListener(), this);
    }
    
    private void setupEconomy() {
        if (EconomyManager.setupEconomy()) {
            getLogger().info("Vault economy integration enabled!");
        } else {
            getLogger().warning("Vault economy not found. Economy features will be disabled.");
        }
    }
    
    public static EnderVaults getInstance() {
        return instance;
    }
}