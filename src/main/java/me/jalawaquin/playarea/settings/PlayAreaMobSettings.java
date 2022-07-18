package me.jalawaquin.playarea.settings;

public class PlayAreaMobSettings {
    private static boolean mobs;
    private double insideMobModifier;
    private static double outsideMobModifier;

    public PlayAreaMobSettings(){
        this.mobs = false;
        this.insideMobModifier = 0;
        this.outsideMobModifier = 0;
    }
    public void setMobs(boolean mobs){this.mobs = mobs;}

    public void setInsideMobModifier(Double insideMobModifier) {this.insideMobModifier = insideMobModifier;}

    public void setOutsideMobModifier(Double outsideMobModifier) {this.outsideMobModifier = outsideMobModifier;}

    public boolean isMobModOn(){return mobs;}

    public double getInsideMobModifier(){return insideMobModifier;}

    public double getOutsideMobModifier(){return outsideMobModifier;}

}
