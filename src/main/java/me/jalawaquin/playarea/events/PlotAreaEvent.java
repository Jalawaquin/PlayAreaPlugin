package me.jalawaquin.playarea.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;


public class PlotAreaEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private static List<Block> blocks = new ArrayList<>();
    private Player player;
    private Location loc1,loc2;

    public PlotAreaEvent(){
        this.blocks.clear();
        this.loc1 = null;
        this.loc2 = null;
    }
    public PlotAreaEvent(Player player_, Location loc1_, Location loc2_){
        this.player = player_;
        this.loc1 = loc1_;
        this.loc2 = loc2_;
    }

    public void setPlotArea(){
        if (loc1.getWorld() != loc2.getWorld()){
            player.sendMessage(ChatColor.RED + "Cannot set Play Area across worlds");
            throw new IllegalArgumentException("Cannot set Play Area across worlds");
        }

        World world = loc1.getWorld();

        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        for (int x = minX; x <= maxX; x++){
            for(int y = minY;  y <= maxY; y++){
                for(int z = minZ; z <= maxZ; z++){
                    blocks.add(world.getBlockAt(x,y,z));
                }
            }
        }
        player.sendMessage("Plot set");
    }

    public Player getPlayer() {
        return player;
    }

    public static List<Block> getBlocks() { return blocks; }

    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }

    //----------------------------------------------------------------------
    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
