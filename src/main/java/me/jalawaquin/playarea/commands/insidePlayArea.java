package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.settings.Plots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class insidePlayArea implements CommandExecutor{
    private Plots plot;

    public insidePlayArea(Plots plot){
        this.plot = plot;
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

        if(plot.isPlotEmpty()){
            player.sendMessage(ChatColor.RED + "Cannot modify inside of play area. No play area exists");
            return false;
        }

        try{
            if(args.length >= 2){
                //turn into switch statement
                String arg_zero = args[0].toLowerCase();

                switch(arg_zero){
                    case "potions":
                        if(args.length >= 3){
                            if(plot.isPotionsModOn()){
                                String potionType = args[1].toUpperCase();
                                Integer duration = Integer.parseInt(args[2]);
                                Integer amplifier = Integer.parseInt(args[3]);

                                plot.setInsidePotionType(plot.getBlockArea(), player, potionType, duration, amplifier);
                            }
                            else{
                                player.sendMessage(ChatColor.RED + "Potions modifier is not turned on");
                            }
                        }
                        else{
                            player.sendMessage(ChatColor.RED + "Invalid Input. /insideplayarea <potions> <duration> <amplifier>");
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
                            plot.setMessageSettings(tmp_message, true, player);
                        }
                        else{
                            player.sendMessage(ChatColor.RED + "Message modifier is not turned on");
                        }
                        break;
                    case "mobs":
                        if(plot.isMobModOn()){
                            plot.setMobSettings(Double.parseDouble(args[1]), true, player);
                        }
                        else{
                            player.sendMessage(ChatColor.RED + "Mob modifier is not turned on");
                        }
                        break;
                    default:
                        player.sendMessage(ChatColor.RED + "Invalid Input. /insideplayarea <modtype> <variable> ....");
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
