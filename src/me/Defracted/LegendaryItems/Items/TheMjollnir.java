package me.Defracted.LegendaryItems.Items;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TheMjollnir {
    public static ItemStack get() {
        // Создаём топорик и его метаданные
        ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
        ItemMeta axeMeta = axe.getItemMeta();

        // Задаём кастомное название
        axeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lThe Mjöllnir"));

        // Задаём лор предмета
        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.translateAlternateColorCodes('&', "&5&oУничтажает ваших врагов."));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7&oПРИСЕД. + ЛКМ — отправить огненный шар дракона Энда."));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7&oПКМ — сокрушить удар молний."));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Урон: &c+12"));
        lore.add("");
        axeMeta.setLore(lore);

        // Добавляем зачар на остроту
        axeMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);

        // Прячем флаги и делаем неломаемым
        axeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        axeMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        axeMeta.setUnbreakable(true);

        // Применяем метаданные к топорику
        axe.setItemMeta(axeMeta);
        return axe;
    }
}
