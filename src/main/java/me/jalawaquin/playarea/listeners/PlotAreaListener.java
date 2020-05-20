package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.events.PlotAreaEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class PlotAreaListener implements Listener {
    private static List<Block> blocks = new ArrayList<>();

    @EventHandler
    public void PlotSave(PlotAreaEvent event){
        event.setPlotArea();
        this.blocks = event.getBlocks();

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        if (blocks.contains(event.getTo().getBlock()) && !blocks.contains(event.getFrom().getBlock())){
           player.sendMessage("You are now entering the play area.");
        }
        else if (!blocks.contains(event.getTo().getBlock()) && blocks.contains(event.getFrom().getBlock())){
            player.sendMessage("You are now leaving the play area");
        }
    }

    public void deletePlot(){
        this.blocks.clear();
    }
}
