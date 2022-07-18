package me.jalawaquin.playarea.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.UUID;


public class PlotAreaEvent extends Event {
    //NOTE: Look up whatever the handlers list does
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Location loc1,loc2;

    public PlotAreaEvent(Player player_, Location loc1_, Location loc2_){
        this.player = player_;
        this.loc1 = loc1_;
        this.loc2 = loc2_;
    }

    public HashMap<String, UUID> getPlotArea(){
        HashMap<String, UUID> blocks = new HashMap<>();

        if (loc1.getWorld() != loc2.getWorld()){
            player.sendMessage(ChatColor.RED + "Cannot set Play Area across worlds");
            throw new IllegalArgumentException("Cannot set Play Area across worlds");
        }

        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());

        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        for (int x = minX; x <= maxX; x++){
            for(int z = minZ; z <= maxZ; z++){
                String blockID = x + "." + z;

                blocks.put(blockID, player.getUniqueId());
            }
        }

        return blocks;
    }

    public Player getPlayer() {
        return player;
    }

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
