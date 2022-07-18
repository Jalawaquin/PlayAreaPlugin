package me.jalawaquin.effectzone.events;

import me.jalawaquin.effectzone.EffectZone;

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

public class EffectEvents implements Listener {
    private EffectZone plugin;
    public EffectEvents(EffectZone plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onHoeHit(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (!player.hasPermission("playarea.playevents")) {
            return;
        }

        ItemStack itemstack = player.getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) && itemstack.getType().equals(Material.GOLDEN_HOE)
                && itemstack.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Play Area Tool")) {
            //Get clicked block location
            Location block_loc = block.getLocation();
            player.sendMessage("NUM OF LOCATIONS" + plugin.getNumOfLocations());
            if (plugin.getNumOfLocations() < 2){
                plugin.addBlockLocation(block_loc);
                ArrayList<Location> block_locations = plugin.getBlockLocations();
                player.sendMessage("Location " + plugin.getNumOfLocations() + " - X: " + block_locations.get(plugin.getNumOfLocations()).getBlockX()
                        + " Z: " + block_locations.get(plugin.getNumOfLocations()).getBlockZ());

                if (plugin.getNumOfLocations() == 1){
                    // calls Plotareaevent so plotarealistner may recieve plot
                    getServer().getPluginManager().callEvent(new PlotAreaEvent(player, block_locations.get(0), block_locations.get(1)));
                    plugin.resetBlockLocations(player);
                }
                else{
                    plugin.incNumOfLocations();
                }
            }

        }
        /*
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (itemstack.getType().equals(Material.GOLDEN_HOE) && itemstack.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Play Area Tool")){
                if (player.hasPermission("playarea.playevents")) {
                    Location block_loc = block.getLocation();

                    if (plot.getNumOfLocations() < 2){
                        plot.addBlockLocation(block_loc);
                        ArrayList<Location> block_locations = plot.getBlockLocations();
                        player.sendMessage("Location " + plot.getNumOfLocations() + " - X: " + block_locations.get(plot.getNumOfLocations()).getBlockX()
                                + " Z: " + block_locations.get(plot.getNumOfLocations()).getBlockZ());

                        if (plot.getNumOfLocations() == 1){
                            // calls Plotareaevent so plotarealistner may recieve plot
                            getServer().getPluginManager().callEvent(new PlotAreaEvent(player, block_locations.get(0), block_locations.get(1)));
                            plot.incNumOfLocations();
                        }
                        plot.incNumOfLocations();
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this tool.");
                }
            }
        }
         */
    }
}
