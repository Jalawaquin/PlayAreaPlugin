package me.jalawaquin.effectzone.commands;

import me.jalawaquin.effectzone.settings.Plots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class outsideEffectZone implements CommandExecutor{
    private Plots plot;

    public outsideEffectZone(Plots plot){this.plot = plot;}
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("playarea.outsideeffectzone")){
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command");
            return false;
        }

        try{
            if(args.length >= 2){
                if(plot.isPlotEmpty()){
                    player.sendMessage(ChatColor.RED + "Cannot modify outside of play area. No play area exists");
                    return false;
                }

                //turn into switch statement
                String arg_zero = args[0].toLowerCase();

                switch(arg_zero){
                    case "potions":
                        if(args.length >= 3){
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
                        else{
                            player.sendMessage(ChatColor.RED + "Invalid Input. /outsideplayarea <modtype> <variable> ....");
                        }
                        break;
                    case "messages":
                        if(plot.isMessageModOn()){
                            String tmp_message = "";

                            for(int i = 1; i < args.length; i++) {
                                if(i == 1){
                                    tmp_message = tmp_message + args[i];
                                    continue;
                                }
                                tmp_message = tmp_message + " " + args[i];
                            }
                            plot.setMessageSettings(tmp_message, false, player);
                        }
                        else{
                            player.sendMessage(ChatColor.RED + "Message modifier is not turned on");
                        }
                        break;
                    case "mobs":
                        if(plot.isMobModOn()){
                            plot.setMobSettings(Double.parseDouble(args[1]), false, player);
                        }
                        else{
                            player.sendMessage(ChatColor.RED + "Mob modifier is not turned on");
                        }
                        break;
                    default:
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
