package me.jalawaquin.playarea.settings;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Plots {
    private HashMap<String, UUID> blockArea;
    private ArrayList<Location> block_locations;
    private int num_of_locations;

    public Plots(){
        this.blockArea = new HashMap<>();
        this.block_locations = new ArrayList<>();
        this.num_of_locations = 0;
    }

    public boolean isPlotEmpty(){return blockArea.isEmpty();}

    public void deletePlot(Player player){
        blockArea.clear();
        block_locations.clear();
        num_of_locations = 0;
        player.sendMessage(ChatColor.GREEN + "Play area deleted");
    }
    public void setBlockArea(HashMap<String, UUID> blockArea){this.blockArea = blockArea;}

    public void addBlockLocation(Location block_loc){
        block_locations.add(block_loc);
    }

    public void incNumOfLocations(){num_of_locations++;}

    public HashMap<String, UUID> getBlockArea(){return blockArea;}

    public ArrayList<Location> getBlockLocations(){return block_locations;}

    public int getNumOfLocations(){return num_of_locations;}

}
