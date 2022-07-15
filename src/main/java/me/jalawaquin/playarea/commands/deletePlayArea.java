package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.settings.Plots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class deletePlayArea implements CommandExecutor {
    private Plots plot;
    public deletePlayArea(Plots plot){
        this.plot = plot;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("playarea.deleteplayarea")){

                if(!plot.isPlotEmpty()){
                    plot.deletePlot(player);
                    plot.clearPotions(player);
//                    messageSettings = new PlayAreaMessageSettings();
                }
                else{
                    player.sendMessage(ChatColor.RED + "Cannot delete play area. No play area exists.");
                }
            }
            else{
                player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command.");
            }
        }

        return false;
    }
}
