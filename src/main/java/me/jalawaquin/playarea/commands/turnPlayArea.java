package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.PlayAreaInfo;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class turnPlayArea implements CommandExecutor{
    private PlayAreaInfo playAreaInfo;

    public turnPlayArea(PlayAreaInfo playAreaInfo){
        this.playAreaInfo = playAreaInfo;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("playarea.turnplayarea")){
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command.");
            return false;
        }

        if(playAreaInfo.getAllPlots().isEmpty()){
            player.sendMessage(ChatColor.RED + "Cannot modify play area. No play areas exist.");
            return false;
        }

        try{
            if(args.length < 2) {
                invalidInput(player);
            }
            switch(args[0].toLowerCase()){
                case "potions":
                    if(playAreaInfo.playAreaPotions(args[1].toLowerCase())){
                        player.sendMessage(ChatColor.GREEN + "Potions modifier turned on ! Utilize /insideplayarea and /outsideplayarea to modify potions effects !");
                    }
                    else {
                        playAreaInfo.clearAllPotions(player);
                        player.sendMessage(ChatColor.GREEN + "Potions modifier turned off !");
                    }
                    break;
                case "messages":
                    if(playAreaInfo.playAreaMessage(args[1].toLowerCase())){
                        player.sendMessage(ChatColor.GREEN + "Message modifier turned on ! Utilize /insideplayarea and /outsideplayarea to modify enter and leaving messages !");
                    }
                    else {
                        player.sendMessage(ChatColor.GREEN + "Message modifier turned off !");
                    }
                    break;
                case "mobs":
                    if(playAreaInfo.playAreaMobs(args[1].toLowerCase())){
                        player.sendMessage(ChatColor.GREEN + "Mob modifier turned on ! Utilize /insideplayarea and /outsideplayarea to modify mob damage !");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "Mob modifier turned off !");
                    }
                    break;
                default:
                    invalidInput(player);
            }
        }
        catch(IllegalArgumentException e){
            invalidInput(player);
        }

        return false;
    }

    private void invalidInput(Player player){
        player.sendMessage(ChatColor.RED + "Invalid Input. /turnplayarea <type> <on/off>");
    }
}
