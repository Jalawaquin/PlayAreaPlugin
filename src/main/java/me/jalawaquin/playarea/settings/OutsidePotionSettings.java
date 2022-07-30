package me.jalawaquin.playarea.settings;

import me.jalawaquin.playarea.PlayAreaInfo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class OutsidePotionSettings {
    //outside status effects are universal
    private PotionEffectType outsidePotion;
    private Integer outsideDuration;
    private Integer outsideAmplifier;

    public OutsidePotionSettings(){
        this.outsidePotion = null;
        this.outsideDuration = 0;
        this.outsideAmplifier = 0;
    }

    public void setOutsidePotion(PotionEffectType outsidePotion, Integer outsideDuration, Integer outsideAmplifier){
        this.outsidePotion = outsidePotion;
        this.outsideDuration = outsideDuration;
        this.outsideAmplifier = outsideAmplifier;
    }

    public void setOutsidePotionType(PlayAreaInfo playAreaInfo, Player player, PotionEffectType outsidePotion, Integer outsideDuration, Integer outsideAmplifier){
        if(outsidePotion != null){
            player.removePotionEffect(Objects.requireNonNull(outsidePotion));
        }

        setOutsidePotion(outsidePotion, outsideDuration, outsideAmplifier);

        String playerLocation = player.getLocation().getBlockX() + "." + player.getLocation().getBlockZ();

        //if player is in none of the plot block areas apply
        if(!playAreaInfo.insidePlot(playerLocation)){
            if(outsideDuration != null && outsideAmplifier != null){
                //Add Effects if player is outside plot
                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(outsidePotion), outsideDuration * 20, outsideAmplifier));
            }
        }
        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + outsidePotion.getName() + " applied to outside of play area"));
    }

    public void clearOutsidePotions(Player player){
        if(outsidePotion != null){
            player.removePotionEffect(Objects.requireNonNull(outsidePotion));
        }

        setOutsidePotion(null, 0, 0);
    }

    public PotionEffectType getOutsidePotion(){
        return outsidePotion;
    }

    public Integer getOutsideDuration(){
        return outsideDuration;
    }

    public Integer getOutsideAmplifier(){
        return outsideAmplifier;
    }
}
