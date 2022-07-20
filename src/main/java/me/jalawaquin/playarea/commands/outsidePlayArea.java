package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.PlayArea;
import me.jalawaquin.playarea.settings.Plots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class outsidePlayArea implements CommandExecutor{
    private PlayArea plugin;

    public outsidePlayArea(PlayArea plugin){this.plugin = plugin;}
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

        if(plugin.getAllPlots().isEmpty()){
            player.sendMessage(ChatColor.RED + "Cannot modify outside of play area. No play area exists");
            return false;
        }

        if(args.length >= 2){
            Plots p = plugin.getAllPlots().get(0);
            switch(args[0].toLowerCase()){
                case "potions":
                    if(args.length >= 3){
                        if(p.getPotionSettings().isPotionsModOn()){
                            try{
                                PotionEffectType potionType = PotionEffectType.getByName(args[1].toUpperCase());
                                Integer duration = Integer.parseInt(args[2]);
                                Integer amplifier = Integer.parseInt(args[3]);

                                p.setOutsidePotionType(p.getBlockArea(), player, potionType, duration, amplifier);
                            }
                            catch(Exception e){
                                invalidInput(player);
                            }
                        }
                        else{
                            invalidInput(player);
                        }
                    }
                    else{
                        invalidInput(player);
                    }
                    break;
                case "messages":
                    if(p.getMessageSettings().isMessageModOn()){
                        String tmp_message = "";

                        for(int i = 1; i < args.length; i++) {
                            if(i == 1){
                                tmp_message = tmp_message + args[i];
                                continue;
                            }
                            tmp_message = tmp_message + " " + args[i];
                        }
                        p.setMessageSettings(tmp_message, false, player);
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Message modifier is not turned on");
                    }
                    break;
                case "mobs":
                    if(p.getMobSettings().isMobModOn()){
                        try{
                            double outsideMobModifier = Double.parseDouble(args[1]);
                            p.setMobSettings(outsideMobModifier, false, player);
                        }
                        catch(Exception e){
                            invalidInput(player);
                        }
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Mob modifier is not turned on");
                    }
                    break;
                default:
                    invalidInput(player);
            }
        }
        else{
            invalidInput(player);
        }


        return false;
    }

    private void invalidInput(Player player) {
        player.sendMessage(ChatColor.RED + "Invalid Input. /outsideplayarea <modtype> <variable> ....");
    }
}
