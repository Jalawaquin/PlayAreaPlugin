package me.jalawaquin.playarea.events;

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
    private Plots plot;
    public PlayEvents(Plots plot){
        this.plot = plot;
    }

    @EventHandler
    public void onHoeHit(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemstack = player.getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (itemstack.getType().equals(Material.GOLDEN_HOE) && itemstack.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Play Area Tool")){
                if (player.hasPermission("playarea.playevents")) {
                    Location block_loc = block.getLocation();

                    if (plot.getNumOfLocations() < 2){
                        plot.addBlockLocation(block_loc);

                        player.sendMessage("Location " + plot.getNumOfLocations());

                        if (plot.getNumOfLocations() == 1){
                            ArrayList<Location> block_locations = plot.getBlockLocations();
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
    }
}
