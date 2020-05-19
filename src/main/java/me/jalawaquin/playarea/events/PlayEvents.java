package me.jalawaquin.playarea.events;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getServer;

public class PlayEvents implements Listener {

    private static Location[] block_locations = new Location[2];
    private static int num_of_locations = 0;

    @EventHandler
    public void onHoeHit(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemstack = player.getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (itemstack.getType().equals(Material.GOLDEN_HOE) && itemstack.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Play Area Tool")){
                if (player.hasPermission("playarea.playevents")) {
                    Location block_loc = block.getLocation();

                    if (num_of_locations < 2){
                        block_locations[num_of_locations] = block_loc;
                        player.sendMessage("Location " + num_of_locations + ": ");
                        player.sendMessage("X: " + block_locations[num_of_locations].getBlockX());
                        player.sendMessage("Y: " + block_locations[num_of_locations].getBlockY());
                        player.sendMessage("Z: " + block_locations[num_of_locations].getBlockZ());

                        if (num_of_locations == 1){
                            getServer().getPluginManager().callEvent(new PlotAreaEvent(player, block_locations[0], block_locations[1]));
                            num_of_locations++;
                        }
                        num_of_locations++;
                    }
                }
                else {
                    player.sendMessage("You do not have the required permissions to use this tool.");
                }
            }
        }
    }

    public void delete_locations(Player player){
        for (int i = 0; i < 2; i++){
            this.block_locations[i] = null;
        }
        this.num_of_locations = 0;

        player.sendMessage("You have deleted your plot");

    }

}
