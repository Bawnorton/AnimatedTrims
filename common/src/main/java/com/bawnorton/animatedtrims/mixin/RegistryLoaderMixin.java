package com.bawnorton.animatedtrims.mixin;

import com.bawnorton.animatedtrims.AnimatedTrims;
import com.bawnorton.animatedtrims.util.DebugHelper;
import com.bawnorton.animatedtrims.util.TrimMaterialHelper;
import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryLoader;
import net.minecraft.resource.Resource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Iterator;
import java.util.Map;

@Mixin(RegistryLoader.class)
public abstract class RegistryLoaderMixin {
    // pure cursedness
    @ModifyExpressionValue(method = "load(Lnet/minecraft/registry/RegistryOps$RegistryInfoGetter;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/registry/MutableRegistry;Lcom/mojang/serialization/Decoder;Ljava/util/Map;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceFinder;findResources(Lnet/minecraft/resource/ResourceManager;)Ljava/util/Map;"))
    private static Map<Identifier, Resource> addAllTrimMaterialJsonFiles(Map<Identifier, Resource> original) {
        Iterator<Map.Entry<Identifier, Resource>> iterator = original.entrySet().iterator();
        if (!iterator.hasNext()) return original;

        Map.Entry<Identifier, Resource> first = original.entrySet().iterator().next();
        if (!first.getKey().getPath().contains("trim_material")) return original;

        TrimMaterialHelper.loopTrimMaterials((item) -> {
            Identifier itemId = Registries.ITEM.getId(item);

            JsonObject resourceJson = new JsonObject();
            try {
                resourceJson.addProperty("asset_name", AnimatedTrims.TRIM_ASSET_NAME);
                JsonObject description = new JsonObject();
                description.addProperty("color", "#FFFFFF");
                description.addProperty("translate", animatedTrims$escape(item.getName().getString()));
                resourceJson.add("description", description);
                resourceJson.addProperty("ingredient", itemId.toString());
                resourceJson.addProperty("item_model_index", Float.MAX_VALUE);
            } catch (RuntimeException e) {
                AnimatedTrims.LOGGER.error("Failed to generate trim material JSON for " + itemId, e);
                return;
            }

            Resource resource = new Resource(first.getValue().getPack(), () -> IOUtils.toInputStream(resourceJson.toString(), "UTF-8"));
            Identifier resourceId = new Identifier(itemId.getNamespace(), "trim_material/" + itemId.getPath() + ".json");
            original.put(resourceId, resource);

            DebugHelper.createDebugFile("trim_materials", itemId + ".json", resourceJson.toString());
        });
        return original;
    }

    @Unique
    private static String animatedTrims$escape(String string) {
        return string.replace("\"", "\\\"");
    }
}