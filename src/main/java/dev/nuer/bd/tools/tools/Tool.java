package dev.nuer.bd.tools.tools;

import dev.nuer.bd.tools.managers.FileManager;
import dev.nuer.bd.tools.utils.ItemBuilderUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Tool {
    private int configID;
    private ToolType type;
    private int delay;
    private int element;
    private HashMap<Material, Short> bannedItems;

    public Tool(int configID, ToolType type, int delay) {
        //Store instance variables
        this.configID = configID;
        this.type = type;
        this.delay = delay;
        //Run switch for different tool types
        switch (type) {
            case INVENTORY:
                this.element = FileManager.get("config").getInt("tools." + configID + ".look-time");
                break;
            case BLINDNESS:
                this.element = FileManager.get("config").getInt("tools." + configID + ".effect-radius");
                break;
            case JAIL:
                this.bannedItems = new HashMap<>();
                for (String cfgMat : FileManager.get("config").getStringList("tools." + configID + ".banned-items")) {
                    String[] material = cfgMat.split(":");
                    this.bannedItems.put(Material.valueOf(material[0].toUpperCase()), Short.parseShort(material[1]));
                }
                break;
            default:
                this.element = -69;
                break;
        }
    }

    public void givePlayer(Player player, String material, String dataValue) {
        ItemBuilderUtil item = new ItemBuilderUtil(material, dataValue);
        YamlConfiguration config = FileManager.get("config");
        item.addName(config.getString("tools." + config + ".name"));
        item.addLore(config.getStringList("tools." + config + ".lore"));
        item.addEnchantments(config.getStringList("tools." + config + ".enchantments"));
        item.addItemFlags(config.getStringList("tools." + config + ".item-flags"));
        item.addNBT(configID, type);
        player.getInventory().addItem(item.getNbtItem().getItem());
    }

    public int getConfigID() {
        return configID;
    }

    public ToolType getType() {
        return type;
    }

    public int getDelay() {
        return delay;
    }

    public int getElement() {
        return element;
    }

    public HashMap<Material, Short> getBannedItems() {
        return bannedItems;
    }
}