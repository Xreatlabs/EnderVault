package com.plugin.endervaults;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    
    public static void loadConfig() {
        FileConfiguration config = EnderVaults.getInstance().getConfig();
        
        // Set default values if not present
        config.addDefault("vaults.1.cost-money", 0);
        config.addDefault("vaults.1.cost-coins", 0);
        config.addDefault("vaults.2.cost-money", 25000);
        config.addDefault("vaults.2.cost-coins", 0);
        config.addDefault("vaults.3.cost-money", 50000);
        config.addDefault("vaults.3.cost-coins", 10);
        config.addDefault("vaults.4.cost-money", 100000);
        config.addDefault("vaults.4.cost-coins", 25);
        config.addDefault("vaults.5.cost-money", 250000);
        config.addDefault("vaults.5.cost-coins", 50);
        
        config.options().copyDefaults(true);
        EnderVaults.getInstance().saveConfig();
    }
}