package me.jalawaquin.playarea;

import me.jalawaquin.playarea.commands.deletePlayArea;
import me.jalawaquin.playarea.commands.insidePlayArea;
import me.jalawaquin.playarea.commands.outsidePlayArea;
import me.jalawaquin.playarea.commands.turnPlayArea;
import me.jalawaquin.playarea.commands.setPlayArea;

import me.jalawaquin.playarea.events.PlayEvents;
import me.jalawaquin.playarea.listeners.PlotAreaListener;
import me.jalawaquin.playarea.settings.Plots;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class PlayArea extends JavaPlugin {
    //turn into ArrayList to have multiple plots ? (if I do this need add plot and remove plot functionality etc.)
    private ArrayList<Plots> plots = new ArrayList<>();
    private ArrayList<Location> block_locations = new ArrayList<>();
    private int num_of_locations = 0;

    @Override
    public void onEnable() {
        // Plugin startup logic

 //       getCommand("deletePlayArea").setExecutor(new deletePlayArea(plot));
 //       getCommand("insidePlayArea").setExecutor(new insidePlayArea(plot));
 //       getCommand("outsidePlayArea").setExecutor(new outsidePlayArea(plot));
        getCommand("setPlayArea").setExecutor(new setPlayArea());
 //       getCommand("turnPlayArea").setExecutor(new turnPlayArea(plot));

        getServer().getPluginManager().registerEvents(new PlayEvents(this),this);
        getServer().getPluginManager().registerEvents(new PlotAreaListener(this), this);
    }

    public boolean addPlot(Plots p){
        //If all plots in arraylist do not overlap with p, add plot
        if(!plots.isEmpty()){
            for (Plots plot : plots) {
                for (String key : p.getBlockArea().keySet()) {
                    if (plot.getBlockArea().containsKey(key)) {
                        return false;
                    }
                }
            }
        }

        plots.add(p);
        return true;
    }

    //Block Location functions
    public void addBlockLocation(Location block_loc){
        block_locations.add(block_loc);
    }
    public ArrayList<Location> getBlockLocations(){return block_locations;}
    public void incNumOfLocations(){num_of_locations++;}
    public int getNumOfLocations(){return num_of_locations;}
    public void clearBlockLocations(){
        block_locations.clear();
        num_of_locations = 0;
    }
}
