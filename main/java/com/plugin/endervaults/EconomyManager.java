package com.plugin.endervaults;

import org.bukkit.entity.Player;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {
    private static Economy vaultEconomy = null;
    
    public static boolean setupEconomy() {
        if (EnderVaults.getInstance().getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        
        RegisteredServiceProvider<Economy> rsp = EnderVaults.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        
        vaultEconomy = rsp.getProvider();
        return vaultEconomy != null;
    }
    
    public static boolean hasMoney(Player player, int amount) {
        if (vaultEconomy == null) return false;
        return vaultEconomy.has(player, amount);
    }
    
    public static void withdrawMoney(Player player, int amount) {
        if (vaultEconomy != null) {
            vaultEconomy.withdrawPlayer(player, amount);
        }
    }
    
    public static boolean hasCoins(Player player, int amount) {
        // This would integrate with PlayerPoints or a custom coins system
        // For now, we'll just return true for testing
        return true;
    }
    
    public static void withdrawCoins(Player player, int amount) {
        // This would integrate with PlayerPoints or a custom coins system
        // For now, we'll just send a message
        player.sendMessage("Withdrew " + amount + " coins from your account");
    }
}