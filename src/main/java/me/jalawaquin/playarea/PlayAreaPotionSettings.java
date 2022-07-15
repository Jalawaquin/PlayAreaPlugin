package me.jalawaquin.playarea;

public class PlayAreaPotionSettings {
    private String insidePotionType;
    private Integer insideDuration;
    private Integer insideAmplifier;

    private String outsidePotionType;
    private Integer outsideDuration;
    private Integer outsideAmplifier;

    public PlayAreaPotionSettings(){
        this.insidePotionType = null;
        this.insideDuration = null;
        this.insideAmplifier = null;
        this.outsidePotionType = null;
        this.outsideDuration = null;
        this.outsideAmplifier = null;
    }

    public void setInsidePotionType(String potionType){
        insidePotionType = potionType;
    }

    public void setInsideDuration(Integer duration){
        insideDuration = duration;
    }

    public void setInsideAmplifier(Integer amplifier){
        insideAmplifier = amplifier;
    }

    public void setOutsidePotionType(String potionType){
        outsidePotionType = potionType;
    }

    public void setOutsideDuration(Integer duration){
        outsideDuration = duration;
    }

    public void setOutsideAmplifier(Integer amplifier){
        outsideAmplifier = amplifier;
    }

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

    public void clearInsidePotion(){
        insidePotionType = null;
        insideDuration = null;
        insideAmplifier = null;
    }

    public void clearOutsidePotion(){
        outsidePotionType = null;
        outsideDuration = null;
        outsideAmplifier = null;
    }
}
