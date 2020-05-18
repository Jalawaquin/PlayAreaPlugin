package me.jalawaquin.playarea.events;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getServer;

public class PlayEvents implements Listener {

    @EventHandler
    public void onHoeHit(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemstack = player.getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();
        if (player.hasPermission("playarea.playevents"))
        {
            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                if (itemstack.getType().equals(Material.GOLDEN_HOE) && itemstack.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Play Area Tool")){
                    Location block_location_ = block.getLocation();

                    getServer().getPluginManager().callEvent(new PlotAreaEvent(player,block_location_));
                }
            }
        }
        else{
            player.sendMessage("You do not have the required permissions to use this tool.");
        }
    }


}
