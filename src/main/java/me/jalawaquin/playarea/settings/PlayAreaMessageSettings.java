package me.jalawaquin.playarea.settings;

public class PlayAreaMessageSettings {
    private static boolean message;
    private String enterMessage;
    private static String leaveMessage;

    public PlayAreaMessageSettings(){
        this.message = true;
        this.enterMessage = "You are now entering play area: ";
        this.leaveMessage = "You are now leaving play area: ";
    }
    public void setMessage(boolean message){this.message = message;}
    public void setEnterMessage(String enterMessage){
        this.enterMessage = enterMessage;
    }
    public void setLeaveMessage(String leaveMessage){
        this.leaveMessage = leaveMessage;
    }
    public boolean isMessageModOn(){return message;}
    public String getEnterMessage(){
        return enterMessage;
    }
    public String getLeaveMessage(){
        return leaveMessage;
    }
}
