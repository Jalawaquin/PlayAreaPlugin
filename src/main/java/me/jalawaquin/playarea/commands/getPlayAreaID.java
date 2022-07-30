package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.PlayAreaInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class getPlayAreaID implements CommandExecutor {

    private PlayAreaInfo playAreaInfo;

    public getPlayAreaID(PlayAreaInfo playAreaInfo){
        this.playAreaInfo = playAreaInfo;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("playarea.getplayareaid")){
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command");
            return false;
        }

        if(playAreaInfo.getAllPlots().isEmpty()){
            player.sendMessage(ChatColor.RED + "Cannot get play area ID. No play areas exist");
            return false;
        }
        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();

        if(!playAreaInfo.insidePlot(playerLocation)){
            player.sendMessage(ChatColor.RED + "Cannot get play area ID. You are not inside a play area");
            return false;
        }

        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Current Play Area ID: " + playAreaInfo.getCurrentPlot(playerLocation).getPlotID()));

        return false;
    }
}
