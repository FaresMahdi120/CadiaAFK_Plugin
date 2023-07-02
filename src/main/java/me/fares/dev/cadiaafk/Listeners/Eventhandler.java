package me.fares.dev.cadiaafk.Listeners;

import me.fares.dev.cadiaafk.CadiaAFK;
import me.fares.dev.cadiaafk.Utils.MessageUtil;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionLeftEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.swing.*;
import java.util.List;

public class Eventhandler implements Listener {
    private final CadiaAFK plugin;


    public Eventhandler(CadiaAFK plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onRegionEnter(RegionEnteredEvent e){
        List<String> listOfRegions = plugin.getConfigInstance().getStringList("Settings.WorldGuard_Regions");
        for (String region : listOfRegions){
            if (e.getRegionName().equals(region)){
                plugin.keeper.put(e.getUUID(), 0);
                plugin.timeStayed.put(e.getUUID(), 0);
                MessageUtil.sendMessage(e.getPlayer(), plugin, "ENTER");
                MessageUtil.sendTitleMessage(e.getPlayer(), plugin, "ENTER");
                MessageUtil.playSound(e.getPlayer(), plugin, "ENTER");
                plugin.Runnable(plugin, e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onRegionLeave(RegionLeftEvent e){
        List<String> listOfRegions = plugin.getConfigInstance().getStringList("Settings.WorldGuard_Regions");
        for (String region : listOfRegions){
            if (e.getRegionName().equals(region)){
                if (!plugin.timeStayed.containsKey(e.getUUID())){
                    plugin.timeStayed.put(e.getUUID(), 0);
                }else plugin.timeStayed.putIfAbsent(e.getUUID(), 0);
                plugin.keeper.remove(e.getUUID());
                MessageUtil.sendMessage(e.getPlayer(), plugin, "LEAVE");
                MessageUtil.sendTitleMessage(e.getPlayer(), plugin, "LEAVE");
                MessageUtil.playSound(e.getPlayer(), plugin, "LEAVE");
            }
        }
    }

}
