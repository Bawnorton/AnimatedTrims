package com.bawnorton.animatedtrims.forge.client;

import com.bawnorton.animatedtrims.AnimatedTrims;
import com.bawnorton.animatedtrims.client.AnimatedTrimsClient;
import com.bawnorton.animatedtrims.client.implementation.YACLImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AnimatedTrims.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AnimatedTrimsForgeClient {
    public static void init(FMLClientSetupEvent event) {
        AnimatedTrimsClient.init();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> YACLImpl.getScreen(screen)));
    }
}
