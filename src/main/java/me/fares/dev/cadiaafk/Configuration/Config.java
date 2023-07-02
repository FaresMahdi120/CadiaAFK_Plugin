package me.fares.dev.cadiaafk.Configuration;

import lombok.Getter;
import me.fares.dev.cadiaafk.CadiaAFK;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {
    private final CadiaAFK plugin;

    @Getter
    public FileConfiguration Config;

    private final File languageFile;

    public Config(CadiaAFK plugin) {
        this.plugin = plugin;
        this.languageFile = new File(plugin.getDataFolder(), "config.yml");

        loadConfigurations();
    }

    public void loadConfigurations() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        if (!languageFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        plugin.getLogger().info("Loading config.yml...");
        Config = YamlConfiguration.loadConfiguration(languageFile);
    }


    public void reloadConfig() {
        Config = YamlConfiguration.loadConfiguration(languageFile);
    }
}
