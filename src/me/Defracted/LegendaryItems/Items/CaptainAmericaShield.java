package me.Defracted.LegendaryItems.Items;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CaptainAmericaShield {
    public static ItemStack get() {
        ItemStack shield = new ItemStack(Material.SHIELD);
        ItemMeta shieldMeta = shield.getItemMeta();

        shieldMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lCaptain's Shield"));

        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7&oПРИСЕД. + ЛКМ — откинет всех вокуруг вас."));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7&oПассивная возможность — защита от любого урона."));
        lore.add("");

        shieldMeta.setLore(lore);
        shieldMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        shieldMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        shieldMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        shieldMeta.setUnbreakable(true);

        shield.setItemMeta(shieldMeta);
        return shield;
    }
}
