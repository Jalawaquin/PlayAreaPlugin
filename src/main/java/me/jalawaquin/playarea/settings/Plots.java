package me.jalawaquin.playarea.settings;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Plots {
    private HashMap<String, UUID> blockArea;
    private ArrayList<Location> block_locations;
    private int num_of_locations;
    //play area settings
    private PlayAreaPotionSettings potionSettings;
    private PlayAreaMessageSettings messageSettings;
    private PlayAreaMobSettings mobSettings;

    public Plots(){
        this.blockArea = new HashMap<>();
        this.block_locations = new ArrayList<>();
        this.num_of_locations = 0;
        this.potionSettings = new PlayAreaPotionSettings();
        this.messageSettings = new PlayAreaMessageSettings();
        this.mobSettings = new PlayAreaMobSettings();
    }

    // mob damage settings
    public PlayAreaMobSettings getMobSettings(){return this.mobSettings;}
    public boolean isMobModOn(){return mobSettings.getMobs();}
    public boolean playAreaMobs(String bool, Player player){
        switch(bool){
            case "on":
                mobSettings.setMobs(true);
                break;
            case "off":
                mobSettings = new PlayAreaMobSettings();
                break;
        }
        return mobSettings.getMobs();
    }
    public void setMobSettings(Float mobModifier, boolean inside, Player player){
        if(inside){
            mobSettings.setInsideMobModifier(mobModifier);
            player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Mob damage modifier set to " + mobModifier + " inside the play area"));
        }
        else{
            mobSettings.setOutsideMobModifier(mobModifier);
            player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Mob damage modifier set to " + mobModifier + " outside the play area"));
        }
    }
    // message settings
    public PlayAreaMessageSettings getMessageSettings(){ return this.messageSettings; }
    public boolean isMessageModOn(){return messageSettings.getMessage();}
    public boolean playAreaMessage(String bool, Player player){
        switch(bool){
            case "on":
                messageSettings.setMessage(true);
                break;
            case "off":
                messageSettings.setMessage(false);
                break;
        }

        return messageSettings.getMessage();
    }
    public void setMessageSettings(String message, boolean inside, Player player){
        if(inside){
            messageSettings.setEnterMessage(message);
            player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Enter message successfully set !"));
        }
        else{
            messageSettings.setLeaveMessage(message);
            player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Leave message successfully set !"));
        }
    }
    // potion functions
    public PlayAreaPotionSettings getPotionSettings(){
        return this.potionSettings;
    }
    public void clearPotions(Player player){
        String insidePotion = potionSettings.getInsidePotion();
        String outsidePotion = potionSettings.getOutsidePotion();

        if(insidePotion != null){
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(insidePotion)));
        }
        if(outsidePotion != null){
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(outsidePotion)));
        }

        this.potionSettings = new PlayAreaPotionSettings();
    }

    public boolean isPotionsModOn(){
        return potionSettings.getPotions();
    }
    public boolean playAreaPotions(String bool, Player player){
        switch(bool){
            case "on":
                potionSettings.setPotions(true);
                break;
            case "off":
                clearPotions(player);
                break;
        }
        return potionSettings.getPotions();
    }
    public void setInsidePotionType(HashMap<String, UUID> blockArea, Player player, String insidePotion, Integer insideDuration, Integer insideAmplifier){
        if(potionSettings.getInsidePotion() != null){
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(potionSettings.getInsidePotion())));
        }

        potionSettings.setInsidePotion(insidePotion, insideDuration, insideAmplifier);

        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();


        if(blockArea.containsKey(playerLocation)){
            //if potions modifier is on
            if (isPotionsModOn() && insideDuration != null && insideAmplifier != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(potionSettings.getInsidePotion())),
                        potionSettings.getInsideDuration() * 20, potionSettings.getInsideAmplifier()));
            }
        }
        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + insidePotion + " applied to inside of play area"));
    }
    public void setOutsidePotionType(HashMap<String, UUID> blockArea, Player player, String outsidePotion, Integer outsideDuration, Integer outsideAmplifier){
        if(potionSettings.getOutsidePotion() != null){
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(potionSettings.getOutsidePotion())));
        }

        potionSettings.setOutsidePotion(outsidePotion, outsideDuration, outsideAmplifier);

        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();

        if(!blockArea.containsKey(playerLocation)){
            if(isPotionsModOn() && outsideDuration != null && outsideAmplifier != null){
                //Add Effects if player is outside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(potionSettings.getOutsidePotion())),
                        potionSettings.getOutsideDuration() * 20, potionSettings.getOutsideAmplifier()));
            }
        }
        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + outsidePotion + " applied to outside of play area"));
    }

    // Hash Map / Plot functions
    public boolean isPlotEmpty(){return blockArea.isEmpty();}
    public void deletePlot(Player player){
        blockArea.clear();
        block_locations.clear();
        num_of_locations = 0;
        messageSettings = new PlayAreaMessageSettings();
        clearPotions(player);
        player.sendMessage(ChatColor.GREEN + "Play area deleted");
    }
    public void setBlockArea(HashMap<String, UUID> blockArea){this.blockArea = blockArea;}
    public HashMap<String, UUID> getBlockArea(){return blockArea;}
    public void addBlockLocation(Location block_loc){
        block_locations.add(block_loc);
    }
    public ArrayList<Location> getBlockLocations(){return block_locations;}
    public void incNumOfLocations(){num_of_locations++;}
    public int getNumOfLocations(){return num_of_locations;}

}
