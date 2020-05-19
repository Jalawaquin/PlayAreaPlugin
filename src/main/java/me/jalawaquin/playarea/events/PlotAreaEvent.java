package me.jalawaquin.playarea.events;

import org.bukkit.Location;
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

    public PlotAreaEvent(Player player_, Location loc1_, Location loc2_){
        this.player = player_;
        this.loc1 = loc1_;
        this.loc2 = loc2_;
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
