package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.settings.PlayAreaMessageSettings;
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
                else if(args[0].equalsIgnoreCase("messages")){
                    if(plot.isMessageModOn()){
                        String tmp_message = new String();

                        for(int i = 1; i < args.length; i++) {
                            if(i == 1){
                                tmp_message = tmp_message + args[i];
                                continue;
                            }
                            tmp_message = tmp_message + " " + args[i];
                        }
                        plot.setMessageSettings(tmp_message, false);
                        player.sendMessage(ChatColor.GREEN + "Leave message successfully set ! ");
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Message modifier is not turned on");
                    }
                }
                else{
                    player.sendMessage(ChatColor.RED + "Invalid Input. /outsideplayarea <modtype> <variable> ....");
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
