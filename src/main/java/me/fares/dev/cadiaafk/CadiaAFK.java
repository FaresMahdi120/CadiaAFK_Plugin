package me.fares.dev.cadiaafk;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.fares.dev.cadiaafk.Configuration.Config;
import me.fares.dev.cadiaafk.Listeners.Eventhandler;
import me.fares.dev.cadiaafk.Utils.MessageUtil;
import me.fares.dev.cadiaafk.Utils.PlaceholderAPIHook;
import me.fares.dev.cadiaafk.Utils.RewardManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public final class CadiaAFK extends JavaPlugin {
    public Config config = new Config(this);
    Commands commands = new Commands(this);
    Eventhandler eventhandler;
    public HashMap<UUID, Integer> timeStayed;
    public HashMap<UUID, Integer> keeper;
    @Override
    public void onEnable() {
        keeper = new HashMap<>();
        timeStayed = new HashMap<>();
        eventhandler = new Eventhandler(this);
        new PlaceholderAPIHook(this).register();
        getServer().getPluginManager().registerEvents(eventhandler, this);
        getCommand("AFKReward").setExecutor(commands);
        getCommand("AFKReward").setTabCompleter(commands);
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("PlaceholderAPI found!");
        } else {
            getLogger().warning("PlaceholderAPI not found!");
        }
        if (getServer().getPluginManager().getPlugin("WorldGuardEvents") != null){
            getLogger().info("WorldGuardEvents found!");
        }else{
            getLogger().severe("WorldGuardEvents which is an important dependency is not installed!");
            getLogger().severe("Disabling " + getName());
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public String replacePlaceHolder(Player player,String input){
        return PlaceholderAPI.setPlaceholders(player, input);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileConfiguration getConfigInstance(){
        return config.getConfig();
    }

    public void Runnable(CadiaAFK plugin, Player player){
        int limit = plugin.getConfigInstance().getInt("Settings.Time_To_Reward_In_Minutes");
        List<Integer> warnings = plugin.getConfigInstance().getIntegerList("Settings.Give_Warning_when_time_is");
        if (limit > 0){
            new BukkitRunnable(){
                @Override
                public void run() {
                    if (keeper.get(player.getUniqueId()) == null && !keeper.containsKey(player.getUniqueId())){
                        this.cancel();
                    }else if (keeper.get(player.getUniqueId()) != null){
                        keeper.replace(player.getUniqueId(), keeper.get(player.getUniqueId()), keeper.get(player.getUniqueId())+1);
                        timeStayed.replace(player.getUniqueId(), timeStayed.get(player.getUniqueId()), timeStayed.get(player.getUniqueId())+1);
                        for (float warning : warnings){
                            if (warning == ((float) keeper.get(player.getUniqueId()) / 60)){
                                String warningMessage = plugin.getConfigInstance().getString("Settings.Warning_Messages_Template");
                                MessageUtil.sendPlayerMessage(player, warningMessage, plugin);
                            }
                        }
                        if ((keeper.get(player.getUniqueId()) / 60) == limit){
                            new BukkitRunnable(){
                                @Override
                                public void run() {
                                    RewardManager.rewardPlayer(player, plugin);
                                    MessageUtil.sendMessage(player, plugin, "REWARD");
                                    MessageUtil.sendTitleMessage(player, plugin, "REWARD");
                                    MessageUtil.playSound(player, plugin, "REWARD");
                                    keeper.replace(player.getUniqueId(), keeper.get(player.getUniqueId()), 0);
                                    timeStayed.replace(player.getUniqueId(), timeStayed.get(player.getUniqueId()), 0);
                                }
                            }.runTask(plugin);
                        }
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 0L, 20L);
        }else {
            plugin.getLogger().log(Level.SEVERE, "Please change the value at <Settings.Time_To_Reward_In_Minutes> to more than 0!");
        }
    }
}
