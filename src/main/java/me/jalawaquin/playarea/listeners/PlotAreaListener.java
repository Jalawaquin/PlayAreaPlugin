package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.settings.PlayAreaMessageSettings;
import me.jalawaquin.playarea.settings.PlayAreaMobSettings;
import me.jalawaquin.playarea.settings.PlayAreaPotionSettings;
import me.jalawaquin.playarea.settings.Plots;
import me.jalawaquin.playarea.events.PlotAreaEvent;

import org.bukkit.ChatColor;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PlotAreaListener implements Listener {
    private Plots plot;

    public PlotAreaListener(Plots plot){
        this.plot = plot;
    }

    @EventHandler
    public void PlotSave(PlotAreaEvent event){
        //get play area
        plot.setBlockArea(event.getPlotArea());
        event.getPlayer().sendMessage(ChatColor.GREEN + "Play area set");
    }

    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Monster)) {
            return;
        }

        if (!plot.isMobModOn()){
            return;
        }

        if(!(event.getEntity() instanceof Player)){
            return;
        }

        HashMap<String, UUID> blockArea = plot.getBlockArea();
        PlayAreaMobSettings temp_mobSettings = plot.getMobSettings();
        String playerLocation = event.getEntity().getLocation().getBlockX() + "." + event.getEntity().getLocation().getBlockZ();
        double damageInc;

        // if player is damaged and mob modifier is on multiply damage by the modifier value
        if(blockArea.containsKey(playerLocation)){
            damageInc =  event.getDamage() + temp_mobSettings.getInsideMobModifier();
            event.setDamage(damageInc);
            event.getEntity().sendMessage("Damage Increased by " + damageInc);

        }
        else if(!blockArea.containsKey(playerLocation)){
            damageInc =  event.getDamage() + temp_mobSettings.getOutsideMobModifier();
            event.getEntity().sendMessage("Damage Increased by " + damageInc);
            event.setDamage(damageInc);
        }

    }

    // NOTE: see how many times this function is called
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        HashMap<String, UUID> blockArea = plot.getBlockArea();

        PlayAreaPotionSettings tempPotionSettings = plot.getPotionSettings();
        PlayAreaMessageSettings tempMessageSettings = plot.getMessageSettings();

        //potion effects
        String insidePotionType = tempPotionSettings.getInsidePotion();
        Integer insideDuration = tempPotionSettings.getInsideDuration();
        Integer insideAmplifier = tempPotionSettings.getInsideAmplifier();

        String outsidePotionType = tempPotionSettings.getOutsidePotion();
        Integer outsideDuration = tempPotionSettings.getOutsideDuration();
        Integer outsideAmplifier = tempPotionSettings.getOutsideAmplifier();

        // When playarea is set check if player is inside or outside of playarea and outside effects
        // blocks
        String currentBlockTo = event.getTo().getBlockX() + "." + event.getTo().getBlockZ();
        String currentBlockFrom = event.getFrom().getBlockX() + "." + event.getFrom().getBlockZ();

        //When player enters playarea (if one exists) send message and apply/remove correct modifers
        if ((blockArea.containsKey(currentBlockTo) && !blockArea.containsKey(currentBlockFrom))){
            // Remove outside potion effects if outsidePotionEffect not null
            if(outsidePotionType != null){
                player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(outsidePotionType)));
            }

            //if potions modifier is on
            if (plot.isPotionsModOn() && insideDuration != null && insideAmplifier != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(insidePotionType)), insideDuration * 20, insideAmplifier));
            }
            //if messages mod on
            if(plot.isMessageModOn()){
                player.sendMessage(ChatColor.YELLOW + (ChatColor.ITALIC + tempMessageSettings.getEnterMessage()));
            }
        }
        else if (!blockArea.containsKey(currentBlockTo) && blockArea.containsKey(currentBlockFrom)){
            // Remove inside potion effects if outsidePotionEffect not null
            if(insidePotionType != null){
                player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(insidePotionType)));
            }

            // if potions modifier turned on
            if(plot.isPotionsModOn() && outsideDuration != null && outsideAmplifier != null){
                //Add Effects if player is outside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(outsidePotionType)), outsideDuration * 20, outsideAmplifier));
            }

            if(plot.isMessageModOn()){
                player.sendMessage(ChatColor.RED + (ChatColor.ITALIC + tempMessageSettings.getLeaveMessage()));
            }
        }
    }
}
