package com.bawnorton.animatedtrims.item;

import com.bawnorton.animatedtrims.client.util.ColourSupplier;
import com.bawnorton.animatedtrims.config.Config;
import net.minecraft.item.Item;

public class AnimatedMaterial extends Item {
    private final boolean isWave;
    private final int duration;
    private final ColourSupplier colourSupplier;


    public AnimatedMaterial(Settings settings, boolean isWave, int duration, ColourSupplier colourSupplier) {
        super(settings);
        this.isWave = isWave;
        this.duration = duration;
        this.colourSupplier = colourSupplier;
    }

    public boolean isWave() {
        return isWave;
    }

    public int getDuration() {
        return duration;
    }

    public ColourSupplier getColourSupplier() {
        return colourSupplier;
    }

    public int getWaveOffset(int i) {
        float intensityPercent = Config.getInstance().waveIntensity / 100f;
        return (int) (duration / 8f * intensityPercent * i);
    }
}
