package me.jalawaquin.effectzone.commands;

import me.jalawaquin.effectzone.settings.Plots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class deleteEffectZone implements CommandExecutor {
    private Plots plot;
    public deleteEffectZone(Plots plot){
        this.plot = plot;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player){
            return false;
        }

        Player player = (Player) sender;
        if (player.hasPermission("playarea.deleteplayarea")){

            if(!plot.isPlotEmpty()){
                plot.deletePlot(player);
            }
            else{
                player.sendMessage(ChatColor.RED + "Cannot delete play area. No play area exists.");
            }
        }
        else{
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command.");
        }


        return false;
    }
}
