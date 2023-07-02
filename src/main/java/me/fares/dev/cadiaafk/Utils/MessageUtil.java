package me.fares.dev.cadiaafk.Utils;

import me.fares.dev.cadiaafk.CadiaAFK;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtil {


    public static void sendTitleMessage(Player player, CadiaAFK plugin, String key){
        int time = plugin.getConfigInstance().getInt("Titles.General_Settings.Title_Time");
        int fadein = plugin.getConfigInstance().getInt("Titles.General_Settings.Fade_In");
        int fadeout = plugin.getConfigInstance().getInt("Titles.General_Settings.Fade_out");
        String title;
        String subTitle;
        switch (key){
            case "ENTER":
                title = plugin.getConfigInstance().getString("Titles.PlayerJoinPoolTitle");
                subTitle = plugin.getConfigInstance().getString("Titles.PlayerJoinPoolSub_Title");
                player.sendTitle(colorize(title, plugin, player), colorize(subTitle, plugin, player), (time * 20), (fadein * 20), (fadeout * 20));
                break;
            case "LEAVE":
                title = plugin.getConfigInstance().getString("Titles.PlayerLeavePoolTitle");
                subTitle = plugin.getConfigInstance().getString("Titles.PlayerLeavePoolSub_Title");
                player.sendTitle(colorize(title, plugin, player), colorize(subTitle, plugin, player), (time * 20), (fadein * 20), (fadeout * 20));
                break;
            case "REWARD":
                title = plugin.getConfigInstance().getString("Titles.PlayerRewardTitle");
                subTitle = plugin.getConfigInstance().getString("Titles.PlayerRewardSub_Title");
                player.sendTitle(colorize(title, plugin, player), colorize(subTitle, plugin, player), (time * 20), (fadein * 20), (fadeout * 20));
                break;
        }
    }
    public static void sendMessage(Player player, CadiaAFK plugin, String key){
        String message;
        switch (key){
            case "ENTER":
                message = plugin.getConfigInstance().getString("Messages.Player_Enter_Pool_Message");
                sendPlayerMessage(player, message, plugin);
                break;
            case "LEAVE":
                message = plugin.getConfigInstance().getString("Messages.Player_Leave_Pool_Message");
                sendPlayerMessage(player, message, plugin);
                break;
            case "REWARD":
                message = plugin.getConfigInstance().getString("Messages.Player_Rewarded_message");
                sendPlayerMessage(player, message, plugin);
                break;
        }
    }
    public static void playSound(Player player, CadiaAFK plugin, String key){
        int volume = plugin.getConfigInstance().getInt("Sound_Effect.volume");
        int pitch = plugin.getConfigInstance().getInt("Sound_Effect.Pitch");
        String sound;
        switch (key){
            case "ENTER":
                sound = plugin.getConfigInstance().getString("Sound_Effect.Sound_Effect_Entering").toLowerCase();
                player.playSound(player, sound, volume, pitch);
                break;
            case "LEAVE":
                sound = plugin.getConfigInstance().getString("Sound_Effect.Sound_Effect_Leaving").toLowerCase();
                player.playSound(player, sound, volume, pitch);
                break;
            case "REWARD":
                sound = plugin.getConfigInstance().getString("Sound_Effect.Sound_Effect_Rewarded").toLowerCase();
                player.playSound(player, sound, volume, pitch);
                break;
        }
    }
    public static void sendPlayerMessage(Player player, String message, CadiaAFK plugin){
        player.sendMessage(colorize(message, plugin, player));
    }
    public static String colorize(String input, CadiaAFK plugin, Player player){
        return ChatColor.translateAlternateColorCodes('&', plugin.replacePlaceHolder(player, input));
    }
}
