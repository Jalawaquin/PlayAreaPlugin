package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.events.PlotAreaEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlotAreaListeners implements Listener {

    @EventHandler
    public void Print_location(PlotAreaEvent event){
        Player player = event.getPlayer();
        int num = event.getNum_of_locations();

        player.sendMessage("Location " + num + ": ");
        player.sendMessage("X: " + event.getBlock_location()[num].getBlockX());
        player.sendMessage("Y: " + event.getBlock_location()[num].getBlockY());
        player.sendMessage("Z: " + event.getBlock_location()[num].getBlockZ());
    }
}
