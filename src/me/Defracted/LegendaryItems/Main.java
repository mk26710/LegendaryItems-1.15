package me.Defracted.LegendaryItems;

import me.Defracted.LegendaryItems.TabCompleters.CapShieldCompleter;
import me.Defracted.LegendaryItems.TabCompleters.MjollnirCompleter;
import me.Defracted.LegendaryItems.commands.Boots;
import me.Defracted.LegendaryItems.TabCompleters.BootsCompleter;

import me.Defracted.LegendaryItems.commands.CapShield;
import me.Defracted.LegendaryItems.commands.Mjollnir;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {
    private void loadCommand(String command, CommandExecutor Executor, TabCompleter Completer) {
        Objects.requireNonNull(getCommand(command)).setExecutor(Executor);
        Objects.requireNonNull(getCommand(command)).setTabCompleter(Completer);
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new GlobalListener(this), this);

        loadCommand("leagueboots", new Boots(), new BootsCompleter());
        loadCommand("mjollnir", new Mjollnir(), new MjollnirCompleter());
        loadCommand("capshield", new CapShield(), new CapShieldCompleter());
    }

    @Override
    public void onDisable() {
    }
}
