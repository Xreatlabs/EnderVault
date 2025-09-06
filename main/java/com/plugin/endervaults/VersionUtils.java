package com.plugin.endervaults;

import org.bukkit.Bukkit;

public class VersionUtils {
    
    private static String serverVersion;
    private static int majorVersion = -1;
    
    static {
        try {
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            serverVersion = packageName.substring(packageName.lastIndexOf('.') + 1);
            
            // Extract version number from package name like "v1_16_R3"
            if (serverVersion.startsWith("v")) {
                String[] parts = serverVersion.substring(1).split("_");
                majorVersion = Integer.parseInt(parts[0]);
            } else {
                // For newer versions like "1.16.5"
                String bukkitVersion = Bukkit.getBukkitVersion();
                String[] parts = bukkitVersion.split("\\.");
                majorVersion = Integer.parseInt(parts[1]);
            }
        } catch (Exception e) {
            majorVersion = 8; // Default to 1.8 if we can't determine version
        }
    }
    
    public static String getServerVersion() {
        return serverVersion;
    }
    
    public static int getMajorVersion() {
        if (majorVersion == -1) {
            try {
                // Extract version number from package name like "v1_16_R3"
                String version = getServerVersion();
                if (version.startsWith("v")) {
                    String[] parts = version.substring(1).split("_");
                    majorVersion = Integer.parseInt(parts[0]);
                } else {
                    // For newer versions like "1.16.5"
                    String bukkitVersion = Bukkit.getBukkitVersion();
                    String[] parts = bukkitVersion.split("\\.");
                    majorVersion = Integer.parseInt(parts[1]);
                }
            } catch (Exception e) {
                majorVersion = 8; // Default to 1.8 if we can't determine version
            }
        }
        return majorVersion;
    }
    
    public static boolean isLegacyVersion() {
        return getMajorVersion() < 13;
    }
    
    public static boolean isModernVersion() {
        return getMajorVersion() >= 13;
    }
}