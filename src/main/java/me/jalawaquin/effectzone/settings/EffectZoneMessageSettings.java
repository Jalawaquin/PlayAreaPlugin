package me.jalawaquin.effectzone.settings;

public class EffectZoneMessageSettings {
    private boolean message;
    private String enterMessage;
    private String leaveMessage;

    public EffectZoneMessageSettings(){
        this.message = true;
        this.enterMessage = "You are now entering the play area.";
        this.leaveMessage = "You are now leaving the play area";
    }
    public void setMessage(boolean message){this.message = message;}
    public void setEnterMessage(String enterMessage){
        this.enterMessage = enterMessage;
    }
    public void setLeaveMessage(String leaveMessage){
        this.leaveMessage = leaveMessage;
    }
    public boolean getMessage(){return message;}
    public String getEnterMessage(){
        return enterMessage;
    }
    public String getLeaveMessage(){
        return leaveMessage;
    }
}
