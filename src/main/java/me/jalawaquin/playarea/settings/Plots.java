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
    private double insideMobModifier;
    private int plotID;
    public Plots(){
        this.blockArea = new HashMap<>();
        this.potionSettings = new PlayAreaPotionSettings();
        this.messageSettings = new PlayAreaMessageSettings();
        this.insideMobModifier = 0;
        this.plotID = 0;
    }
    // mob damage settings
    public void setInsideMobModifier(Double mobModifier, Player player){
        insideMobModifier = mobModifier;
        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Mob damage increased by " + mobModifier + " inside the play area"));
    }

    public double getInsideMobModifier(){return this.insideMobModifier;}
    // message settings
    public PlayAreaMessageSettings getMessageSettings(){ return this.messageSettings; }
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
