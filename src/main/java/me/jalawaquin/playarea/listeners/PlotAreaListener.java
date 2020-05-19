package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.events.PlotAreaEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlotAreaListener implements Listener {

    @EventHandler
    public void PlotSave(PlotAreaEvent event){
        Player player = event.getPlayer();

        player.sendMessage("Successfully set plot");
    }
}
