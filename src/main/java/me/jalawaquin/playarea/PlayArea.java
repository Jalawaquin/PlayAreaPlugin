package me.jalawaquin.playarea;

import me.jalawaquin.playarea.commands.*;

import me.jalawaquin.playarea.events.PlayEvents;
import me.jalawaquin.playarea.listeners.PlotAreaListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayArea extends JavaPlugin {
    //inside and outside plot variables (playareainfo)
    PlayAreaInfo playAreaInfo = new PlayAreaInfo();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("deletePlayArea").setExecutor(new deletePlayArea(playAreaInfo));
        getCommand("getPlayAreaID").setExecutor(new getPlayAreaID(playAreaInfo));
        getCommand("insidePlayArea").setExecutor(new insidePlayArea(playAreaInfo));
        getCommand("outsidePlayArea").setExecutor(new outsidePlayArea(playAreaInfo));
        getCommand("setPlayArea").setExecutor(new setPlayArea());
        getCommand("turnPlayArea").setExecutor(new turnPlayArea(playAreaInfo));

        getServer().getPluginManager().registerEvents(new PlayEvents(playAreaInfo),this);
        getServer().getPluginManager().registerEvents(new PlotAreaListener(playAreaInfo), this);
    }
}
