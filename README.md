#PlayAreaPlugin
Use a tool to create plots that apply status effects to players.
If a player wanders in or out of any of these plots, customizable messages, potion effects, & mob damage can be set.

Commands:
-Desc: deletes plot of specific ID. enter 'all' to clear all plots.
/deleteplayarea <plotID> 

-Desc: gets the ID of the plot you are standing in
/getplayareaid

-Desc: recieve 'play area tool' to begin setting plots
/setplayarea 

-Desc: turns playarea settings on and off
/turnplayarea <modtype> 
              <potions> <on/off> (off by default)
              <messages> <on/off> (on by default)
              <mobs> <on/off> (off by default)

-Note: Specific to each plot. Use 'all' to apply to all plots.
/insideplayarea <plotID> <modtype>
                         <potions> <type> <duration> <amplifier>
                         <entermessage> <custom message>
                         <leavemessage> <custom message>
                         <mobs> <damage> 
                         
Note: Universal (everytime you step out from inside a plot, these custom settings apply)                         
/outsideplayarea <modtype>
                 <potions> <type> <duration> <amplifier>
                 <mobs> <damage>


potions list
https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html
