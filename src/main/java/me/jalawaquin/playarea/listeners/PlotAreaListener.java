package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.settings.PlayAreaMessageSettings;
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
    private static PlayAreaPotionSettings potionSettings = new PlayAreaPotionSettings();
    private static boolean mobs;

    // Custom Messages
    private static PlayAreaMessageSettings messageSettings = new PlayAreaMessageSettings();

    @EventHandler
    public void PlotSave(PlotAreaEvent event){
        // modifiers are off by default
        potions = false;
        //get play area
        blocks = event.getPlotArea();
        event.getPlayer().sendMessage(ChatColor.GREEN + "Play area set");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        //potion effects
        String insidePotionType = potionSettings.getInsidePotionType();
        Integer insideDuration = potionSettings.getInsideDuration();
        Integer insideAmplifier = potionSettings.getInsideAmplifier();

        String outsidePotionType = potionSettings.getOutsidePotionType();
        Integer outsideDuration = potionSettings.getOutsideDuration();
        Integer outsideAmplifier = potionSettings.getOutsideAmplifier();

        // When playarea is set check if player is inside or outside of playarea and outside effects
        // blocks
        String currentBlockTo = event.getTo().getBlockX() + "." + event.getTo().getBlockZ();
        String currentBlockFrom = event.getFrom().getBlockX() + "." + event.getFrom().getBlockZ();

        //When player enters playarea (if one exists) send message and apply/remove correct modifers
        if ((blocks.containsKey(currentBlockTo) && !blocks.containsKey(currentBlockFrom))){
            // Remove outside potion effects if outsidePotionEffect not null
            if(outsidePotionType != null){
                player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(outsidePotionType)));
            }

            //if potions modifier is on
            if (potions && insideDuration != null && insideAmplifier != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(insidePotionType)), insideDuration * 20, insideAmplifier));
            }
            player.sendMessage(ChatColor.YELLOW + messageSettings.getEnterMessage());
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

            player.sendMessage(ChatColor.RED + messageSettings.getLeaveMessage());
        }
    }

    // POTION MODIFIER FUNCTIONS
    //NOTE: Think about if there is a better way to implement these functions, so that not all functions are crammed into plotarealistener
    // (most likely done by removing static variables and using proper object orientation)
    public boolean isPotionsModOn(){
        return potions;
    }

    public void setInsidePotionType(Player player, String potionsType, int duration, int amplifier){
        potionSettings.setInsidePotionType(potionsType);
        potionSettings.setInsideDuration(duration);
        potionSettings.setInsideAmplifier(amplifier);

        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();

        String insidePotionType = potionSettings.getInsidePotionType();
        Integer insideDuration = potionSettings.getInsideDuration();
        Integer insideAmplifier = potionSettings.getInsideAmplifier();

        if(blocks.containsKey(playerLocation)){
            //if potions modifier is on
            if (potions && insideDuration != null && insideAmplifier != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(insidePotionType)), insideDuration * 20, insideAmplifier));
                player.sendMessage(ChatColor.GREEN + insidePotionType + " applied to inside of play area");
            }
        }
    }

    public void setOutsidePotionType(Player player, String potionsType, int duration, int amplifier){
        potionSettings.setOutsidePotionType(potionsType);
        potionSettings.setOutsideDuration(duration);
        potionSettings.setOutsideAmplifier(amplifier);

        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();

        String outsidePotionType = potionSettings.getOutsidePotionType();
        Integer outsideDuration = potionSettings.getOutsideDuration();
        Integer outsideAmplifier = potionSettings.getOutsideAmplifier();

        if(!blocks.containsKey(playerLocation)){
            if(potions && outsideDuration != null && outsideAmplifier != null){
                //Add Effects if player is outside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(outsidePotionType)), outsideDuration * 20, outsideAmplifier));
                player.sendMessage(ChatColor.GREEN + outsidePotionType + " applied to outside of play area");
            }
        }
    }
    // clear potion modifiers
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
    // MOB MODIFIER FUNCTIONS


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

    public boolean playAreaMobs(String bool, Player player){
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
    public void turnModifiersOff(Player player){
        potions = false;
        mobs = false;
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
