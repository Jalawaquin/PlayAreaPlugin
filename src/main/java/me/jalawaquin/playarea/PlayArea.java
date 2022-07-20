package me.jalawaquin.playarea;

import me.jalawaquin.playarea.commands.*;

import me.jalawaquin.playarea.events.PlayEvents;
import me.jalawaquin.playarea.listeners.PlotAreaListener;
import me.jalawaquin.playarea.settings.Plots;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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

        getCommand("deletePlayArea").setExecutor(new deletePlayArea(this));
        getCommand("getPlayAreaID").setExecutor(new getPlayAreaID(this));
        getCommand("insidePlayArea").setExecutor(new insidePlayArea(this));
        getCommand("outsidePlayArea").setExecutor(new outsidePlayArea(this));
        getCommand("setPlayArea").setExecutor(new setPlayArea());
        getCommand("turnPlayArea").setExecutor(new turnPlayArea(this));

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
        p.setPlotID(plots.size());
        plots.add(p);

        return true;
    }

    public boolean deletePlot(String plotID, Player player){
        if(plots.isEmpty()){
            return false;
        }

        if(plotID.equalsIgnoreCase("all")){
            plots.clear();
        }
        else{
            plots.get(Integer.parseInt(plotID)).clearPotions(player);
            plots.remove(Integer.parseInt(plotID));
        }

        return true;
    }
    public boolean insidePlot(String location){
        if(!plots.isEmpty()){
            for (Plots plot : plots) {
                if (plot.getBlockArea().containsKey(location)) {
                    return true;
                }
            }
        }

        return false;
    }
    public Plots getCurrentPlot(String location){
        for(Plots plot : plots){
            if(plot.getBlockArea().containsKey(location)){
                return plot;
            }
        }
        return null;
    }
    public ArrayList<Plots> getAllPlots(){
        return plots;
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
