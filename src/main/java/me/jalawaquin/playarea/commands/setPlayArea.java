package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.events.PlayEvents;
import me.jalawaquin.playarea.events.PlotAreaEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class setPlayArea implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack itemstack = new ItemStack(Material.GOLDEN_HOE, 1);
            ItemMeta meta = itemstack.getItemMeta();

            if(player.hasPermission("playarea.setplayarea")){
                player.sendMessage("Left Click a corner of your desired play area");
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Play Area Tool");
                itemstack.setItemMeta(meta);
                player.getInventory().addItem(itemstack);
            }
            else{
                player.sendMessage("You do not have the required permissions to use this command.");
            }

        }


        return false;
    }
}