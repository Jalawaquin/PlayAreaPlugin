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
    //put inside plots class
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("PlayArea Plugin start");

        getCommand("deletePlayArea").setExecutor(new deletePlayArea(plot));
        getCommand("insidePlayArea").setExecutor(new insidePlayArea(plot));
        getCommand("outsidePlayArea").setExecutor(new outsidePlayArea(plot));
        getCommand("setPlayArea").setExecutor(new setPlayArea());
        getCommand("turnPlayArea").setExecutor(new turnPlayArea(plot));

        getServer().getPluginManager().registerEvents(new PlayEvents(plot),this);
        getServer().getPluginManager().registerEvents(new PlotAreaListener(plot), this);
    }

}
