package me.fares.dev.cadiaafk;

import me.fares.dev.cadiaafk.Utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Commands implements TabExecutor {

    private final CadiaAFK plugin;

    public Commands(CadiaAFK plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            switch (strings.length){
                case 0:
                    MessageUtil.sendPlayerMessage(player, "%afkrewards_prefix% &cNot enough arguments!",plugin);
                    break;
                case 1:
                    switch (strings[0]){
                        case "Reload":
                            plugin.config.reloadConfig();
                            MessageUtil.sendPlayerMessage(player, "%afkrewards_prefix% &dReloaded the configuration!",plugin);
                            break;
                        default:
                            MessageUtil.sendPlayerMessage(player, "%afkrewards_prefix% &cUse correct subcommands!",plugin);
                            break;
                    }
                    break;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (command.getName().equals("AFKReward")){
            if (strings.length == 1){
                List<String> tabComplete = new ArrayList<>();
                tabComplete.add("Reload");
                return tabComplete;
            }
        }
        return null;
    }
}
