package me.jalawaquin.playarea.settings;

public class PlayAreaMobSettings {
    private boolean mobs;
    private Float insideMobModifier;
    private static Float outsideMobModifier;

    public PlayAreaMobSettings(){
        this.mobs = false;
        this.insideMobModifier = null;
        this.outsideMobModifier = null;
    }
    public void setMobs(boolean mobs){this.mobs = mobs;}

    public void setInsideMobModifier(Float insideMobModifier) {this.insideMobModifier = insideMobModifier;}

    public void setOutsideMobModifier(Float outsideMobModifier) {this.outsideMobModifier = outsideMobModifier;}

    public boolean getMobs(){return mobs;}

    public Float getInsideMobModifier(){return insideMobModifier;}

    public Float getOutsideMobModifier(){return outsideMobModifier;}

}
