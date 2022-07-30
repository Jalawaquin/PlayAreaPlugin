package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.PlayAreaInfo;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class deletePlayArea implements CommandExecutor {
    private PlayAreaInfo playAreaInfo;
    public deletePlayArea(PlayAreaInfo playAreaInfo){
        this.playAreaInfo = playAreaInfo;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("playarea.deleteplayarea")) {
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command.");
            return false;
        }

        if(playAreaInfo.getAllPlots().isEmpty()){
            player.sendMessage(ChatColor.RED + "Cannot delete play areas. No play areas exist");
            return false;
        }

        try{
            if(args.length >= 1){
                if(playAreaInfo.deletePlot(args[0], player)){
                    player.sendMessage(ChatColor.RED + (ChatColor.BOLD + "Play area(s) " + args[0] + " deleted"));
                }
                else{
                    player.sendMessage(ChatColor.RED + "Cannot delete play area. Play area ID: " + args[0] + ", does not exist");
                }
            }
        }
        catch(Exception e){
            player.sendMessage(ChatColor.RED + "Invalid Input. /deleteplayarea <plotID>");
        }

        return false;
    }
}
