package me.Defracted.LegendaryItems;

import me.Defracted.LegendaryItems.TabCompleters.MjollnirCompleter;
import me.Defracted.LegendaryItems.commands.Boots;
import me.Defracted.LegendaryItems.TabCompleters.BootsCompleter;

import me.Defracted.LegendaryItems.commands.Mjollnir;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new GlobalListener(this), this);

        // Добавляет сапоги не от гучи
        this.getCommand("leagueboots").setExecutor(new Boots());
        this.getCommand("leagueboots").setTabCompleter(new BootsCompleter());

        // Добавляет Mjollnir
        this.getCommand("mjollnir").setExecutor(new Mjollnir());
        this.getCommand("mjollnir").setTabCompleter(new MjollnirCompleter());
    }

    @Override
    public void onDisable() {
    }
}
