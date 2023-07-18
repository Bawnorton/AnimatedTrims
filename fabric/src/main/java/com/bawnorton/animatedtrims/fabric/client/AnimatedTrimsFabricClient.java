package com.bawnorton.animatedtrims.fabric.client;

import com.bawnorton.animatedtrims.client.AnimatedTrimsClient;
import net.fabricmc.api.ClientModInitializer;

public class AnimatedTrimsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AnimatedTrimsClient.init();
    }
}
