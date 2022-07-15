package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.listeners.PlotAreaListener;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class insidePlayArea implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("playarea.insideplayarea")){
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command");
            return false;
        }

        try{
            if(args.length >= 2){
                PlotAreaListener listener = new PlotAreaListener();

                if(listener.isPlotEmpty()){
                    player.sendMessage(ChatColor.RED + "Cannot modify inside of play area. No play area exists");
                    return false;
                }

                if(args[0].equalsIgnoreCase("potions") && args.length >= 3)
                {
                    if(listener.isPotionsModOn()){
                        String potionType = args[1].toUpperCase();
                        Integer duration = Integer.parseInt(args[2]);
                        Integer amplifier = Integer.parseInt(args[3]);

                        listener.setInsidePotionType(player, potionType, duration, amplifier);
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Potions modifier is not turned on");
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "Invalid Input. /insideplayarea potion <effect> <seconds> <amplifier>");
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "Invalid Input. /insideplayarea <modtype> <variable> ....");
            }
        }
        catch(IllegalArgumentException e){
            player.sendMessage(ChatColor.RED + "Invalid Input. /insideplayarea <modtype> <variable> ....");
        }

        return true;
    }

}
