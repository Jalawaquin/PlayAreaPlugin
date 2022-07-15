package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.settings.Plots;
import me.jalawaquin.playarea.settings.PlayAreaMessageSettings;
import me.jalawaquin.playarea.settings.PlayAreaPotionSettings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class turnPlayArea implements CommandExecutor{
    private Plots plot;
    private PlayAreaPotionSettings potionSettings;
    private PlayAreaMessageSettings messageSettings;

    public turnPlayArea(Plots plot, PlayAreaPotionSettings potionSettings, PlayAreaMessageSettings messageSettings){
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

        if (!player.hasPermission("playarea.turnplayarea")){
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command.");
            return false;
        }

        try{
            if(args.length >= 2){

                if(plot.isPlotEmpty()){
                    player.sendMessage(ChatColor.RED + "Cannot modify play area. No play area exists.");
                    return false;
                }

                if(args[0].equalsIgnoreCase("potions"))
                {
                    if(potionSettings.playAreaPotions(args[1].toLowerCase(), player)){
                        player.sendMessage(ChatColor.GREEN + "Potions modifier turned on ! Utilize /insideplayarea and /outsideplayarea to modify potions effects !");
                    }
                    else {
                        player.sendMessage(ChatColor.GREEN + "Potions modifier turned off !");
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
