package me.jalawaquin.playarea.events;

import me.jalawaquin.playarea.PlayArea;
import me.jalawaquin.playarea.settings.Plots;

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

import java.util.ArrayList;

public class PlayEvents implements Listener {
    private PlayArea plugin;
    public PlayEvents(PlayArea plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onHoeHit(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if (!player.hasPermission("playarea.playevents")) {
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this tool.");
            return;
        }


        ItemStack itemstack = player.getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) && itemstack.getType().equals(Material.GOLDEN_HOE)
                && itemstack.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Play Area Tool")) {
            if (plugin.getNumOfLocations() < 2){
                Location block_loc = block.getLocation();
                plugin.addBlockLocation(block_loc);
                ArrayList<Location> block_locations = plugin.getBlockLocations();
                player.sendMessage("Location " + plugin.getNumOfLocations() + " - X: " + block_locations.get(plugin.getNumOfLocations()).getBlockX()
                        + " Z: " + block_locations.get(plugin.getNumOfLocations()).getBlockZ());

                if (plugin.getNumOfLocations() == 1){
                    // calls Plotareaevent so plotarealistner may recieve plot
                    getServer().getPluginManager().callEvent(new PlotAreaEvent(player, block_locations.get(0), block_locations.get(1)));
                    plugin.clearBlockLocations();
                }
                else{
                    plugin.incNumOfLocations();
                }

            }
        }
    }
}
