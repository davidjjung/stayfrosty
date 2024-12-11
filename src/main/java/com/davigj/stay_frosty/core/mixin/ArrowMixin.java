package com.davigj.stay_frosty.core.mixin;

import com.davigj.stay_frosty.core.SFConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Arrow.class)
public abstract class ArrowMixin extends AbstractArrow{

    protected ArrowMixin(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    @Inject(method = "makeParticle", at = @At("HEAD"))
    public void snowyStrayArrow(int p_36877_, CallbackInfo ci) {
        Arrow arrow = (Arrow)(Object)this;
        if (arrow.getOwner() instanceof Stray && SFConfig.CLIENT.snowParticles.get() && this.inGroundTime < 60) {
            for(int j = 0; j < p_36877_; ++j) {
                this.level.addParticle(ParticleTypes.SNOWFLAKE, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0, 0, 0);
            }
        }
    }
}
