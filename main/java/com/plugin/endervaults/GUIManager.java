package com.plugin.endervaults;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUIManager {
    
    public static void openVaultListGUI(Player player) {
        // Create GUI with 9 slots per row, 3 rows
        Inventory gui = Bukkit.createInventory(null, 27, "§8[§6EnderVaults§8] Your Vaults");
        
        // Get player's unlocked vaults
        int unlockedVaults = VaultManager.getUnlockedVaults(player);
        
        // Add vault items to GUI
        for (int i = 1; i <= 9; i++) {
            ItemStack vaultItem;
            ItemMeta meta;
            
            if (i <= unlockedVaults) {
                // Unlocked vault - emerald block for visual appeal
                vaultItem = new ItemStack(Material.EMERALD_BLOCK);
                meta = vaultItem.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName("§a§lVault " + i + " §a§l(UNLOCKED)");
                    List<String> lore = new ArrayList<>();
                    lore.add("§7Status: §aUnlocked");
                    lore.add("§7");
                    lore.add("§7Click to open this vault");
                    lore.add("§7");
                    lore.add("§e§lLEFT CLICK: §fOpen Vault");
                    meta.setLore(lore);
                }
            } else {
                // Locked vault - redstone block for visual appeal
                vaultItem = new ItemStack(Material.REDSTONE_BLOCK);
                meta = vaultItem.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName("§c§lVault " + i + " §c§l(LOCKED)");
                    List<String> lore = new ArrayList<>();
                    lore.add("§7Status: §cLocked");
                    lore.add("§7");
                    // Get cost for this vault
                    int moneyCost = VaultManager.getMoneyCost(i);
                    int coinsCost = VaultManager.getCoinsCost(i);
                    if (moneyCost > 0) {
                        lore.add("§7Cost: §6$" + moneyCost);
                    }
                    if (coinsCost > 0) {
                        lore.add("§7Cost: §e" + coinsCost + " Coins");
                    }
                    if (moneyCost > 0 && coinsCost > 0) {
                        lore.add("§7");
                        lore.add("§e§lLEFT CLICK: §fPurchase with Money");
                        lore.add("§e§lRIGHT CLICK: §fPurchase with Coins");
                    } else if (moneyCost > 0) {
                        lore.add("§7");
                        lore.add("§e§lLEFT CLICK: §fPurchase");
                    } else if (coinsCost > 0) {
                        lore.add("§7");
                        lore.add("§e§lLEFT CLICK: §fPurchase");
                    }
                    meta.setLore(lore);
                }
            }
            
            if (meta != null) {
                vaultItem.setItemMeta(meta);
            }
            gui.setItem(i - 1, vaultItem);
        }
        
        // Add decorative items
        ItemStack decoration = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta decorationMeta = decoration.getItemMeta();
        if (decorationMeta != null) {
            decorationMeta.setDisplayName(" ");
            decoration.setItemMeta(decorationMeta);
        }
        
        // Fill empty slots with decorations
        for (int i = 9; i < 27; i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, decoration.clone());
            }
        }
        
        // Add info item
        ItemStack infoItem = new ItemStack(Material.BOOK);
        ItemMeta infoMeta = infoItem.getItemMeta();
        if (infoMeta != null) {
            infoMeta.setDisplayName("§b§lEnderVaults Info");
            List<String> infoLore = new ArrayList<>();
            infoLore.add("§7Unlocked Vaults: §a" + unlockedVaults + "§7/§a9");
            infoLore.add("§7");
            infoLore.add("§7Use §e/echest help §7for commands");
            infoLore.add("§7Use §e/echest buy <#> §7to purchase");
            infoMeta.setLore(infoLore);
        }
        infoItem.setItemMeta(infoMeta);
        gui.setItem(22, infoItem);
        
        player.openInventory(gui);
    }
    
    public static void openHelpGUI(Player player) {
        // Create GUI with 9 slots per row, 3 rows
        Inventory gui = Bukkit.createInventory(null, 27, "§8[§6EnderVaults§8] Help");
        
        // Add help items
        ItemStack helpItem = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta helpMeta = helpItem.getItemMeta();
        if (helpMeta != null) {
            helpMeta.setDisplayName("§b§lEnderVaults Commands");
            List<String> lore = new ArrayList<>();
            lore.add("§7Main command: §e/echest");
            lore.add("§7");
            lore.add("§e/echest §7- Open vault 1");
            lore.add("§e/echest <#> §7- Open specific vault");
            lore.add("§e/echest list §7- Open vault selection GUI");
            lore.add("§e/echest buy <#> §7- Buy a vault");
            lore.add("§e/echest help §7- Show this help");
            lore.add("§7");
            lore.add("§7Permissions:");
            lore.add("§eendervaults.use §7- Use /echest");
            lore.add("§eendervaults.buy §7- Buy vaults");
            lore.add("§eendervaults.admin §7- Admin commands");
            helpMeta.setLore(lore);
        }
        helpItem.setItemMeta(helpMeta);
        gui.setItem(13, helpItem);
        
        // Add decorative items
        ItemStack decoration = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta decorationMeta = decoration.getItemMeta();
        if (decorationMeta != null) {
            decorationMeta.setDisplayName(" ");
            decoration.setItemMeta(decorationMeta);
        }
        
        // Fill empty slots with decorations
        for (int i = 0; i < 27; i++) {
            if (i != 13) { // Skip the help item slot
                gui.setItem(i, decoration.clone());
            }
        }
        
        player.openInventory(gui);
    }
}