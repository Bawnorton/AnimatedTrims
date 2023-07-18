package com.bawnorton.animatedtrims.mixin.client;

import com.bawnorton.animatedtrims.client.palette.AnimatedPalette;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "renderWorld", at = @At("HEAD"))
    private void incrementAnimationFrame(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        AnimatedPalette.incrementFrame();
    }
}
