package me.jalawaquin.playarea;

import me.jalawaquin.playarea.commands.setPlayArea;
import me.jalawaquin.playarea.events.setPlayEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayArea extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("PlayArea Plugin start");
        getCommand("setPlayArea").setExecutor(new setPlayArea());
        getServer().getPluginManager().registerEvents(new setPlayEvents(),this);
    }
}
