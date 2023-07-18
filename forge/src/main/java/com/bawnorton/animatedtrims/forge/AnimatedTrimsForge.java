package com.bawnorton.animatedtrims.forge;

import com.bawnorton.animatedtrims.AnimatedTrims;
import com.bawnorton.animatedtrims.client.AnimatedTrimsClient;
import com.bawnorton.animatedtrims.client.implementation.YACLImpl;
import com.bawnorton.animatedtrims.forge.client.AnimatedTrimsForgeClient;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AnimatedTrims.MOD_ID)
public class AnimatedTrimsForge {
    public AnimatedTrimsForge() {
        EventBuses.registerModEventBus(AnimatedTrims.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        AnimatedTrims.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(AnimatedTrimsForgeClient::init);
    }
}
