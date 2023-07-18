package com.bawnorton.animatedtrims.client.palette;

import com.bawnorton.animatedtrims.item.AnimatedMaterial;

import java.util.HashMap;
import java.util.Map;

public class PaletteStorage {
    private static final Map<AnimatedMaterial, AnimatedPalette> PALETTES = new HashMap<>();

    public static AnimatedPalette getPalette(AnimatedMaterial material) {
        if (PALETTES.containsKey(material)) return PALETTES.get(material);

        AnimatedPalette palette = PaletteGenerator.createPalette(material.getDuration(), material.getColourSupplier());
        PALETTES.put(material, palette);
        return palette;
    }
}
