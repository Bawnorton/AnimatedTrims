package com.bawnorton.animatedtrims;

import com.bawnorton.animatedtrims.config.ConfigManager;
import com.bawnorton.animatedtrims.item.AnimatedTrimsItems;
import com.bawnorton.animatedtrims.util.LogWrapper;
import net.minecraft.util.Identifier;
import org.slf4j.LoggerFactory;

public class AnimatedTrims {
    public static final String MOD_ID = "animatedtrims";
    public static final String TRIM_ASSET_NAME = "animated";
    public static final LogWrapper LOGGER = LogWrapper.of(LoggerFactory.getLogger(MOD_ID), "[AnimatedTrims]");

    public static void init() {
        LOGGER.info("AnimatedTrims Initialized!");
        ConfigManager.loadConfig();
        AnimatedTrimsItems.init();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
