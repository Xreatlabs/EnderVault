package com.plugin.endervaults;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class VaultManager {
    
    public static void openVault(Player player, int vaultNumber) {
        // Check if player has permission to open this vault
        if (!player.hasPermission("endervaults.open." + vaultNumber)) {
            player.sendMessage("You don't have permission to open vault " + vaultNumber);
            return;
        }
        
        // Check if player has unlocked this vault
        if (vaultNumber > getUnlockedVaults(player)) {
            player.sendMessage("You haven't unlocked vault " + vaultNumber + " yet. Use /echest list to see available vaults.");
           // TODO: Implement auto-purchase prompt if enabled
            return;
        }
        
        // Open the ender chest inventory for the player
        // In a real implementation, we would associate this with the specific vault number
        player.openInventory(player.getEnderChest());
        player.sendMessage("Opened vault " + vaultNumber);
    }
    
    public static int getUnlockedVaults(Player player) {
        // Retrieve from data store
        return DataManager.getPlayerData(player).getUnlockedVaults();
    }
    
    public static void buyVault(Player player, int vaultNumber) {
        // Check if player has permission to buy vaults
        if (!player.hasPermission("endervaults.buy")) {
            player.sendMessage("You don't have permission to buy vaults.");
            return;
        }
        
        // Check if vault is already unlocked
        if (vaultNumber <= getUnlockedVaults(player)) {
            player.sendMessage("You already own vault " + vaultNumber);
            return;
        }
        
        // Check if this is the next vault to be unlocked
        if (vaultNumber > getUnlockedVaults(player) + 1) {
            player.sendMessage("You must unlock vaults in order. Unlock vault " + (getUnlockedVaults(player) + 1) + " first.");
            return;
        }
        
        // Get costs for this vault
        int moneyCost = getMoneyCost(vaultNumber);
        int coinsCost = getCoinsCost(vaultNumber);
        
        // Try to purchase with money first, then coins
        if (moneyCost > 0 && EconomyManager.hasMoney(player, moneyCost)) {
            EconomyManager.withdrawMoney(player, moneyCost);
            unlockVault(player, vaultNumber);
            player.sendMessage("Successfully purchased vault " + vaultNumber + " for $" + moneyCost);
        } else if (coinsCost > 0 && EconomyManager.hasCoins(player, coinsCost)) {
            EconomyManager.withdrawCoins(player, coinsCost);
            unlockVault(player, vaultNumber);
            player.sendMessage("Successfully purchased vault " + vaultNumber + " for " + coinsCost + " coins");
        } else {
            player.sendMessage("You don't have enough money or coins to purchase this vault.");
        }
    }
    
    private static void unlockVault(Player player, int vaultNumber) {
        // Update the player's data
        PlayerData data = DataManager.getPlayerData(player);
        data.unlockVault();
        DataManager.setPlayerData(player, data);
        
        // Play sound effect (handle version differences)
        playUnlockSound(player);
        player.sendMessage("Vault " + vaultNumber + " unlocked!");
        // TODO: Add particle effects
    }
    
    private static void playUnlockSound(Player player) {
        try {
            // For newer versions
            player.playSound(player.getLocation(), "BLOCK_NOTE_BLOCK_CHIME", 1.0f, 1.0f);
        } catch (IllegalArgumentException e) {
            // For older versions
            try {
                player.playSound(player.getLocation(), "NOTE_PIANO", 1.0f, 1.0f);
            } catch (IllegalArgumentException e2) {
                // If both fail, silently ignore
            }
        }
    }
    
    public static int getMoneyCost(int vaultNumber) {
        // Retrieve from config
        return EnderVaults.getInstance().getConfig().getInt("vaults." + vaultNumber + ".cost-money", 0);
    }
    
    public static int getCoinsCost(int vaultNumber) {
        // Retrieve from config
        return EnderVaults.getInstance().getConfig().getInt("vaults." + vaultNumber + ".cost-coins", 0);
    }
}