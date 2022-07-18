package me.jalawaquin.effectzone.settings;

public class EffectZoneMobSettings {
    private boolean mobs;
    private double insideMobModifier;
    private double outsideMobModifier;

    public EffectZoneMobSettings(){
        this.mobs = false;
        this.insideMobModifier = 0;
        this.outsideMobModifier = 0;
    }
    public void setMobs(boolean mobs){this.mobs = mobs;}

    public void setInsideMobModifier(Double insideMobModifier) {this.insideMobModifier = insideMobModifier;}

    public void setOutsideMobModifier(Double outsideMobModifier) {this.outsideMobModifier = outsideMobModifier;}

    public boolean getMobs(){return mobs;}

    public double getInsideMobModifier(){return insideMobModifier;}

    public double getOutsideMobModifier(){return outsideMobModifier;}

}
