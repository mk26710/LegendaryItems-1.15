package me.Defracted.LegendaryItems.commands;

import me.Defracted.LegendaryItems.Items.SevenLeagueBoots;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Boots implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("leagueboots")) {

            Player issuer = (Player) sender;
            PlayerInventory inventory = issuer.getInventory();
            Location location = issuer.getLocation();
            World world = issuer.getWorld();

            if (inventory.firstEmpty() == -1) {
                world.dropItemNaturally(location, SevenLeagueBoots.get());
                issuer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&8&oРак на горе свистнул, а рядом с вами упали какие-то ботолы, даже лучше гучи..."));
            } else {
                inventory.addItem(SevenLeagueBoots.get());
                issuer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&8&oРак на горе свистнул, а вы получили какие-то ботолы, даже лучше гучи..."));
            }

            issuer.playSound(location, Sound.ITEM_TOTEM_USE, 1f, 1f);


            return true;
        }

        return false;
    }
}
