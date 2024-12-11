package com.davigj.stay_frosty.core.mixin;

import com.davigj.stay_frosty.core.SFConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Stray.class)
public abstract class StrayMixin extends AbstractSkeleton {
    protected StrayMixin(EntityType<? extends AbstractSkeleton> p_32133_, Level p_32134_) {
        super(p_32133_, p_32134_);
    }

    @Inject(method = "getArrow", at = @At("RETURN"), cancellable = true)
    public void modifyArrow(ItemStack stack, float p_33847_, CallbackInfoReturnable<AbstractArrow> cir) {
        if (SFConfig.COMMON.straySlownessDisable.get()) {
            cir.setReturnValue(super.getArrow(stack, p_33847_));
        }
    }
}
