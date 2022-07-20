package me.jalawaquin.playarea.settings;

import org.bukkit.potion.PotionEffectType;

public class PlayAreaPotionSettings {
    // are potions or mobs being modified?
    private static boolean potions;
    private PotionEffectType insidePotion;
    private int insideDuration;
    private int insideAmplifier;

    //outside status effects are universal
    private static PotionEffectType outsidePotion;
    private static Integer outsideDuration;
    private static Integer outsideAmplifier;

    public PlayAreaPotionSettings(){
        this.potions = false;
        this.insidePotion = null;
        this.insideDuration = 0;
        this.insideAmplifier = 0;
        this.outsidePotion = null;
        this.outsideDuration = 0;
        this.outsideAmplifier = 0;
    }
    public boolean isPotionsModOn(){
        return this.potions;
    }

    public void setPotions(boolean potions){ this.potions = potions; }

    public void setInsidePotion(PotionEffectType insidePotion, Integer insideDuration, Integer insideAmplifier){
        this.insidePotion = insidePotion;
        this.insideDuration = insideDuration;
        this.insideAmplifier = insideAmplifier;
    }
    public void setOutsidePotion(PotionEffectType outsidePotion, Integer outsideDuration, Integer outsideAmplifier){
        this.outsidePotion = outsidePotion;
        this.outsideDuration = outsideDuration;
        this.outsideAmplifier = outsideAmplifier;
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

    public PotionEffectType getOutsidePotion(){
        return outsidePotion;
    }

    public Integer getOutsideDuration(){
        return outsideDuration;
    }

    public Integer getOutsideAmplifier(){
        return outsideAmplifier;
    }
}
