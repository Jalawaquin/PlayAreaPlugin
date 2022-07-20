package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.PlayArea;
import me.jalawaquin.playarea.settings.Plots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class insidePlayArea implements CommandExecutor{
    private PlayArea plugin;

    public insidePlayArea(PlayArea plugin){
        this.plugin = plugin;
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

        if(plugin.getAllPlots().isEmpty()){
            player.sendMessage(ChatColor.RED + "Cannot modify inside of play area. No play area exists");
            return false;
        }


        // Note: make this cleaner
        if(args.length < 2) {
            invalidInput(player);
            return false;
        }

        try{
            Plots p = plugin.getAllPlots().get(Integer.parseInt(args[0]));
            switch(args[1].toLowerCase()){
                case "potions":
                    if(args.length < 3) {
                        player.sendMessage(ChatColor.RED + "Invalid Input. /insideplayarea <plotID> <potions> <type> <duration> <amplifier>");
                        break;
                    }

                    if(p.getPotionSettings().isPotionsModOn()){
                        PotionEffectType potionType = PotionEffectType.getByName(args[2].toUpperCase());
                        Integer duration = Integer.parseInt(args[3]);
                        Integer amplifier = Integer.parseInt(args[4]);

                        p.setInsidePotionType(p.getBlockArea(), player, potionType, duration, amplifier);
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Potions modifier is not turned on");
                    }
                    break;
                case "messages":
                    if(p.getMessageSettings().isMessageModOn()){
                        String tmp_message = "";

                        for(int i = 2; i < args.length; i++) {
                            if(i == 2){
                                tmp_message = tmp_message + args[i];
                                continue;
                            }
                            tmp_message = tmp_message + " " + args[i];
                        }
                        p.setMessageSettings(tmp_message, true, player);
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Message modifier is not turned on");
                    }
                    break;
                case "mobs":
                    if(p.getMobSettings().isMobModOn()){
                        double insideMobModifier = Double.parseDouble(args[2]);
                        p.setMobSettings(insideMobModifier, true, player);
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Mob modifier is not turned on");
                    }
                    break;
                default:
                    invalidInput(player);
            }
        }catch(Exception e){
            invalidInput(player);
        }

        return false;
    }


    private void invalidInput(Player player){
        player.sendMessage(ChatColor.RED + "Invalid Input. /insideplayarea <plotID> <modtype> <variable> ....");
    }
}
