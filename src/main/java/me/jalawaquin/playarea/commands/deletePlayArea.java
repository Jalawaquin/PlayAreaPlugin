package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.PlayArea;
import me.jalawaquin.playarea.settings.Plots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class deletePlayArea implements CommandExecutor {
    private PlayArea plugin;
    public deletePlayArea(PlayArea plugin){
        this.plugin = plugin;
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

        if(plugin.getAllPlots().isEmpty()){
            player.sendMessage(ChatColor.RED + "Cannot delete play areas. No play areas exist.");
            return false;
        }

        try{
            if(args.length >= 1){
                if(plugin.deletePlot(args[0], player)){
                    player.sendMessage(ChatColor.RED + (ChatColor.BOLD + "Play area(s) " + args[0] + " deleted"));
                }
                else{
                    player.sendMessage(ChatColor.RED + "Cannot delete play area. No play area exists.");
                }
            }
        }
        catch(Exception e){
            player.sendMessage(ChatColor.RED + "Invalid Input. /deleteplayarea <plotID>");
        }

        return false;
    }
}
