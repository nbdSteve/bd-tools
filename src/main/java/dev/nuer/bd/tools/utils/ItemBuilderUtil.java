package dev.nuer.bd.tools.utils;

import dev.nuer.bd.tools.support.nbtapi.NBTItem;
import dev.nuer.bd.tools.tools.ToolType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * Class that handles creating and item
 */
public class ItemBuilderUtil {
    private NBTItem nbtItem;
    private Material material;
    private Short dataValue;
    private ItemStack item;
    private ItemMeta itemMeta;
    private List<String> lore = new ArrayList<>();
    private Map<Enchantment, Integer> enchantments = new HashMap<>();
    private Set<ItemFlag> flags = new HashSet<>();

    public ItemBuilderUtil(ItemStack item) {
        this.item = item;
        this.material = item.getType();
        this.dataValue = item.getDurability();
        this.itemMeta = item.getItemMeta();
        this.lore = item.getItemMeta().getLore();
        this.enchantments = item.getEnchantments();
        this.flags = item.getItemMeta().getItemFlags();
    }

    public ItemBuilderUtil(String material, String dataValue) {
        this.material = Material.getMaterial(material.toUpperCase());
        this.dataValue = Short.parseShort(dataValue);
        this.item = new ItemStack(this.material, 1, this.dataValue);
        this.itemMeta = item.getItemMeta();
    }

    public void addName(String name) {
        itemMeta.setDisplayName(ColorUtil.colorize(name));
        item.setItemMeta(itemMeta);
    }

    public void addLore(List<String> lore) {
        for (String line : lore) {
            this.lore.add(ColorUtil.colorize(line));
        }
        itemMeta.setLore(this.lore);
        item.setItemMeta(itemMeta);
    }

    public void replaceLorePlaceholder(String placeholder, String replacement) {
        for (int i = 0; i < this.lore.size(); i++) {
            this.lore.set(i, lore.get(i).replace(placeholder, replacement));
        }
        this.itemMeta.setLore(this.lore);
    }

    public void addEnchantments(List<String> enchants) {
        for (String enchantment : enchants) {
            String[] enchantmentParts = enchantment.split(":");
            itemMeta.addEnchant(Enchantment.getByName(enchantmentParts[0].toUpperCase()),
                    Integer.parseInt(enchantmentParts[1]), true);
            this.enchantments.put(Enchantment.getByName(enchantmentParts[0].toUpperCase()),
                    Integer.parseInt(enchantmentParts[1]));
        }
        item.setItemMeta(itemMeta);
    }

    public void addItemFlags(List<String> itemFlags) {
        for (String flag : itemFlags) {
            itemMeta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
            this.flags.add(ItemFlag.valueOf(flag.toUpperCase()));
        }
        item.setItemMeta(itemMeta);
    }

    public void addNBT(int configID, ToolType type) {
        this.nbtItem = new NBTItem(item);
        this.nbtItem.setBoolean("bd-tool", true);
        this.nbtItem.setInteger("bd-tools.config-id", configID);
        this.nbtItem.setObject("bd-tools.type", type);
    }

    public NBTItem getNbtItem() {
        return nbtItem;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemMeta getItemMeta() {
        return itemMeta;
    }

    public List<String> getLore() {
        return lore;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public Set<ItemFlag> getFlags() {
        return flags;
    }
}