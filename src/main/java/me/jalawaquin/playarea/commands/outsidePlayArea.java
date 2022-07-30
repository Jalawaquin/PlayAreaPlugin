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

        if(args.length < 2){
            invalidInput(player);
            return false;
        }

        try{
            switch(args[0].toLowerCase()){
                case "potions":
                    if(args.length < 3) {
                        invalidInput(player);
                        break;
                    }

                    if(plugin.isPotionsModOn()){
                        PotionEffectType potionType = PotionEffectType.getByName(args[1].toUpperCase());
                        Integer duration = Integer.parseInt(args[2]);
                        Integer amplifier = Integer.parseInt(args[3]);

                        //p.getblockarea only calls 0 change to apply to only the current area player is in
                        plugin.getOutsidePotionSettings().setOutsidePotionType(plugin, player, potionType, duration, amplifier);
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Potions modifier is not turned on");
                    }
                    break;
                case "mobs":
                    if(plugin.isMobModOn()){
                        double outsideMobModifier = Double.parseDouble(args[1]);
                       plugin.setOutsideMobModifier(outsideMobModifier, player);
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
        Plots p = plugin.getAllPlots().get(0);



        return false;
    }

    private void invalidInput(Player player) {
        player.sendMessage(ChatColor.RED + "Invalid Input. /outsideplayarea <modtype> <variable> ....");
    }
}
