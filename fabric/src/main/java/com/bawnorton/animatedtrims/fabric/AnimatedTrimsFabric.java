package com.bawnorton.animatedtrims.fabric;

import com.bawnorton.animatedtrims.AnimatedTrims;
import net.fabricmc.api.ModInitializer;

public class AnimatedTrimsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AnimatedTrims.init();
    }
}
