package com.plugin.endervaults;

public class PlayerData {
    private int unlockedVaults;
    
    public PlayerData() {
        // By default, players start with 1 unlocked vault
        this.unlockedVaults = 1;
    }
    
    public int getUnlockedVaults() {
        return unlockedVaults;
    }
    
    public void setUnlockedVaults(int unlockedVaults) {
        this.unlockedVaults = unlockedVaults;
    }
    
    public void unlockVault() {
        this.unlockedVaults++;
    }
}