# EnderVaults - Expandable Ender Chests

A Minecraft plugin that allows players to unlock additional ender chest pages (vaults) using in-game currency or premium coins.

## Features

- **Expandable Ender Chests**: Players start with 1 vault and can unlock up to 9 (configurable)
- **Dual Currency System**: Purchase vaults with in-game money (Vault) or premium coins (PlayerPoints/Custom)
- **GUI Interface**: Visual menu to view and manage unlocked vaults
- **Permission System**: Control access to vaults with fine-grained permissions
- **PlaceholderAPI Support**: Use `%endervaults_unlocked%` and `%endervaults_max_vaults%` in other plugins
- **Fully Configurable**: Customize costs, messages, and features
- **Multi-Version Support**: Compatible with Minecraft versions 1.8 through 1.21.8

## Commands

- `/echest` - Opens default ender chest (page 1)
- `/echest <number>` - Open specific unlocked vault
- `/echest list` - Opens GUI menu with all pages (locked/unlocked)
- `/echest buy <number>` - Buy the specified vault
- `/echest admin <sub>` - Admin controls (WIP)

## Permissions

- `endervaults.use` - allows /echest
- `endervaults.open.<number>` - allows opening specific vaults
- `endervaults.buy` - allows buying new vaults
- `endervaults.admin` - manage/reset other players' vaults

## Configuration

```yaml
# Vault configuration
vaults:
  1:
    cost-money: 0
    cost-coins: 0
  2:
    cost-money: 25000
    cost-coins: 0
  3:
    cost-money: 50000
    cost-coins: 10
  # ... more vaults

# GUI Settings
gui:
  title: "Your Ender Vaults"
  size: 27
  unlocked-material: GREEN_STAINED_GLASS_PANE
  locked-material: RED_STAINED_GLASS_PANE

# Economy Settings
economy:
  use-vault: true
  use-playerpoints: true
  use-custom-coins: false
```

## Requirements

- Spigot/Paper 1.8 or higher
- Vault (for economy integration)
- PlayerPoints (optional, for coin integration)
- PlaceholderAPI (optional, for placeholders)

## Installation

1. Download the latest release
2. Place the `.jar` file in your `plugins` folder
3. Restart your server
4. Configure the plugin in `plugins/EnderVaults/config.yml`
5. Restart the server again

## Building from Source

```bash
git clone https://github.com/xreatlabs/EnderVaults.git
cd EnderVaults
mvn clean package
```

The compiled jar will be in the `target` directory.

## Support

For issues, feature requests, or contributions, please visit our [GitHub repository](https://github.com/xreatlabs/EnderVaults).
