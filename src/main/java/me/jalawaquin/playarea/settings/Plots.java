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
            player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Mob damage increased by " + mobModifier + " outside the play area"));
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

    // Hash Map / Plot functions
    public void setBlockArea(HashMap<String, UUID> blockArea){this.blockArea = blockArea;}
    public HashMap<String, UUID> getBlockArea(){return blockArea;}

    public int getPlotID(){return plotID;}
    public int setPlotID(int plotID){return this.plotID = plotID;}
}
