package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.listeners.PlotAreaListener;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class inPlayArea implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("playarea.inplayarea")){
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command.");
            return false;
        }

        try{
            if(args.length >= 2){
                PlotAreaListener listener = new PlotAreaListener();

                if(listener.isPlotEmpty()){
                    player.sendMessage(ChatColor.RED + "Cannot modify inside of play area. No play area exists.");
                    return false;
                }

                if(args[0].toLowerCase().equals("potions"))
                {
                    PotionEffectType potionType = PotionEffectType.getByName(args[1].toUpperCase());
                    int duration = Integer.parseInt(args[2]);
                    int amplifier = Integer.parseInt(args[3]);

                    listener.setPotions(potionType, duration, amplifier);
                    player.sendMessage(potionType + "," + duration + "," + amplifier);
                }
                else {
                    player.sendMessage(ChatColor.RED + "Invalid Input. /inplayarea potion <effect> <seconds> <amplifier>");
                }

                if(args[0].toLowerCase().equals("mobs")){

                }
                else{
                    player.sendMessage(ChatColor.RED + "Invalid Input. /inplayarea mobs <damageModifier> ");
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "Invalid Input. /inplayarea <type> <mod> ....");
            }
        }
        catch(IllegalArgumentException e){
            player.sendMessage(ChatColor.RED + "Invalid Input. /inplayarea <type> <mod> ....");
        }

        return true;
    }

}
