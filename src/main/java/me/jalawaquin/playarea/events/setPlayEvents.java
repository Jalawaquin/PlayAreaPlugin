package me.jalawaquin.playarea.events;


import me.jalawaquin.playarea.plots.setPlotArea;
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

public class setPlayEvents implements Listener {
    @EventHandler
    public void onHoeHit(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemstack = player.getInventory().getItemInMainHand();
        Block block = event.getClickedBlock();

        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (itemstack.getType().equals(Material.GOLDEN_HOE) && itemstack.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Play Area Tool")){
                Location bLoc = block.getLocation();
                player.sendMessage("Corner Set");


            }

        }


    }

}
