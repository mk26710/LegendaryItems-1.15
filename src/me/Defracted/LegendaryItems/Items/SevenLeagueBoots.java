package me.Defracted.LegendaryItems.Items;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SevenLeagueBoots {
    public static ItemStack get() {
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();

        bootsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lSeven League Boots"));

        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&5&oПозволяют прыгать на нереальные расстояния блин"));
        lore.add("");

        bootsMeta.setLore(lore);
        bootsMeta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
        bootsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        bootsMeta.setUnbreakable(true);

        boots.setItemMeta(bootsMeta);
        return boots;
    }
}
