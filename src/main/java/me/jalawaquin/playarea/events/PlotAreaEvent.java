package me.jalawaquin.playarea.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlotAreaEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Location block_location;
    private int num_of_locations = 0;

    public PlotAreaEvent(Player player_, Location block_location_){
        this.player = player_;
        this.block_location = block_location_;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getBlock_location() {
        return block_location;
    }

    public HandlerList getHandlers(){
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
