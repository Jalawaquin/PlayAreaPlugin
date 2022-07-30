package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.PlayArea;
import me.jalawaquin.playarea.settings.Plots;
import me.jalawaquin.playarea.events.PlotAreaEvent;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;


public class PlotAreaListener implements Listener {
    private PlayArea plugin;

    public PlotAreaListener(PlayArea plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void PlotSave(PlotAreaEvent event){
        Plots p = new Plots();
        p.setBlockArea(event.getPlotArea());

        if(!plugin.addPlot(p)){
            event.getPlayer().sendMessage(ChatColor.RED + "Failed. Plot overlaps with another plot");
            return;
        }
        //if else play area is succesffuly set
        event.getPlayer().sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Play area set"));
    }

    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent event){
        //if there are no plots, stop
        if (plugin.getAllPlots().isEmpty()){
            return;
        }

        //if damage was dealt by player, stop
        if(event.getDamager() instanceof Player){
            return;
        }

        //if the entity being damaged was not a player, stop
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        String playerLocation = event.getEntity().getLocation().getBlockX() + "." + event.getEntity().getLocation().getBlockZ();

        //if the mob modifier is not on, stop
        // Note: this function seems like it could implemented better

        //check if mob Modifier turned on (static variable within plots)
        if(!plugin.isMobModOn()){
            return;
        }


        // if player is damaged and mob modifier is on multiply damage by the modifier value
        if(plugin.insidePlot(playerLocation)){
            Plots p = plugin.getCurrentPlot(playerLocation);
            event.setDamage(event.getDamage() + p.getInsideMobModifier());
            event.getEntity().sendMessage("Damage Increased by " + event.getDamage() + p.getInsideMobModifier());
        }
        else if(!plugin.insidePlot(playerLocation)){
            event.getEntity().sendMessage("Damage Increased by " + event.getDamage() + plugin.getOutsideMobModifier());
            event.setDamage(event.getDamage() + plugin.getOutsideMobModifier());
        }

    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(plugin.getAllPlots().isEmpty()){
            return;
        }
        Player player = event.getPlayer();

        // When playarea is set check if player is inside or outside of playarea and outside effects
        // blocks
        String currentBlockTo = event.getTo().getBlockX() + "." + event.getTo().getBlockZ();
        String currentBlockFrom = event.getFrom().getBlockX() + "." + event.getFrom().getBlockZ();

        //When player enters playarea (if one exists) send message and apply/remove correct modifers
        if ((plugin.insidePlot(currentBlockTo) && !plugin.insidePlot(currentBlockFrom))){
            //temp plot variable
            Plots p = plugin.getCurrentPlot(currentBlockTo);
            // Remove outside potion effects if outsidePotionEffect not null

            if(plugin.isPotionsModOn() && plugin.getOutsidePotionSettings().getOutsidePotion() != null){
                player.removePotionEffect(Objects.requireNonNull((plugin.getOutsidePotionSettings().getOutsidePotion())));
            }

            //if potions modifier is on
            if (plugin.isPotionsModOn() && p.getPotionSettings().getInsideDuration() != null
                    && p.getPotionSettings().getInsideAmplifier() != null && p.getPotionSettings().getInsidePotion() != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(p.getPotionSettings().getInsidePotion()),
                        p.getPotionSettings().getInsideDuration() * 20, p.getPotionSettings().getInsideAmplifier()));
            }
            //if messages mod on
            if(plugin.isMessageModOn() && p.getMessageSettings().getEnterMessage().equals("You are now entering play area: ")){
                player.sendMessage(ChatColor.YELLOW + (ChatColor.ITALIC + p.getMessageSettings().getEnterMessage() + p.getPlotID()));
            }
            else if (plugin.isMessageModOn()){
                player.sendMessage(ChatColor.YELLOW + (ChatColor.ITALIC + p.getMessageSettings().getEnterMessage()));
            }
        }
        else if (!plugin.insidePlot(currentBlockTo) && plugin.insidePlot(currentBlockFrom)){
            Plots p = plugin.getCurrentPlot(currentBlockFrom);
            // Remove inside potion effects if outsidePotionEffect not null
            if(plugin.isPotionsModOn() && p.getPotionSettings().getInsidePotion() != null){
                player.removePotionEffect(Objects.requireNonNull(p.getPotionSettings().getInsidePotion()));
            }

            // if potions modifier turned on
            if(plugin.isPotionsModOn() && plugin.getOutsidePotionSettings().getOutsidePotion() != null &&  plugin.getOutsidePotionSettings().getOutsideDuration() != null
                    &&  plugin.getOutsidePotionSettings().getOutsideAmplifier() != null ){
                //Add Effects if player is outside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(plugin.getOutsidePotionSettings().getOutsidePotion()),
                        plugin.getOutsidePotionSettings().getOutsideDuration() * 20, plugin.getOutsidePotionSettings().getOutsideAmplifier()));
            }

            if(plugin.isMessageModOn() && p.getMessageSettings().getLeaveMessage().equals("You are now leaving play area: ")){
                player.sendMessage(ChatColor.RED + (ChatColor.ITALIC + p.getMessageSettings().getLeaveMessage() + p.getPlotID()));
            }
            else if (plugin.isMessageModOn()){
                player.sendMessage(ChatColor.RED + (ChatColor.ITALIC + p.getMessageSettings().getLeaveMessage()));
            }
        }
    }
}
