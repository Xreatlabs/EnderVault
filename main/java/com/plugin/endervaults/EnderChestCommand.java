package com.plugin.endervaults;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players.");
            return true;
        }
        
        Player player = (Player) sender;
        
        // Check if player has permission to use the command
        if (!player.hasPermission("endervaults.use")) {
            player.sendMessage("§cYou don't have permission to use this command.");
            return true;
        }
        
        // If no arguments, open default vault (page 1)
        if (args.length == 0) {
            VaultManager.openVault(player, 1);
            return true;
        }
        
        // Handle different subcommands
        switch (args[0].toLowerCase()) {
            case "list":
                // No additional permission check needed for list - already checked above
                GUIManager.openVaultListGUI(player);
                return true;
            case "buy":
                if (!player.hasPermission("endervaults.buy")) {
                    player.sendMessage("§cYou don't have permission to buy vaults.");
                    return true;
                }
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /echest buy <number>");
                    return true;
                }
                try {
                    int vaultNumber = Integer.parseInt(args[1]);
                    VaultManager.buyVault(player, vaultNumber);
                } catch (NumberFormatException e) {
                    player.sendMessage("§cInvalid vault number.");
                }
                return true;
            case "help":
                // Check if player wants GUI help or text help
                if (args.length > 1 && args[1].equalsIgnoreCase("gui")) {
                    GUIManager.openHelpGUI(player);
                } else {
                    // Show help information
                    sendHelpMessage(player);
                }
                return true;
            case "admin":
                if (!player.hasPermission("endervaults.admin")) {
                    player.sendMessage("§cYou don't have permission to use admin commands.");
                    return true;
                }
                // Handle admin commands
                AdminCommandHandler.handleAdminCommand(player, args);
                return true;
            default:
                // Try to open specified vault number
                try {
                    int vaultNumber = Integer.parseInt(args[0]);
                    VaultManager.openVault(player, vaultNumber);
                } catch (NumberFormatException e) {
                    player.sendMessage("§cInvalid command. Use /echest help for available commands.");
                }
                return true;
        }
    }
    
    private void sendHelpMessage(Player player) {
        player.sendMessage("§8§m---------------§r §8[§6EnderVaults§8] §8§m---------------");
        player.sendMessage("§e/echest §7- Open your default vault (vault 1)");
        player.sendMessage("§e/echest <number> §7- Open a specific vault");
        player.sendMessage("§e/echest list §7- Open the vault selection GUI");
        player.sendMessage("§e/echest buy <number> §7- Purchase a vault");
        player.sendMessage("§e/echest help §7- Show this help message");
        player.sendMessage("§e/echest help gui §7- Open help GUI");
        player.sendMessage("§8§m----------------------------------------");
    }
}