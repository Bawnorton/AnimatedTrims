package com.bawnorton.animatedtrims.client;

import com.bawnorton.animatedtrims.AnimatedTrims;
import com.bawnorton.animatedtrims.Compat;
import com.bawnorton.animatedtrims.client.implementation.YACLImpl;
import com.bawnorton.animatedtrims.client.palette.AnimatedPalette;
import com.bawnorton.animatedtrims.client.palette.PaletteStorage;
import com.bawnorton.animatedtrims.client.util.ImageUtil;
import com.bawnorton.animatedtrims.config.Config;
import com.bawnorton.animatedtrims.item.AnimatedMaterial;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AnimatedTrimsClient {
    public static final ThreadLocal<String> MATERIAL = new ThreadLocal<>();

    public static void init() {
        AnimatedTrims.LOGGER.info("Initializing AnimatedTrims Client");

        //Andrew6rant https://github.com/Andrew6rant provided the proof of concept code for this
        //noinspection SuspiciousToArrayCall
        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> {
            ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
            if (networkHandler == null) return -1;

            DynamicRegistryManager registryManager = networkHandler.getRegistryManager();
            Optional<ArmorTrim> optionalTrim = ArmorTrim.getTrim(registryManager, stack);
            if(optionalTrim.isEmpty()) {
                if(stack.getItem() instanceof DyeableArmorItem dyeableArmorItem) return tintIndex == 0 ? dyeableArmorItem.getColor(stack) : -1;
                return -1;
            }

            ArmorTrimMaterial trimMaterial = optionalTrim.get().getMaterial().value();
            Item trimItem = trimMaterial.ingredient().value();
            String assetName =  trimMaterial.assetName();
            if(!assetName.equals(AnimatedTrims.TRIM_ASSET_NAME)) return -1;
            if(!(trimItem instanceof AnimatedMaterial animatedMaterial)) return -1;

            AnimatedPalette palette = PaletteStorage.getPalette(animatedMaterial);
            if(stack.getItem() instanceof DyeableArmorItem dyeableArmorItem) {
                if(tintIndex == 0) return dyeableArmorItem.getColor(stack);
                if(tintIndex >= 2) {
                    if(!animatedMaterial.isWave()) return palette.currentColour().getRGB();

                    return palette.currentColour(animatedMaterial.getWaveOffset(tintIndex - 2)).getRGB();
                }
                return -1;
            }

            if(tintIndex < 1) return -1;
            Map<Integer, Float> tintIndexBrightness = Map.of(1, 0.5f, 2, 0.7f, 3, 0.9f);
            if(!animatedMaterial.isWave()) return ImageUtil.changeBrightness(palette.currentColour(), tintIndexBrightness.getOrDefault(tintIndex, 1f)).getRGB();

            return ImageUtil.changeBrightness(palette.currentColour(animatedMaterial.getWaveOffset(tintIndex - 1)), tintIndexBrightness.getOrDefault(tintIndex, 1f)).getRGB();
        }, Registries.ITEM.stream().filter(item -> item instanceof Equipment).toArray(Item[]::new));
    }

    public static Screen getConfigScreen(Screen parent) {
        if (Compat.isYaclLoaded()) {
            return YACLImpl.getScreen(parent);
        } else {
            return new ConfirmScreen((result) -> {
                if (result) {
                    Util.getOperatingSystem().open(URI.create("https://modrinth.com/mod/yacl/versions"));
                }
                MinecraftClient.getInstance().setScreen(parent);
            }, Text.of("Yet Another Config Lib not installed!"), Text.of("YACL 3 is required to edit the config in game, would you like to install YACL 3?"), ScreenTexts.YES, ScreenTexts.NO);
        }
    }
}
