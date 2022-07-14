package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.events.PlotAreaEvent;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class PlotAreaListener implements Listener {
    //Note: Static variables bad practice, find a way to change

    // hash map to hold play area
    private static HashMap<String, UUID> blocks = new HashMap<>();

    // are potions or mobs being modified?
    private static boolean potions;
    private static boolean mobs;

    // Potions modifier values
    private static PotionEffectType insidePotionType;
    private static Integer insideDuration;
    private static Integer insideAmplifier;

    private static PotionEffectType outsidePotionType;
    private static Integer outsideDuration;
    private static Integer outsideAmplifier;

    //Mob modifier values
    private static Float insideDamageModifier;
    private static Float outsideDamageModifier;

    @EventHandler
    public void PlotSave(PlotAreaEvent event){
        // modifiers are off by default
        potions = false;
        mobs = false;

        //get play area
        blocks = event.getPlotArea();
        event.getPlayer().sendMessage(ChatColor.YELLOW + "Play area set");
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        String currentBlockTo = event.getTo().getBlockX() + "." + event.getTo().getBlockZ();
        String currentBlockFrom = event.getFrom().getBlockX() + "." + event.getFrom().getBlockZ();

        if (blocks.containsKey(currentBlockTo) && !blocks.containsKey(currentBlockFrom)){
            player.sendMessage(ChatColor.YELLOW + "You are now entering the play area.");
            //Effects if player is inside plot
            if(potions && insideDuration != null && insideAmplifier != null){
                player.addPotionEffect(new PotionEffect(insidePotionType, insideDuration, insideAmplifier));
            }
        }
        else if (!blocks.containsKey(currentBlockTo) && blocks.containsKey(currentBlockFrom)){
            player.sendMessage(ChatColor.RED + "You are now leaving the play area");
            //Effects if player is outside plot
            if(potions && outsideDuration != null && outsideAmplifier != null){
                player.addPotionEffect(new PotionEffect(outsidePotionType, outsideDuration, outsideAmplifier));
            }
        }
    }

    public void setPotions(PotionEffectType potionsType, int duration, int amplifier){
        insidePotionType = potionsType;
        insideDuration = duration;
        insideAmplifier = amplifier;
    }

    // turn on and off modifiers.
    public boolean playAreaPotions(String bool){
        switch(bool){
            case "on":
                potions = true;
                break;
            case "off":
                potions = false;
                clearPotions();
                break;
        }
        return potions;
    }

    public boolean playAreaMobs(String bool){
        switch(bool){
            case "on":
                mobs = true;
                break;
            case "off":
                mobs = false;
                clearMobs();
                break;
        }


        return mobs;
    }
    //clear modifiers
    public void turnModifiersOff(){
        potions = false;
        mobs = false;
        clearPotions();
        clearMobs();
    }
    private void clearPotions(){
        insidePotionType = null;
        insideDuration = null;
        insideAmplifier = null;
        outsidePotionType = null;
        outsideDuration = null;
        outsideAmplifier = null;
    }

    private void clearMobs(){
        insideDamageModifier = null;
        outsideDamageModifier = null;
    }

    //Hash Map functions
    public void deletePlot(){
        blocks.clear();
    }

    public boolean isPlotEmpty(){
        return blocks.isEmpty();
    }

}
