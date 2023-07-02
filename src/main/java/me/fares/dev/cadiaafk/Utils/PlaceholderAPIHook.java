package me.fares.dev.cadiaafk.Utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.fares.dev.cadiaafk.CadiaAFK;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;

public class PlaceholderAPIHook extends PlaceholderExpansion {
    private final CadiaAFK plugin;
    public PlaceholderAPIHook(CadiaAFK plugin){
        this.plugin = plugin;
    }
    @Override
    public @NotNull String getIdentifier() {
        return "afkrewards";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FaresMahdi120";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.equalsIgnoreCase("player")){
            return player.getName();
        }else if (params.equalsIgnoreCase("prefix")){
            return plugin.getConfigInstance().getString("Messages.Prefix");
        }else if (params.equalsIgnoreCase("time_limit")){
            return String.valueOf(plugin.getConfigInstance().getInt("Settings.Time_To_Reward_In_Minutes"));
        }else if (params.equalsIgnoreCase("time_stayed")){
            DecimalFormat df = new DecimalFormat("#.#");
            return df.format((float) (plugin.timeStayed.get(player.getUniqueId()) / 60));
        }
        return super.onPlaceholderRequest(player, params);
    }
}
