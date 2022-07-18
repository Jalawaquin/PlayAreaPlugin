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
        event.getPlayer().sendMessage(ChatColor.GREEN + "Play area set");
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
        Plots p = plugin.getAllPlots().get(0);
        //check if mob Modifier turned on (static variable within plots)
        if(!p.getMobSettings().isMobModOn()){
            return;
        }


        // if player is damaged and mob modifier is on multiply damage by the modifier value
        if(plugin.insidePlot(playerLocation)){
            event.setDamage(event.getDamage() + p.getMobSettings().getInsideMobModifier());
            event.getEntity().sendMessage("Damage Increased by " + event.getDamage() + p.getMobSettings().getInsideMobModifier());
        }
        else if(!plugin.insidePlot(playerLocation)){
            event.getEntity().sendMessage("Damage Increased by " + event.getDamage() + p.getMobSettings().getOutsideMobModifier());
            event.setDamage(event.getDamage() + p.getMobSettings().getOutsideMobModifier());
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

            if(p.getPotionSettings().isPotionsModOn() && p.getPotionSettings().getOutsidePotion() != null){
                player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(p.getPotionSettings().getOutsidePotion())));
            }

            //if potions modifier is on
            if (p.getPotionSettings().isPotionsModOn() && p.getPotionSettings().getInsideDuration() != null
                    && p.getPotionSettings().getInsideAmplifier() != null && p.getPotionSettings().getInsidePotion() != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(p.getPotionSettings().getInsidePotion())),
                        p.getPotionSettings().getInsideDuration() * 20, p.getPotionSettings().getInsideAmplifier()));
            }
            //if messages mod on
            if(p.getMessageSettings().isMessageModOn()){
                player.sendMessage(ChatColor.YELLOW + (ChatColor.ITALIC + p.getMessageSettings().getEnterMessage() + p.getPlotID()));
            }
        }
        else if (!plugin.insidePlot(currentBlockTo) && plugin.insidePlot(currentBlockFrom)){
            Plots p = plugin.getCurrentPlot(currentBlockFrom);
            // Remove inside potion effects if outsidePotionEffect not null
            if(p.getPotionSettings().isPotionsModOn() && p.getPotionSettings().getInsidePotion() != null){
                player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(p.getPotionSettings().getInsidePotion())));
            }

            // if potions modifier turned on
            if(p.getPotionSettings().isPotionsModOn() && p.getPotionSettings().getOutsidePotion() != null && p.getPotionSettings().getOutsideDuration() != null
                    && p.getPotionSettings().getOutsideAmplifier() != null ){
                //Add Effects if player is outside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(p.getPotionSettings().getOutsidePotion())),
                        p.getPotionSettings().getOutsideDuration() * 20, p.getPotionSettings().getOutsideAmplifier()));
            }

            if(p.getMessageSettings().isMessageModOn()){
                player.sendMessage(ChatColor.RED + (ChatColor.ITALIC + p.getMessageSettings().getLeaveMessage() + p.getPlotID()));
            }
        }

    }
}
