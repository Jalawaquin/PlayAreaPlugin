package me.jalawaquin.playarea.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;



public class PlotAreaEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Location[] block_locations = new Location[2];
    private int num_of_locations;

    public PlotAreaEvent(Player player_, Location block_location_){
        this.player = player_;
        this.num_of_locations = 0;
        this.block_locations[num_of_locations] = block_location_;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Location[] getBlock_location() {
        return this.block_locations;
    }

    public int getNum_of_locations(){
        return this.num_of_locations;
    }
    //----------------------------------------------------
    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
