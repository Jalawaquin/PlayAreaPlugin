package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.PlayArea;
import me.jalawaquin.playarea.settings.Plots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class turnPlayArea implements CommandExecutor{
    private PlayArea plugin;

    public turnPlayArea(PlayArea plugin){
        this.plugin = plugin;
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

        if(plugin.getAllPlots().isEmpty()){
            player.sendMessage(ChatColor.RED + "Cannot modify play area. No play areas exist.");
            return false;
        }

        try{
            Plots p = plugin.getAllPlots().get(0);
            if(args.length < 2) {
                invalidInput(player);
            }
            switch(args[0].toLowerCase()){
                case "potions":
                    if(p.playAreaPotions(args[1].toLowerCase(), player)){
                        player.sendMessage(ChatColor.GREEN + "Potions modifier turned on ! Utilize /insideplayarea and /outsideplayarea to modify potions effects !");
                    }
                    else {
                        player.sendMessage(ChatColor.GREEN + "Potions modifier turned off !");
                    }
                    break;
                case "messages":
                    if(p.playAreaMessage(args[1].toLowerCase())){
                        player.sendMessage(ChatColor.GREEN + "Message modifier turned on ! Utilize /insideplayarea and /outsideplayarea to modify enter and leaving messages !");
                    }
                    else {
                        player.sendMessage(ChatColor.GREEN + "Message modifier turned off !");
                    }
                    break;
                case "mobs":
                    if(p.playAreaMobs(args[1].toLowerCase())){
                        player.sendMessage(ChatColor.GREEN + "Mob modifier turned on ! Utilize /insideplayarea and /outsideplayarea to modify mob damage !");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "Mob modifier turned off !");
                    }
                    break;
                default:
                    invalidInput(player);
            }
        }
        catch(IllegalArgumentException e){
            invalidInput(player);
        }

        return false;
    }

    private void invalidInput(Player player){
        player.sendMessage(ChatColor.RED + "Invalid Input. /turnplayarea <type> <on/off>");
    }
}
