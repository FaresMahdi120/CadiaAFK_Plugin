package me.fares.dev.cadiaafk.Utils;

import me.fares.dev.cadiaafk.CadiaAFK;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class RewardManager {


    public static void rewardPlayer(Player player, CadiaAFK plugin){
        List<String> commands = plugin.getConfigInstance().getStringList("Settings.Rewards");
        for (String command : commands){
            if (command.startsWith("/")){
                command.replace("/", "");
            }
            plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), MessageUtil.colorize(command, plugin, player));
        }
    }
}
