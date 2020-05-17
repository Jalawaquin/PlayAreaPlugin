package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.events.PlotAreaEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlotAreaListeners implements Listener {

    @EventHandler
    public void Print_location(PlotAreaEvent event){
        Player player = event.getPlayer();
        player.sendMessage("Location: ");
        player.sendMessage("X: " + event.getBlock_location().getBlockX());
        player.sendMessage("Y: " + event.getBlock_location().getBlockY());
        player.sendMessage("Z: " + event.getBlock_location().getBlockZ());
    }
}
