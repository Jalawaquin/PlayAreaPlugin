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
    private String insidePotion;
    private Integer insideDuration;
    private Integer insideAmplifier;

    private String outsidePotion;
    private Integer outsideDuration;
    private Integer outsideAmplifier;

    public PlayAreaPotionSettings(){
        this.potions = false;
        this.insidePotion = null;
        this.insideDuration = null;
        this.insideAmplifier = null;
        this.outsidePotion = null;
        this.outsideDuration = null;
        this.outsideAmplifier = null;
    }
    public boolean getPotions(){
        return this.potions;
    }

    public void setPotions(boolean potions){ this.potions = potions; }

    public void setInsidePotion(String insidePotion, Integer insideDuration, Integer insideAmplifier){
        this.insidePotion = insidePotion;
        this.insideDuration = insideDuration;
        this.insideAmplifier = insideAmplifier;
    }
    public void setOutsidePotion(String outsidePotion, Integer outsideDuration, Integer outsideAmplifier){
        this.outsidePotion = outsidePotion;
        this.outsideDuration = outsideDuration;
        this.outsideAmplifier = outsideAmplifier;
    }

    public String getInsidePotion(){
        return insidePotion;
    }
    public Integer getInsideDuration(){
        return insideDuration;
    }

    public Integer getInsideAmplifier(){
        return insideAmplifier;
    }

    public String getOutsidePotion(){
        return outsidePotion;
    }

    public Integer getOutsideDuration(){
        return outsideDuration;
    }

    public Integer getOutsideAmplifier(){
        return outsideAmplifier;
    }
}
