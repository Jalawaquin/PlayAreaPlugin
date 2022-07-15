package me.jalawaquin.playarea.settings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PlayAreaPotionSettings {
    // are potions or mobs being modified?
    private boolean potions;
    private String insidePotionType;
    private Integer insideDuration;
    private Integer insideAmplifier;

    private String outsidePotionType;
    private Integer outsideDuration;
    private Integer outsideAmplifier;

    public PlayAreaPotionSettings(){
        this.potions = false;
        this.insidePotionType = null;
        this.insideDuration = null;
        this.insideAmplifier = null;
        this.outsidePotionType = null;
        this.outsideDuration = null;
        this.outsideAmplifier = null;
    }
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

    public void setInsidePotionType(HashMap<String, UUID> blockArea, Player player, String insidePotionType, Integer insideDuration, Integer insideAmplifier){
        this.insidePotionType = insidePotionType;
        this.insideDuration = insideDuration;
        this.insideAmplifier = insideAmplifier;

        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();

        if(blockArea.containsKey(playerLocation)){
            //if potions modifier is on
            if (potions && insideDuration != null && insideAmplifier != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(insidePotionType)), insideDuration * 20, insideAmplifier));
                player.sendMessage(ChatColor.GREEN + insidePotionType + " applied to inside of play area");
            }
        }
    }
    public void setOutsidePotionType(HashMap<String, UUID> blockArea, Player player, String outsidePotionType, Integer insideDuration, Integer insideAmplifier){
        this.outsidePotionType = outsidePotionType;
        this.outsideDuration = insideDuration;
        this.outsideAmplifier = insideAmplifier;

        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();

        if(!blockArea.containsKey(playerLocation)){
            if(potions && outsideDuration != null && outsideAmplifier != null){
                //Add Effects if player is outside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(outsidePotionType)), outsideDuration * 20, outsideAmplifier));
                player.sendMessage(ChatColor.GREEN + outsidePotionType + " applied to outside of play area");
            }
        }
    }
    public boolean isPotionsModOn(){ return potions; }

    public String getInsidePotionType(){
        return insidePotionType;
    }

    public Integer getInsideDuration(){
        return insideDuration;
    }

    public Integer getInsideAmplifier(){
        return insideAmplifier;
    }

    public String getOutsidePotionType(){
        return outsidePotionType;
    }

    public Integer getOutsideDuration(){
        return outsideDuration;
    }

    public Integer getOutsideAmplifier(){
        return outsideAmplifier;
    }
    public void clearPotions(Player player){
        if(insidePotionType != null){
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(insidePotionType)));
        }
        if(outsidePotionType != null){
            player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(outsidePotionType)));
        }

        insidePotionType = null;
        insideDuration = null;
        insideAmplifier = null;
        outsidePotionType = null;
        outsideDuration = null;
        outsideAmplifier = null;
    }
}
