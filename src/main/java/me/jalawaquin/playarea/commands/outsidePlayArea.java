package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.settings.Plots;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class outsidePlayArea implements CommandExecutor{
    private Plots plot;

    public outsidePlayArea(Plots plot){
        this.plot = plot;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("playarea.outsideplayarea")){
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command");
            return false;
        }

        try{
            if(args.length >= 2){

                if(plot.isPlotEmpty()){
                    player.sendMessage(ChatColor.RED + "Cannot modify outside of play area. No play area exists");
                    return false;
                }

                if(args[0].equalsIgnoreCase("potions") && args.length >= 3)
                {
                    if(plot.isPotionsModOn()){
                        String potionType = args[1].toUpperCase();
                        Integer duration = Integer.parseInt(args[2]);
                        Integer amplifier = Integer.parseInt(args[3]);

                        plot.setOutsidePotionType(plot.getBlockArea(), player, potionType, duration, amplifier);
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Potions modifier is not turned on");
                    }
                }
                else if(args[0].equalsIgnoreCase("message")){
                    //messageSettings.setLeaveMessage(args[1]);
                }
                else {
                    player.sendMessage(ChatColor.RED + "Invalid Input. /outsideplayarea potion <effect> <seconds> <amplifier>");
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "Invalid Input. /outsideplayarea <modtype> <variable> ....");
            }
        }
        catch(IllegalArgumentException e){
            player.sendMessage(ChatColor.RED + "Invalid Input. /outsideplayarea <modtype> <variable> ....");
        }

        return true;
    }
}
