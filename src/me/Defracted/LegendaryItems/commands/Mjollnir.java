package me.Defracted.LegendaryItems.commands;

import me.Defracted.LegendaryItems.Items.TheMjollnir;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Mjollnir implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mjollnir")) {
            if (!(sender instanceof Player)) {
                return true;
            }

            Player issuer = (Player) sender;
            World world = issuer.getWorld();
            Location location = issuer.getLocation();
            PlayerInventory inventory = issuer.getInventory();

            if (inventory.firstEmpty() == -1) {
                world.dropItemNaturally(location, TheMjollnir.get());
            } else {
                inventory.addItem(TheMjollnir.get());
            }

            issuer.playSound(location, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1f, 1f);
            issuer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&8&oКто-то птичка, а вы молния..."));


            return true;
        }

        return false;
    }
}
