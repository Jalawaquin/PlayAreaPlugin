package me.jalawaquin.effectzone.commands;

import me.jalawaquin.effectzone.settings.Plots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class turnEffectZone implements CommandExecutor{
    private Plots plot;

    public turnEffectZone(Plots plot){
        this.plot = plot;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("playarea.turneffectzone")){
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command.");
            return false;
        }

        try{
            if(args.length >= 2){

                if(plot.isPlotEmpty()){
                    player.sendMessage(ChatColor.RED + "Cannot modify effect zone. No effect zone exists.");
                    return false;
                }

                String arg_zero = args[0].toLowerCase();

                switch(arg_zero){
                    case "potions":
                        if(plot.playAreaPotions(args[1].toLowerCase(), player)){
                            player.sendMessage(ChatColor.GREEN + "Potions modifier turned on ! Utilize /insideeffectzone and /outsideeffectzone to modify potions effects !");
                        }
                        else {
                            player.sendMessage(ChatColor.GREEN + "Potions modifier turned off !");
                        }
                        break;
                    case "messages":
                        if(plot.playAreaMessage(args[1].toLowerCase())){
                            player.sendMessage(ChatColor.GREEN + "Message modifier turned on ! Utilize /insideeffectzone and /outsideeffectzone to modify enter and leaving messages !");
                        }
                        else {
                            player.sendMessage(ChatColor.GREEN + "Message modifier turned off !");
                        }
                        break;
                    case "mobs":
                        if(plot.playAreaMobs(args[1].toLowerCase())){
                            player.sendMessage(ChatColor.GREEN + "Mob modifier turned on ! Utilize /insideeffectzone and /outsideeffectzone to modify mob damage !");
                        }
                        else{
                            player.sendMessage(ChatColor.GREEN + "Mob modifier turned off !");
                        }
                        break;
                    default:
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
        player.sendMessage(ChatColor.RED + "Invalid Input. /turnplayarea <type> <on/off>");
    }
}
