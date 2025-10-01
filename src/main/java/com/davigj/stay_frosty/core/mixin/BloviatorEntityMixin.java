package com.davigj.stay_frosty.core.mixin;

import com.davigj.stay_frosty.core.SFConfig;
import com.github.alexthe668.cloudstorage.entity.BloviatorEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Pseudo
@Mixin(BloviatorEntity.class)
public class BloviatorEntityMixin {
    @Inject(method = "m_8119_", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;m_20256_(Lnet/minecraft/world/phys/Vec3;)V"),
    locals = LocalCapture.CAPTURE_FAILHARD)
    private void blowback(CallbackInfo ci, Entity pushing, Vec3 mouth, Vec3 vec2) {
        if (pushing != null) {
            int currentTicks = pushing.getTicksFrozen();
            int maxTicks = pushing.getTicksRequiredToFreeze() + SFConfig.COMMON.frostCap.get();
            pushing.setTicksFrozen(Math.min(currentTicks + SFConfig.COMMON.bloviatorFreezeTicks.get(), maxTicks));
        }
    }
}
