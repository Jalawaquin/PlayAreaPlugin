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
    private PotionEffectType insidePotion;
    private int insideDuration;
    private int insideAmplifier;

    public PlayAreaPotionSettings(){
        this.insidePotion = null;
        this.insideDuration = 0;
        this.insideAmplifier = 0;
    }
    public void clearInsidePotions(Player player){
        if(insidePotion != null){
            player.removePotionEffect(Objects.requireNonNull(insidePotion));
        }
        setInsidePotion(null, 0, 0);
    }
    public void setInsidePotion(PotionEffectType insidePotion, Integer insideDuration, Integer insideAmplifier){
        this.insidePotion = insidePotion;
        this.insideDuration = insideDuration;
        this.insideAmplifier = insideAmplifier;
    }
    public void setInsidePotionType(HashMap<String, UUID> blockArea, Player player, PotionEffectType insidePotion, Integer insideDuration, Integer insideAmplifier){
        if(insidePotion != null){
            player.removePotionEffect(Objects.requireNonNull(insidePotion));
        }

        //check if inside potion is valid
        setInsidePotion(insidePotion, insideDuration, insideAmplifier);

        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();

        if(blockArea.containsKey(playerLocation)){
            //if potions modifier is on
            if (insideDuration != null && insideAmplifier != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(insidePotion),
                        insideDuration * 20, insideAmplifier));
            }
        }

 //       player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + insidePotion.getName() + " potion applied to inside of play area"));
    }

    public PotionEffectType getInsidePotion(){
        return insidePotion;
    }
    public Integer getInsideDuration(){
        return insideDuration;
    }

    public Integer getInsideAmplifier(){
        return insideAmplifier;
    }

}
