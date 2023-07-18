package com.bawnorton.animatedtrims.client.palette;

import java.awt.*;
import java.util.Map;

public class AnimatedPalette {
    private static int currentFrame = 0;
    private final Map<Integer, Integer> animatedPalette;

    public AnimatedPalette(Map<Integer, Integer> animatedPalette) {
        this.animatedPalette = animatedPalette;
    }

    public static void incrementFrame() {
        currentFrame++;
    }

    public Color currentColour(int offset) {
        return new Color(animatedPalette.get((currentFrame + offset) % animatedPalette.size()));
    }

    public Color currentColour() {
        return currentColour(0);
    }

    public Color[] getColours() {
        return animatedPalette.values().stream().map(Color::new).toArray(Color[]::new);
    }

    public int getDuration() {
        return animatedPalette.size();
    }
}
