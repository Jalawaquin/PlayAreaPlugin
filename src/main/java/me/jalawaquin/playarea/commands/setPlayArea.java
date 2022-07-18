package me.jalawaquin.playarea.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class setPlayArea implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if(player.hasPermission("playarea.setplayarea")){
                ItemStack itemstack = new ItemStack(Material.GOLDEN_HOE, 1);
                ItemMeta meta = itemstack.getItemMeta();

                player.sendMessage("Left Click a corner of your desired play area");

                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Play Area Tool");

                itemstack.setItemMeta(meta);
                player.getInventory().addItem(itemstack);
            }
            else{
                player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command.");
            }
        }

        return false;
    }
}