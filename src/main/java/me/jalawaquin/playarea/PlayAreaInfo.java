package me.jalawaquin.playarea;

import me.jalawaquin.playarea.settings.OutsidePotionSettings;
import me.jalawaquin.playarea.settings.Plots;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

public class PlayAreaInfo {
    private boolean potions;
    private boolean message;
    private boolean mobs;
    private ArrayList<Plots> plots;
    private OutsidePotionSettings outsidePotionSettings;
    private double outsideMobModifier;
    //Setting up plot variables
    private ArrayList<Location> block_locations;
    private int num_of_locations;

    public PlayAreaInfo(){
        potions = false;
        message = true;
        mobs = false;

        plots = new ArrayList<>();

        outsidePotionSettings = new OutsidePotionSettings();
        outsideMobModifier = 0;

        block_locations = new ArrayList<>();
        num_of_locations = 0;
    }
    //plot modifier functions
    //messages
    public boolean isMessageModOn(){return message;}
    public boolean playAreaMessage(String bool){
        switch(bool){
            case "on":
                message = true;
                break;
            case "off":
                message = false;
                break;
        }

        return message;
    }
    //mob modifer
    public void setOutsideMobModifier(Double mobModifier, Player player){
        outsideMobModifier = mobModifier;
        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Mob damage increased by " + mobModifier + " inside the play area"));
    }
    public double getOutsideMobModifier(){return this.outsideMobModifier;}
    public boolean isMobModOn(){return mobs;}
    public boolean playAreaMobs(String bool){
        switch(bool){
            case "on":
                mobs = true;
                break;
            case "off":
                mobs = false;
                break;
        }
        return mobs;
    }
    //potions
    public void clearAllPotions(Player player){
        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();

        for(Plots plot : plots){
            if(plot.getBlockArea().containsKey(playerLocation)){
                plot.getPotionSettings().clearInsidePotions(player);
            }
        }

        getOutsidePotionSettings().clearOutsidePotions(player);
    }
    public OutsidePotionSettings getOutsidePotionSettings(){
        return this.outsidePotionSettings;
    }
    public boolean isPotionsModOn(){
        return this.potions;
    }
    public boolean playAreaPotions(String bool){
        switch(bool){
            case "on":
                potions = true;
                break;
            case "off":
                potions = false;
                break;
        }
        return potions;
    }
    //add plot functions
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
            //if potions modifier is on, clear all potions
            if(potions){
                clearAllPotions(player);
                outsidePotionSettings.clearOutsidePotions(player);
                potions = false;
            }

            plots.clear();
            return true;
        }

        for(int i = 0; i < plots.size(); i++){
            if(plots.get(i).getPlotID() == Integer.parseInt(plotID)){
                plots.get(i).getPotionSettings().clearInsidePotions(player);

                //if player is inside plot and plot is not final plot, at time of deletion add outside plot modifiers
                String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();
                if(plots.get(i).getBlockArea().containsKey(playerLocation) && plots.size() != 1){
                    // if potions modifier on
                    if(potions && (outsidePotionSettings.getOutsidePotion() != null && outsidePotionSettings.getOutsideDuration() != null
                            && outsidePotionSettings.getOutsideAmplifier() != null)){
                        //add potion effect
                        player.addPotionEffect(new PotionEffect(outsidePotionSettings.getOutsidePotion(),
                                outsidePotionSettings.getOutsideDuration(), outsidePotionSettings.getOutsideAmplifier()));
                    }
                }

                //remove plot from list
                plots.remove(i);

                //if plots now empty remove any lingering potions effects
                if(plots.isEmpty()){
                    outsidePotionSettings.clearOutsidePotions(player);
                    potions = false;
                }

                return true;
            }
        }

        return false;
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
