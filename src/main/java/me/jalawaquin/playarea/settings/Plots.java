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
    //play area settings
    private PlayAreaPotionSettings potionSettings;
    private PlayAreaMessageSettings messageSettings;
    private PlayAreaMobSettings mobSettings;
    private int plotID;
    public Plots(){
        this.blockArea = new HashMap<>();
        this.potionSettings = new PlayAreaPotionSettings();
        this.messageSettings = new PlayAreaMessageSettings();
        this.mobSettings = new PlayAreaMobSettings();
        this.plotID = 0;
    }
    // mob damage settings
    public PlayAreaMobSettings getMobSettings(){return this.mobSettings;}
    public boolean playAreaMobs(String bool){
        switch(bool){
            case "on":
                mobSettings.setMobs(true);
                break;
            case "off":
                mobSettings = new PlayAreaMobSettings();
                break;
        }
        return mobSettings.isMobModOn();
    }
    public void setMobSettings(Double mobModifier, boolean inside, Player player){
        if(inside){
            mobSettings.setInsideMobModifier(mobModifier);
            player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Mob damage increased by " + mobModifier + " inside the play area"));
        }
        else{
            mobSettings.setOutsideMobModifier(mobModifier);
            player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Mob damage increased by" + mobModifier + " outside the play area"));
        }
    }
    // message settings
    public PlayAreaMessageSettings getMessageSettings(){ return this.messageSettings; }
    public boolean playAreaMessage(String bool){
        switch(bool){
            case "on":
                messageSettings.setMessage(true);
                break;
            case "off":
                messageSettings.setMessage(false);
                break;
        }

        return messageSettings.isMessageModOn();
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
    public void clearInsidePotions(Player player){
        if(potionSettings.getInsidePotion() != null){
            player.removePotionEffect(Objects.requireNonNull(potionSettings.getInsidePotion()));
        }
        potionSettings.setInsidePotion(null, 0, 0);
    }

    public void clearOutsidePotions(Player player){
        if(potionSettings.getOutsidePotion() != null){
            player.removePotionEffect(Objects.requireNonNull(potionSettings.getOutsidePotion()));
        }

        potionSettings.setOutsidePotion(null, 0, 0);
    }

    public boolean playAreaPotions(String bool){
        switch(bool){
            case "on":
                potionSettings.setPotions(true);
                break;
            case "off":
                potionSettings.setPotions(false);
                break;
        }
        return potionSettings.isPotionsModOn();
    }
    public void setInsidePotionType(HashMap<String, UUID> blockArea, Player player, PotionEffectType insidePotion, Integer insideDuration, Integer insideAmplifier){
        if(potionSettings.getInsidePotion() != null){
            player.removePotionEffect(Objects.requireNonNull(potionSettings.getInsidePotion()));
        }

        //check if inside potion is valid
        potionSettings.setInsidePotion(insidePotion, insideDuration, insideAmplifier);

        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();


        if(blockArea.containsKey(playerLocation)){
            //if potions modifier is on
            if (insideDuration != null && insideAmplifier != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(potionSettings.getInsidePotion()),
                        potionSettings.getInsideDuration() * 20, potionSettings.getInsideAmplifier()));
            }
        }
        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + potionSettings.getInsidePotion().getName() + " potion applied to inside of play area"));
    }
    //Redundant
    public void setOutsidePotionType(HashMap<String, UUID> blockArea, Player player, PotionEffectType outsidePotion, Integer outsideDuration, Integer outsideAmplifier){
        if(potionSettings.getOutsidePotion() != null){
            player.removePotionEffect(Objects.requireNonNull(potionSettings.getOutsidePotion()));
        }

        potionSettings.setOutsidePotion(outsidePotion, outsideDuration, outsideAmplifier);

        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();

        if(!blockArea.containsKey(playerLocation)){
            if(outsideDuration != null && outsideAmplifier != null){
                //Add Effects if player is outside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(potionSettings.getOutsidePotion()),
                        potionSettings.getOutsideDuration() * 20, potionSettings.getOutsideAmplifier()));
            }
        }
        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + potionSettings.getOutsidePotion().getName() + " applied to outside of play area"));
    }

    // Hash Map / Plot functions
    public void setBlockArea(HashMap<String, UUID> blockArea){this.blockArea = blockArea;}
    public HashMap<String, UUID> getBlockArea(){return blockArea;}

    public int getPlotID(){return plotID;}
    public int setPlotID(int plotID){return this.plotID = plotID;}
}
