package me.jalawaquin.playarea.commands;

import me.jalawaquin.playarea.PlayAreaInfo;
import me.jalawaquin.playarea.settings.Plots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class insidePlayArea implements CommandExecutor{
    private PlayAreaInfo playAreaInfo;

    public insidePlayArea(PlayAreaInfo playAreaInfo){
        this.playAreaInfo = playAreaInfo;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("playarea.insideplayarea")){
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to use this command");
            return false;
        }

        if(playAreaInfo.getAllPlots().isEmpty()){
            player.sendMessage(ChatColor.RED + "Cannot modify inside of play area. No play area exists");
            return false;
        }

        // Note: make this cleaner
        if(args.length < 2) {
            invalidInput(player);
            return false;
        }

        try{
            switch(args[1].toLowerCase()){
                case "potions":
                    if(args.length < 3) {
                        player.sendMessage(ChatColor.RED + "Invalid Input. /insideplayarea <plotID> <potions> <type> <duration> <amplifier>");
                        break;
                    }

                    if(!playAreaInfo.isPotionsModOn()){
                        player.sendMessage(ChatColor.RED + "Potions modifier is not turned on");
                        break;
                    }

                    PotionEffectType potionType = PotionEffectType.getByName(args[2].toUpperCase());
                    Integer duration = Integer.parseInt(args[3]);
                    Integer amplifier = Integer.parseInt(args[4]);

                    if(args[0].equalsIgnoreCase("all")){
                        ArrayList<Plots> allPlots = playAreaInfo.getAllPlots();

                        for(Plots p: allPlots){
                            p.getPotionSettings().setInsidePotionType(p.getBlockArea(), player, potionType, duration, amplifier);
                        }

                        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + potionType.getName() + " potion applied to inside of all play areas"));
                    }else{
                        Plots p = playAreaInfo.getAllPlots().get(Integer.parseInt(args[0]));
                        p.getPotionSettings().setInsidePotionType(p.getBlockArea(), player, potionType, duration, amplifier);

                        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + potionType.getName() + " potion applied to inside of play area: " + args[0]));
                    }
                    break;
                case "entermessage":
                    if(!playAreaInfo.isMessageModOn()){
                        player.sendMessage(ChatColor.RED + "Message modifier is not turned on");
                        break;
                    }

                    String tmp_entermessage = argsToMessage(args);

                    if(args[0].equalsIgnoreCase("all")){
                        ArrayList<Plots> allPlots = playAreaInfo.getAllPlots();

                        for(Plots p: allPlots){
                            p.setMessageSettings(tmp_entermessage, true, player);
                        }

                        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Enter message successfully set to all play areas!"));
                    }else{
                        Plots p = playAreaInfo.getAllPlots().get(Integer.parseInt(args[0]));
                        p.setMessageSettings(tmp_entermessage, true, player);

                        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Enter message successfully set for play area: " + args[0] + "!"));
                    }
                    break;
                case "leavemessage":
                    if(!playAreaInfo.isMessageModOn()){
                        player.sendMessage(ChatColor.RED + "Message modifier is not turned on");
                        break;
                    }

                    String tmp_leavemessage = argsToMessage(args);

                    if(args[0].equalsIgnoreCase("all")){
                        ArrayList<Plots> allPlots = playAreaInfo.getAllPlots();

                        for(Plots p: allPlots){
                            p.setMessageSettings(tmp_leavemessage, false, player);
                        }

                        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Leave message successfully set for all play areas!"));

                    }else{
                        Plots p = playAreaInfo.getAllPlots().get(Integer.parseInt(args[0]));
                        p.setMessageSettings(tmp_leavemessage, false, player);

                        player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Leave message successfully set for play area: " + args[0] + "!"));
                    }
                    break;
                case "mobs":
                    if(playAreaInfo.isMobModOn()){
                        double insideMobModifier = Double.parseDouble(args[2]);

                        if(args[0].equalsIgnoreCase("all")){
                            ArrayList<Plots> allPlots = playAreaInfo.getAllPlots();

                            for(Plots p: allPlots){
                                p.setInsideMobModifier(insideMobModifier, player);
                            }

                            player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Mob damage increased by " + insideMobModifier + " for all play areas"));
                        }else{
                            Plots p = playAreaInfo.getAllPlots().get(Integer.parseInt(args[0]));
                            p.setInsideMobModifier(insideMobModifier, player);

                            player.sendMessage(ChatColor.GREEN + (ChatColor.BOLD + "Mob damage increased by " + insideMobModifier + " for play area: " + args[0] + "!"));
                        }
                    }
                    else{
                        player.sendMessage(ChatColor.RED + "Mob modifier is not turned on");
                    }
                    break;
                default:
                    invalidInput(player);
            }
        }catch(Exception e){
            invalidInput(player);
        }

        return false;
    }

    private String argsToMessage(String[] args){
        StringBuilder message = new StringBuilder();
        for(int i = 2; i < args.length; i++) {
            if(i == 2){
                message.append(args[i]);
                continue;
            }
            message.append(" ").append(args[i]);
        }

        return message.toString();
    }
    private void invalidInput(Player player){
        player.sendMessage(ChatColor.RED + "Invalid Input. /insideplayarea <plotID> <modtype> <variable> ....");
    }
}
