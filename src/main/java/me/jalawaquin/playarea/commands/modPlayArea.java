package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.listeners.PlotAreaListener;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class modPlayArea implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("playarea.modplayarea")){
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command.");
            return false;
        }

        try{
            if(args.length >= 2){
                PlotAreaListener listener = new PlotAreaListener();

                if(listener.isPlotEmpty()){
                    player.sendMessage(ChatColor.RED + "Cannot modify play area. No play area exists.");
                    return false;
                }

                if(args[0].toLowerCase().equals("potions"))
                {
                    if(listener.playAreaPotions(args[1].toLowerCase())){
                        player.sendMessage(ChatColor.GREEN + "Potions modifier turned on !");
                    }
                    else {
                        player.sendMessage(ChatColor.GREEN + "Potions modifier turned off !");
                    }
                }
                else if(args[0].toLowerCase().equals("mobs")){
                    if(listener.playAreaMobs(args[1].toLowerCase())){
                        player.sendMessage(ChatColor.GREEN + "Mob modifier turned on !");
                    }
                    else {
                        player.sendMessage(ChatColor.GREEN + "Mob modifier turned off !");
                    }
                }
                else {
                    invalidInput(player);
                }
            }
            else{
                invalidInput(player);
            }
        }
        catch(IllegalArgumentException e){
            invalidInput(player);
        }


        return true;
    }

    private void invalidInput(Player player){
        player.sendMessage(ChatColor.RED + "Invalid Input. /modplayarea <type> <on/off>");
    }
}
