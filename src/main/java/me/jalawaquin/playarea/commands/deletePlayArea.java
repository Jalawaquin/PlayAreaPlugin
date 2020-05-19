package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.events.PlayEvents;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class deletePlayArea implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("playarea.deleteplayarea")){
                PlayEvents playevents = new PlayEvents();
                playevents.delete_locations(player);
            }
            else{
                player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command.");
            }
        }

        return false;
    }
}
