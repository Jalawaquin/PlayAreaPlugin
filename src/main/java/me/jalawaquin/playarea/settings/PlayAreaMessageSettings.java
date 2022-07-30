package me.jalawaquin.playarea.settings;

public class PlayAreaMessageSettings {
    private String enterMessage;
    private String leaveMessage;

    public PlayAreaMessageSettings(){
        this.enterMessage = "You are now entering play area: ";
        this.leaveMessage = "You are now leaving play area: ";
    }
    public void setEnterMessage(String enterMessage){
        this.enterMessage = enterMessage;
    }
    public void setLeaveMessage(String leaveMessage){
        this.leaveMessage = leaveMessage;
    }
    public String getEnterMessage(){
        return enterMessage;
    }
    public String getLeaveMessage(){
        return leaveMessage;
    }
}
