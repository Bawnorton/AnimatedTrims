package com.bawnorton.animatedtrims.client.palette;

import com.bawnorton.animatedtrims.client.util.ColourSupplier;

import java.util.HashMap;
import java.util.Map;

public abstract class PaletteGenerator {
    public static AnimatedPalette createPalette(Map<Integer, Integer> palette) {
        return new AnimatedPalette(palette);
    }

    public static AnimatedPalette createPalette(int duration, ColourSupplier colourSupplier) {
        Map<Integer, Integer> animatedPalette = new HashMap<>();
        for (int i = 0; i < duration; i++) {
            animatedPalette.put(i, colourSupplier.getColour(i).getRGB());
        }
        return createPalette(animatedPalette);
    }
}
