package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.settings.PlayAreaPotionSettings;
import me.jalawaquin.playarea.events.PlotAreaEvent;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PlotAreaListener implements Listener {
    //Note: Static variables bad practice, find a way to change if you can

    // hash map to hold play area
    private static HashMap<String, UUID> blocks = new HashMap<>();

    // are potions or mobs being modified?
    private static boolean potions;

    // Potions modifier values (put inside own class)
    private static PlayAreaPotionSettings potionSettings= new PlayAreaPotionSettings();

    @EventHandler
    public void PlotSave(PlotAreaEvent event){
        // modifiers are off by default
        potions = false;

        //get play area
        blocks = event.getPlotArea();
        event.getPlayer().sendMessage(ChatColor.YELLOW + "Play area set");
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        // blocks
        String currentBlockTo = event.getTo().getBlockX() + "." + event.getTo().getBlockZ();
        String currentBlockFrom = event.getFrom().getBlockX() + "." + event.getFrom().getBlockZ();

        //potion effects
        String insidePotionType = potionSettings.getInsidePotionType();
        Integer insideDuration = potionSettings.getInsideDuration();
        Integer insideAmplifier = potionSettings.getInsideAmplifier();

        String outsidePotionType = potionSettings.getOutsidePotionType();
        Integer outsideDuration = potionSettings.getOutsideDuration();
        Integer outsideAmplifier = potionSettings.getOutsideAmplifier();

        if (blocks.containsKey(currentBlockTo) && !blocks.containsKey(currentBlockFrom)){
            // Remove outside potion effects if outsidePotionEffect not null
            if(outsidePotionType != null){
                player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(outsidePotionType)));
            }

            //if potions modifier is on
            if (potions && insideDuration != null && insideAmplifier != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(insidePotionType)), insideDuration * 20, insideAmplifier));
            }
            player.sendMessage(ChatColor.YELLOW + "You are now entering the play area.");
        }
        else if (!blocks.containsKey(currentBlockTo) && blocks.containsKey(currentBlockFrom)){
            // Remove inside potion effects if outsidePotionEffect not null
            if(insidePotionType != null){
                player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(insidePotionType)));
            }

            // if potions modifier turned on
            if(potions && outsideDuration != null && outsideAmplifier != null){
                //Add Effects if player is outside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(outsidePotionType)), outsideDuration * 20, outsideAmplifier));
            }

            player.sendMessage(ChatColor.RED + "You are now leaving the play area");
        }
    }
    public boolean isPotionsModOn(){
        return potions;
    }
    public void setInsidePotionType(String potionsType, int duration, int amplifier){
        potionSettings.setInsidePotionType(potionsType);
        potionSettings.setInsideDuration(duration);
        potionSettings.setInsideAmplifier(amplifier);
    }

    public void setOutsidePotionType(String potionsType, int duration, int amplifier){
        potionSettings.setOutsidePotionType(potionsType);
        potionSettings.setOutsideDuration(duration);
        potionSettings.setOutsideAmplifier(amplifier);
    }
    // turn on and off modifiers.
    // NOTE: Edit this to take in player and remove potions from player when turning off
    public boolean playAreaPotions(String bool, Player player){
        switch(bool){
            case "on":
                potions = true;
                break;
            case "off":
                potions = false;
                clearPotions(player);
                break;
        }
        return potions;
    }

    // clear modifiers
    public void clearPotions(Player player){
        if(potionSettings.getInsidePotionType() != null){
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(potionSettings.getInsidePotionType())));
        }
        if(potionSettings.getOutsidePotionType() != null){
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(potionSettings.getOutsidePotionType())));
        }

        potionSettings.clearInsidePotion();
        potionSettings.clearOutsidePotion();
    }
    public void turnModifiersOff(Player player){
        potions = false;
        clearPotions(player);
    }
    // Hash Map functions
    public void deletePlot(){
        blocks.clear();
    }

    public boolean isPlotEmpty(){
        return blocks.isEmpty();
    }

}
