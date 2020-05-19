package me.jalawaquin.playarea;

import me.jalawaquin.playarea.commands.deletePlayArea;
import me.jalawaquin.playarea.commands.setPlayArea;
import me.jalawaquin.playarea.events.PlayEvents;
import me.jalawaquin.playarea.listeners.PlotAreaListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayArea extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("PlayArea Plugin start");
        getCommand("setPlayArea").setExecutor(new setPlayArea());
        getCommand("deletePlayArea").setExecutor(new deletePlayArea());
        getServer().getPluginManager().registerEvents(new PlayEvents(),this);
        getServer().getPluginManager().registerEvents(new PlotAreaListener(), this);

    }
}
