package com.bawnorton.animatedtrims.mixin.client;

import com.bawnorton.animatedtrims.client.render.AnimatedTrimRenderer;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(value = ArmorFeatureRenderer.class, priority = 1500)
public abstract class ArmorFeatureRendererMixin {
    @ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModelManager;getAtlas(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/texture/SpriteAtlasTexture;"))
    private SpriteAtlasTexture captureAtlas(SpriteAtlasTexture atlas) {
        AnimatedTrimRenderer.setAtlas(atlas);
        return atlas;
    }
}
