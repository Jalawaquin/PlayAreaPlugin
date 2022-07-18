package me.jalawaquin.effectzone;

import me.jalawaquin.effectzone.commands.setEffectZone;

import me.jalawaquin.effectzone.events.EffectEvents;
import me.jalawaquin.effectzone.listeners.PlotAreaListener;
import me.jalawaquin.effectzone.settings.Plots;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class EffectZone extends JavaPlugin {
    //turn into ArrayList to have multiple plots ? (if I do this need add plot and remove plot functionality etc.)
    private ArrayList<Plots> plots = new ArrayList<>();
    private ArrayList<Location> block_locations = new ArrayList<>();;
    private int num_of_locations = 0;
    @Override
    public void onEnable() {
        // Plugin startup logic

        /*
        getCommand("deletePlayArea").setExecutor(new deletePlayArea(plot));
        getCommand("insidePlayArea").setExecutor(new insidePlayArea(plot));
        getCommand("outsidePlayArea").setExecutor(new outsidePlayArea(plot));
        getCommand("setPlayArea").setExecutor(new setPlayArea());
        getCommand("turnPlayArea").setExecutor(new turnPlayArea(plot));
        */
        getCommand("setPlayArea").setExecutor(new setEffectZone());

        getServer().getPluginManager().registerEvents(new PlotAreaListener(this), this);
        getServer().getPluginManager().registerEvents(new EffectEvents(this),this);

    }
    //Create functions that checks all plots
    public void checkPlotOverlap(Plots plot_to_check){
        // for each plot index
        for(int i = 0; i < plots.size(); i++){
            //check if hashmap conflicts with plot_to_check

        }
    }
    public void addPlot(Plots plot){
        plots.add(plot);
        //if plot successfully added. reset block locations
    }

    public Plots getPlot(int plotIndex){
        return plots.get(plotIndex);
    }

    // Block Location functionality for setting a plot up
    public void resetBlockLocations(Player player){
        player.sendMessage("RESET CALLED");
        block_locations.clear();
        num_of_locations = 0;
    }
    public void addBlockLocation(Location block_loc){
        block_locations.add(block_loc);
    }
    public ArrayList<Location> getBlockLocations(){return block_locations;}
    public void incNumOfLocations(){num_of_locations++;}
    public int getNumOfLocations(){return num_of_locations;}
}
