package com.bawnorton.animatedtrims.config;

import com.bawnorton.animatedtrims.AnimatedTrims;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import dev.architectury.platform.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path configPath = Platform.getConfigFolder().resolve("animatedtrims.json");

    public static void loadConfig() {
        Config config = load();

        if(config.debug == null) config.debug = false;
        if(config.waveIntensity == null) config.waveIntensity = 50;
        if(config.waveIntensity < 1) config.waveIntensity = 1;
        if(config.waveIntensity > 100) config.waveIntensity = 100;

        Config.update(config);
        save();
        AnimatedTrims.LOGGER.info("Config loaded");
    }

    private static Config load() {
        Config config = Config.getInstance();
        try {
            if (!Files.exists(configPath)) {
                Files.createDirectories(configPath.getParent());
                Files.createFile(configPath);
                return config;
            }
            try {
                config = GSON.fromJson(Files.newBufferedReader(configPath), Config.class);
            } catch (JsonSyntaxException e) {
                AnimatedTrims.LOGGER.error("Failed to parse config file, using default config");
                config = new Config();
            }
        } catch (IOException e) {
            AnimatedTrims.LOGGER.error("Failed to load config", e);
        }
        return config;
    }

    private static void save() {
        try {
            Files.write(configPath, GSON.toJson(Config.getInstance()).getBytes());
        } catch (IOException e) {
            AnimatedTrims.LOGGER.error("Failed to save config", e);
        }
    }

    public static void saveConfig() {
        save();
        AnimatedTrims.LOGGER.info("Saved client config");
    }
}
