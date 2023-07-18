package com.bawnorton.animatedtrims.item;

import com.bawnorton.animatedtrims.AnimatedTrims;
import com.bawnorton.animatedtrims.client.palette.Interpolation;
import com.bawnorton.animatedtrims.client.util.ColourSupplier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AnimatedTrimsItems {
    public static final List<Item> ITEMS = new ArrayList<>(); 
    
    public static final Item RAINBOW_MATERIAL = registerMaterial("rainbow_material", false,
            frame -> Color.getHSBColor((float) (frame % 360) / 360, 1.0F, 1.0F));
    public static final Item RAINBOW_WAVE_MATERIAL = registerMaterial("rainbow_wave_material", true,
            frame -> Color.getHSBColor((float) (frame % 360) / 360, 1.0F, 1.0F));
    public static final Item RED_MATERIAL = registerSingleColourMaterial("red_material", Color.RED);
    public static final Item ORANGE_MATERIAL = registerSingleColourMaterial("orange_material", Color.ORANGE);
    public static final Item YELLOW_MATERIAL = registerSingleColourMaterial("yellow_material", Color.YELLOW);
    public static final Item GREEN_MATERIAL = registerSingleColourMaterial("green_material", Color.GREEN);
    public static final Item BLUE_MATERIAL = registerSingleColourMaterial("blue_material", Color.BLUE);
    public static final Item PURPLE_MATERIAL = registerSingleColourMaterial("purple_material", new Color(128, 0, 255));
    public static final Item MAGENTA_MATERIAL = registerSingleColourMaterial("magenta_material", Color.MAGENTA);

    public static void init() {
        AnimatedTrims.LOGGER.debug("Initializing Items");
    }

    private static Item registerMaterial(String name, boolean isWave, ColourSupplier colourSupplier) {
        Item item = Registry.register(Registries.ITEM, AnimatedTrims.id(name), new AnimatedMaterial(new Item.Settings(), isWave, 360, colourSupplier));
        ITEMS.add(item);
        return item;
    }

    private static Item registerSingleColourMaterial(String name, Color colour) {
        Item item = Registry.register(Registries.ITEM, AnimatedTrims.id(name), new AnimatedMaterial(new Item.Settings(), false, 200, fadeBetween(colour.brighter(), colour.darker(), 200)));
        ITEMS.add(item);
        return item;
    }

    private static Item registerBiColourMaterial(String name, boolean isWave, Color colour1, Color colour2) {
        Item item = Registry.register(Registries.ITEM, AnimatedTrims.id(name), new AnimatedMaterial(new Item.Settings(), isWave, 360, fadeBetween(colour1, colour2, 360)));
        ITEMS.add(item);
        return item;
    }

    private static ColourSupplier fadeBetween(Color color1, Color color2, int duration) {
        return frame -> {
            if(frame <= duration / 2) {
                return new Color(Interpolation.SMOOTHER.apply(color1.getRGB(), color2.getRGB(), (float) (frame % duration) / duration));
            }
            return new Color(Interpolation.SMOOTHER.apply(color2.getRGB(), color1.getRGB(), (float) (frame % duration) / duration));
        };
    }
}
