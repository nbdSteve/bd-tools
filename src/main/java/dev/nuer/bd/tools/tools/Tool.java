package dev.nuer.bd.tools.tools;

import com.sun.org.apache.xerces.internal.xs.StringList;
import dev.nuer.bd.tools.managers.FileManager;
import dev.nuer.bd.tools.utils.ItemBuilderUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tool {
    private int configID;
    private ToolType type;
    private int delay;
    private ArrayList<Integer> effectAttributes;
    private HashMap<Material, Short> bannedItems;
    private ArrayList<String> commands;

    public Tool(int configID, ToolType type, int delay, List<String> commands) {
        //Store instance variables
        this.configID = configID;
        this.type = type;
        this.delay = delay;
        this.commands = new ArrayList<>(commands);
        //Run switch for different tool types
        switch (type) {
            case BLINDNESS:
                effectAttributes = new ArrayList<>();
                effectAttributes.add(FileManager.get("config").getInt("tools." + configID + ".effect.radius"));
                effectAttributes.add(FileManager.get("config").getInt("tools." + configID + ".effect.duration"));
                effectAttributes.add(FileManager.get("config").getInt("tools." + configID + ".effect.amplifier") - 1);
                break;
            case JAIL:
                this.bannedItems = new HashMap<>();
                for (String cfgMat : FileManager.get("config").getStringList("tools." + configID + ".banned-items")) {
                    String[] material = cfgMat.split(":");
                    this.bannedItems.put(Material.valueOf(material[0].toUpperCase()), Short.parseShort(material[1]));
                }
                break;
            default:
                break;
        }
    }

    public void givePlayer(Player player, String material, String dataValue) {
        ItemBuilderUtil item = new ItemBuilderUtil(material, dataValue);
        YamlConfiguration config = FileManager.get("config");
        item.addName(config.getString("tools." + this.configID + ".name"));
        item.addLore(config.getStringList("tools." + this.configID + ".lore"));
        item.addEnchantments(config.getStringList("tools." + this.configID + ".enchantments"));
        item.addItemFlags(config.getStringList("tools." + this.configID + ".item-flags"));
        item.addNBT(this.configID, type);
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

    public ArrayList<Integer> getEffectAttributes() {
        return effectAttributes;
    }

    public HashMap<Material, Short> getBannedItems() {
        return bannedItems;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }
}