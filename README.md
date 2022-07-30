# PlayAreaPlugin
The PlayAreaPlugin is a tool to create plots that apply status effects to players.
If a player wanders in or out of any of these plots, customizable messages, potion effects, & mob damage can be set.

Commands:
```

/deleteplayarea <plotID> 
-Desc: deletes plot of specific ID. enter 'all' to clear all plots.


/getplayareaid
Description: gets the ID of the plot you are standing in


/setplayarea 
Description: recieve 'play area tool' to begin setting plots


/turnplayarea <modtype> 
              <potions> <on/off> (off by default)
              <messages> <on/off> (on by default)
              <mobs> <on/off> (off by default)
Description: turns playarea settings on and off


/insideplayarea <plotID> <modtype>
                         <potions> <type> <duration> <amplifier>
                         <entermessage> <custom message>
                         <leavemessage> <custom message>
                         <mobs> <damage>             
Description: Apply customizable effects to a specific plot. Use 'all' to apply to all plots.

                   
/outsideplayarea <modtype>
                 <potions> <type> <duration> <amplifier>
                 <mobs> <damage>
Description: Apply customizable effects to player everytime they leave a plot.

```
## Helpful Links
- Potion Effects List: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html

