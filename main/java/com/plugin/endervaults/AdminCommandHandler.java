package com.plugin.endervaults;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommandHandler {
    
    public static void handleAdminCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage("§cUsage: /echest admin <subcommand>");
            player.sendMessage("§cSubcommands: reset <player>, give <player> <vault>");
            return;
        }
        
        switch (args[1].toLowerCase()) {
            case "reset":
                if (args.length < 3) {
                    player.sendMessage("§cUsage: /echest admin reset <player>");
                    return;
                }
                resetPlayerVaults(player, args[2]);
                break;
            case "give":
                if (args.length < 4) {
                    player.sendMessage("§cUsage: /echest admin give <player> <vault>");
                    return;
                }
                givePlayerVault(player, args[2], args[3]);
                break;
            default:
                player.sendMessage("§cUnknown subcommand. Use /echest admin for help.");
                break;
        }
    }
    
    private static void resetPlayerVaults(Player sender, String playerName) {
        // In a real implementation, this would reset the player's vault data
        sender.sendMessage("§aReset vault data for player " + playerName);
    }
    
    private static void givePlayerVault(Player sender, String playerName, String vaultNumberStr) {
        try {
            int vaultNumber = Integer.parseInt(vaultNumberStr);
            // In a real implementation, this would give the player the specified vault
            sender.sendMessage("§aGave vault " + vaultNumber + " to player " + playerName);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid vault number.");
        }
    }
}