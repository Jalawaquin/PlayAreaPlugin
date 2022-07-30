package me.jalawaquin.playarea.listeners;

import me.jalawaquin.playarea.PlayAreaInfo;
import me.jalawaquin.playarea.settings.Plots;
import me.jalawaquin.playarea.events.PlotAreaEvent;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;


public class PlotAreaListener implements Listener {
    private PlayAreaInfo playAreaInfo;

    public PlotAreaListener(PlayAreaInfo plugin){
        this.playAreaInfo = plugin;
    }

    @EventHandler
    public void PlotSave(PlotAreaEvent event){
        Plots p = new Plots();
        p.setBlockArea(event.getPlotArea());

        if(!playAreaInfo.addPlot(p)){
            event.getPlayer().sendMessage(ChatColor.RED + "Failed. Plot overlaps with another plot");
            return;
        }
        //if else play area is succesffuly set
        event.getPlayer().sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Play area set"));
    }

    @EventHandler
    public void onDamageEvent(EntityDamageByEntityEvent event){
        //if there are no plots, stop
        if (playAreaInfo.getAllPlots().isEmpty()){
            return;
        }

        //if damage was dealt by player, stop
        if(event.getDamager() instanceof Player){
            return;
        }
        //add check for arrow

        //if the entity being damaged was not a player, stop
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        String playerLocation = event.getEntity().getLocation().getBlockX() + "." + event.getEntity().getLocation().getBlockZ();

        //if the mob modifier is not on, stop
        //check if mob Modifier turned on (static variable within plots)
        if(!playAreaInfo.isMobModOn()){
            return;
        }


        // if player is damaged and mob modifier is on multiply damage by the modifier value
        if(playAreaInfo.insidePlot(playerLocation)){
            Plots p = playAreaInfo.getCurrentPlot(playerLocation);
            event.setDamage(event.getDamage() + p.getInsideMobModifier());
            //event.getEntity().sendMessage("Damage Increased by " + event.getDamage() + p.getInsideMobModifier());
        }
        else if(!playAreaInfo.insidePlot(playerLocation)){
            //test
            //event.getEntity().sendMessage("Damage Increased by " + event.getDamage() + plugin.getOutsideMobModifier());
            event.setDamage(event.getDamage() + playAreaInfo.getOutsideMobModifier());
        }

    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(playAreaInfo.getAllPlots().isEmpty()){
            return;
        }
        Player player = event.getPlayer();

        // When playarea is set check if player is inside or outside of playarea and outside effects
        // blocks
        String currentBlockTo = event.getTo().getBlockX() + "." + event.getTo().getBlockZ();
        String currentBlockFrom = event.getFrom().getBlockX() + "." + event.getFrom().getBlockZ();

        //When player enters playarea (if one exists) send message and apply/remove correct modifers
        if ((playAreaInfo.insidePlot(currentBlockTo) && !playAreaInfo.insidePlot(currentBlockFrom))){
            //temp plot variable
            Plots p = playAreaInfo.getCurrentPlot(currentBlockTo);
            // Remove outside potion effects if outsidePotionEffect not null

            if(playAreaInfo.isPotionsModOn() && playAreaInfo.getOutsidePotionSettings().getOutsidePotion() != null){
                player.removePotionEffect(Objects.requireNonNull((playAreaInfo.getOutsidePotionSettings().getOutsidePotion())));
            }

            //if potions modifier is on
            if (playAreaInfo.isPotionsModOn() && p.getPotionSettings().getInsideDuration() != null
                    && p.getPotionSettings().getInsideAmplifier() != null && p.getPotionSettings().getInsidePotion() != null) {
                // Add Effects if player is inside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(p.getPotionSettings().getInsidePotion()),
                        p.getPotionSettings().getInsideDuration() * 20, p.getPotionSettings().getInsideAmplifier()));
            }
            //if messages mod on
            if(playAreaInfo.isMessageModOn() && p.getMessageSettings().getEnterMessage().equals("You are now entering play area: ")){
                player.sendMessage(ChatColor.YELLOW + (ChatColor.ITALIC + p.getMessageSettings().getEnterMessage() + p.getPlotID()));
            }
            else if (playAreaInfo.isMessageModOn()){
                player.sendMessage(ChatColor.YELLOW + (ChatColor.ITALIC + p.getMessageSettings().getEnterMessage()));
            }
        }
        else if (!playAreaInfo.insidePlot(currentBlockTo) && playAreaInfo.insidePlot(currentBlockFrom)){
            Plots p = playAreaInfo.getCurrentPlot(currentBlockFrom);
            // Remove inside potion effects if outsidePotionEffect not null
            if(playAreaInfo.isPotionsModOn() && p.getPotionSettings().getInsidePotion() != null){
                player.removePotionEffect(Objects.requireNonNull(p.getPotionSettings().getInsidePotion()));
            }

            // if potions modifier turned on
            if(playAreaInfo.isPotionsModOn() && playAreaInfo.getOutsidePotionSettings().getOutsidePotion() != null &&  playAreaInfo.getOutsidePotionSettings().getOutsideDuration() != null
                    &&  playAreaInfo.getOutsidePotionSettings().getOutsideAmplifier() != null ){
                //Add Effects if player is outside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(playAreaInfo.getOutsidePotionSettings().getOutsidePotion()),
                        playAreaInfo.getOutsidePotionSettings().getOutsideDuration() * 20, playAreaInfo.getOutsidePotionSettings().getOutsideAmplifier()));
            }

            if(playAreaInfo.isMessageModOn() && p.getMessageSettings().getLeaveMessage().equals("You are now leaving play area: ")){
                player.sendMessage(ChatColor.RED + (ChatColor.ITALIC + p.getMessageSettings().getLeaveMessage() + p.getPlotID()));
            }
            else if (playAreaInfo.isMessageModOn()){
                player.sendMessage(ChatColor.RED + (ChatColor.ITALIC + p.getMessageSettings().getLeaveMessage()));
            }
        }
    }
}
