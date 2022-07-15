package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.settings.Plots;
import me.jalawaquin.playarea.settings.PlayAreaMessageSettings;
import me.jalawaquin.playarea.settings.PlayAreaPotionSettings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class insidePlayArea implements CommandExecutor{
    private Plots plot;
    private PlayAreaPotionSettings potionSettings;
    private PlayAreaMessageSettings messageSettings;
    public insidePlayArea(Plots plot, PlayAreaPotionSettings potionSettings, PlayAreaMessageSettings messageSettings){
        this.plot = plot;
        this.potionSettings = potionSettings;
        this.messageSettings = messageSettings;
    }
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

                if(plot.isPlotEmpty()){
                    player.sendMessage(ChatColor.RED + "Cannot modify inside of play area. No play area exists");
                    return false;
                }

                if(args[0].equalsIgnoreCase("potion") && args.length >= 3)
                {
                    if(potionSettings.isPotionsModOn()){
                        String potionType = args[1].toUpperCase();
                        Integer duration = Integer.parseInt(args[2]);
                        Integer amplifier = Integer.parseInt(args[3]);

                        potionSettings.setInsidePotionType(plot.getBlockArea(), player, potionType, duration, amplifier);
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Potions modifier is not turned on");
                    }
                }
                else if(args[0].equalsIgnoreCase("message")){
                    if(args[1] != null){
                        messageSettings.setEnterMessage(args[1]);
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
