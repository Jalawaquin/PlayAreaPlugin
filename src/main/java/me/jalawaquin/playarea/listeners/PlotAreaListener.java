package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.events.PlotAreaEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.UUID;

public class PlotAreaListener implements Listener {
    private static HashMap<String, UUID> blocks = new HashMap<>();

    @EventHandler
    public void PlotSave(PlotAreaEvent event){
        this.blocks = event.getPlotArea();
        event.getPlayer().sendMessage( "Plot set");

    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        String currentBlockTo = event.getTo().getBlockX() + "." + event.getTo().getBlockZ();
        String currentBlockFrom = event.getFrom().getBlockX() + "." + event.getFrom().getBlockZ();

        if (blocks.containsKey(currentBlockTo) && !blocks.containsKey(currentBlockFrom)){
            player.sendMessage("You are now entering the play area.");
        }
        else if (!blocks.containsKey(currentBlockTo) && blocks.containsKey(currentBlockFrom)){
            player.sendMessage("You are now leaving the play area");
        }
    }

    public void deletePlot(){
        this.blocks.clear();
    }
}
