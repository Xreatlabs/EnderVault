package com.plugin.endervaults;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.inventory.ClickType;

public class VaultListener implements Listener {
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event == null || !(event.getWhoClicked() instanceof Player)) return;
        
        Player player = (Player) event.getWhoClicked();
        if (player == null) return;
        
        Inventory inventory = event.getInventory();
        if (inventory == null) return;
        
        // Check if this is our vault GUI (handle version differences)
        String inventoryTitle = getInventoryTitle(inventory);
        if (inventoryTitle != null && inventoryTitle.contains("EnderVaults")) {
            event.setCancelled(true); // Prevent taking items
            
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
            
            ItemMeta itemMeta = clickedItem.getItemMeta();
            if (itemMeta == null) return;
            
            String displayName = itemMeta.getDisplayName();
            if (displayName == null) return;
            
            // Handle help GUI
            if (inventoryTitle.contains("Help") && displayName.contains("EnderVaults Commands")) {
                player.closeInventory();
                return;
            }
            
            // Handle vault list GUI
            if (inventoryTitle.contains("Your Vaults") && displayName.contains("Vault ")) {
                // Extract vault number from name (format: "§a§lVault X §a§l(UNLOCKED)" or "§c§lVault X §c§l(LOCKED)")
                String[] parts = displayName.split(" ");
                if (parts.length >= 2) {
                    try {
                        int vaultNumber = Integer.parseInt(parts[1]);
                        
                        if (displayName.contains("(UNLOCKED)")) {
                            // Open the vault
                            VaultManager.openVault(player, vaultNumber);
                            player.closeInventory();
                        } else if (displayName.contains("(LOCKED)")) {
                            // Attempt to buy the vault
                            VaultManager.buyVault(player, vaultNumber);
                            // Refresh the GUI to show updated status
                            // Use a delayed task to ensure the inventory is closed before reopening
                            Bukkit.getScheduler().runTaskLater(EnderVaults.getInstance(), () -> {
                                GUIManager.openVaultListGUI(player);
                            }, 1L);
                        }
                    } catch (NumberFormatException e) {
                        // Invalid vault number
                        player.sendMessage("§cInvalid vault number.");
                    } catch (Exception e) {
                        player.sendMessage("§cAn error occurred while processing your request.");
                    }
                }
            }
            
            // Handle info item in vault list GUI
            if (inventoryTitle.contains("Your Vaults") && displayName.contains("EnderVaults Info")) {
                player.closeInventory();
                player.sendMessage("§8[§6EnderVaults§8] §7Use §e/echest help §7for more information.");
            }
        }
    }
    
    // Handle version differences for getting inventory title
    private String getInventoryTitle(Inventory inventory) {
        if (inventory == null) return null;
        
        try {
            // For newer versions
            return inventory.getTitle();
        } catch (NoSuchMethodError e) {
            // For newer versions where getTitle() is deprecated
            try {
                if (!inventory.getViewers().isEmpty()) {
                    return inventory.getViewers().get(0).getOpenInventory().getTitle();
                }
            } catch (Exception ex) {
                // If all methods fail, return null
                return null;
            }
            return null;
        }
    }
}