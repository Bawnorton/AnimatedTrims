package com.bawnorton.animatedtrims.fabric.client.implementation;

import com.bawnorton.animatedtrims.client.AnimatedTrimsClient;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return AnimatedTrimsClient::getConfigScreen;
    }
}
