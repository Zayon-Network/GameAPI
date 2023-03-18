package de.nehlen.gameapi.ItemsAPI;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.UUID;

public class Items {

    /**
     * @param material
     * @param displayname
     * @param amount
     * @return
     */
    public static ItemStack createItem(Material material, String displayname, int amount) {

        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayname);
        item.setItemMeta(itemMeta);

        return item;
    }

    /**
     * @param item
     * @param displayname
     * @return
     */
    public static ItemStack setDisplayName(ItemStack item, String displayname) {

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayname);
        item.setItemMeta(itemMeta);

        return item;
    }

    /**
     * @param item
     * @return
     */
    public static ItemStack setMaxDurability(ItemStack item) {

        int max = item.getType().getMaxDurability();

        ItemMeta itemMeta = item.getItemMeta();
        ((Damageable) itemMeta).setDamage(0);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * @param material
     * @param displayname
     * @param lore
     * @param amount
     * @return
     */
    public static ItemStack createLore(Material material, String displayname, ArrayList<String> lore, int amount) {

        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(displayname);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * @param material
     * @param displayname
     * @param lore
     * @param amount
     * @return
     */
    public static ItemStack createLore(Material material, String displayname, String lore, int amount) {

        ArrayList<String> loreL = new ArrayList<String>();
        loreL.add(lore);

        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(loreL);
        itemMeta.setDisplayName(displayname);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * @param m
     * @param displayname
     * @param color
     * @param unbreakable
     * @param amount
     * @return
     */
    public static ItemStack createLeatherArmor(Material m, String displayname, Color color, Boolean unbreakable, int amount) {

        ItemStack i = new ItemStack(m, amount);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) i.getItemMeta();
        itemMeta.setDisplayName(displayname);
        itemMeta.setColor(color);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(unbreakable);
        i.setItemMeta(itemMeta);

        return i;
    }

    /**
     * @param material
     * @param displayname
     * @param amount
     * @param enchantment
     * @param level
     * @return
     */
    public static ItemStack createEnchantment(Material material, String displayname, int amount, Enchantment enchantment, int level) {

        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(displayname);
        item.setItemMeta(itemMeta);

        return item;

    }


    /**
     * @param Display
     * @param Owner
     * @return
     */
    public static ItemStack createSkull(String Display, UUID Owner) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta itemMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.PLAYER_HEAD);

        itemMeta.setOwningPlayer(Bukkit.getOfflinePlayer(Owner));
        itemMeta.setDisplayName(Display);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * @param type
     * @param level
     * @param extend
     * @param upgraded
     * @param displayName
     * @return
     */
    public static ItemStack createPotion(PotionType type, int level, boolean extend, boolean upgraded, String displayName) {
        ItemStack potion = new ItemStack(Material.POTION, 1);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.setBasePotionData(new PotionData(type, extend, upgraded));
        potion.setItemMeta(meta);
        return potion;
    }


}