package me.jalawaquin.playarea;

import me.jalawaquin.playarea.commands.deletePlayArea;
import me.jalawaquin.playarea.commands.insidePlayArea;
import me.jalawaquin.playarea.commands.outsidePlayArea;
import me.jalawaquin.playarea.commands.turnPlayArea;
import me.jalawaquin.playarea.commands.setPlayArea;

import me.jalawaquin.playarea.events.PlayEvents;
import me.jalawaquin.playarea.listeners.PlotAreaListener;
import me.jalawaquin.playarea.settings.PlayAreaMessageSettings;
import me.jalawaquin.playarea.settings.PlayAreaPotionSettings;
import me.jalawaquin.playarea.settings.Plots;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayArea extends JavaPlugin {
    private Plots plot = new Plots();
    private PlayAreaPotionSettings potionSettings = new PlayAreaPotionSettings();
    private PlayAreaMessageSettings messageSettings = new PlayAreaMessageSettings();
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("PlayArea Plugin start");

        getCommand("deletePlayArea").setExecutor(new deletePlayArea(plot, potionSettings, messageSettings));
        getCommand("insidePlayArea").setExecutor(new insidePlayArea(plot, potionSettings, messageSettings));
        getCommand("outsidePlayArea").setExecutor(new outsidePlayArea(plot, potionSettings, messageSettings));
        getCommand("setPlayArea").setExecutor(new setPlayArea());
        getCommand("turnPlayArea").setExecutor(new turnPlayArea(plot, potionSettings, messageSettings));

        getServer().getPluginManager().registerEvents(new PlayEvents(plot),this);
        getServer().getPluginManager().registerEvents(new PlotAreaListener(plot, potionSettings, messageSettings), this);
    }

}
