package com.bawnorton.animatedtrims.mixin.client;

import com.bawnorton.animatedtrims.client.palette.AnimatedPalette;
import com.bawnorton.animatedtrims.client.palette.PaletteStorage;
import com.bawnorton.animatedtrims.item.AnimatedMaterial;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Optional;

@Mixin(ArmorTrim.class)
public abstract class ArmorTrimMixin {

    @Shadow
    public static Optional<ArmorTrim> getTrim(DynamicRegistryManager registryManager, ItemStack stack) {
        throw new AssertionError();
    }

    @ModifyExpressionValue(method = "appendTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;append(Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;"))
    private static MutableText updateColour(MutableText original, ItemStack stack, DynamicRegistryManager registryManager, List<Text> tooltip) {
        ArmorTrim trim = getTrim(registryManager, stack).orElseThrow(AssertionError::new);
        if(!(trim.getMaterial().value().ingredient().value() instanceof AnimatedMaterial animatedMaterial)) return original;

        AnimatedPalette palette = PaletteStorage.getPalette(animatedMaterial);
        Style style = original.getStyle().withColor(palette.currentColour().getRGB());
        return Text.literal(original.getString()).setStyle(style);
    }
}
