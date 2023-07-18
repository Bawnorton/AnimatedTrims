package com.bawnorton.animatedtrims.mixin.client;

import com.bawnorton.animatedtrims.client.extend.InlinedConditionExtender;
import com.bawnorton.animatedtrims.client.extend.ModelOverrideConditionExtender;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.render.model.json.ModelOverride;
import net.minecraft.client.render.model.json.ModelOverrideList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.bawnorton.animatedtrims.client.AnimatedTrimsClient.MATERIAL;

@Mixin(ModelOverrideList.class)
public abstract class ModelOverrideListMixin {
    @Inject(method = "method_33696", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/json/ModelOverrideList$InlinedCondition;<init>(IF)V"))
    private static void captureMaterial(Object2IntMap<?> map, ModelOverride.Condition condition, CallbackInfoReturnable<?> cir) {
        MATERIAL.set(((ModelOverrideConditionExtender) condition).animatedTrims$getMaterial());
    }

    @Mixin(ModelOverrideList.InlinedCondition.class)
    abstract static class InlinedConditionMixin implements InlinedConditionExtender {
        @Unique
        private String animatedTrims$material;

        @Inject(method = "<init>", at = @At("RETURN"))
        private void setMaterial(CallbackInfo ci) {
            this.animatedTrims$material = MATERIAL.get();
            MATERIAL.set(null);
        }

        @Override
        public String animatedTrims$getMaterial() {
            return animatedTrims$material;
        }
    }
}
