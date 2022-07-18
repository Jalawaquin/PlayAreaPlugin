package me.jalawaquin.playarea.settings;

public class PlayAreaPotionSettings {
    // are potions or mobs being modified?
    private boolean potions;
    private String insidePotion;
    private int insideDuration;
    private int insideAmplifier;

    //outside status effects are universal
    private String outsidePotion;
    private Integer outsideDuration;
    private Integer outsideAmplifier;

    public PlayAreaPotionSettings(){
        this.potions = false;
        this.insidePotion = null;
        this.insideDuration = 0;
        this.insideAmplifier = 0;
        this.outsidePotion = null;
        this.outsideDuration = 0;
        this.outsideAmplifier = 0;
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
