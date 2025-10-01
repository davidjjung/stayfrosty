package com.davigj.stay_frosty.core.other;

import com.davigj.stay_frosty.core.SFConfig;
import com.davigj.stay_frosty.core.StayFrosty;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Objects;

@EventBusSubscriber(modid = StayFrosty.MOD_ID)
public class SFEvents {
    @SubscribeEvent
    public static void freezeDance(EntityInvulnerabilityCheckEvent event) {
        Entity victim = event.getEntity();
        if (victim.level().isClientSide || (victim instanceof LivingEntity living && living.isDeadOrDying()) || !victim.canFreeze()) {
            return;
        }

        int freezeTicksToAdd = 0;
        Entity source = event.getSource().getEntity();

        if (source instanceof Stray) {
            freezeTicksToAdd = SFConfig.COMMON.strayFreezeTicks.get();
        } else if (source instanceof SnowGolem) {
            freezeTicksToAdd = SFConfig.COMMON.snowGolemFreezeTicks.get();
        } else if (event.getSource().getDirectEntity() instanceof Snowball) {
            freezeTicksToAdd = SFConfig.COMMON.snowballFreezeTicks.get();
        } else if (source != null && source.getType().is(SFEntityTypeTags.FROST_DAMAGE_DEALERS)) {
            freezeTicksToAdd = SFConfig.COMMON.otherFreezeTicks.get();
        }

        if (freezeTicksToAdd > 0) {
            int currentTicks = victim.getTicksFrozen();
            int maxTicks = victim.getTicksRequiredToFreeze() + SFConfig.COMMON.frostCap.get();
            victim.setTicksFrozen(Math.min(currentTicks + freezeTicksToAdd, maxTicks));
        }
    }


//    @SubscribeEvent
//    public static void featherFrost(PlayerTickEvent event) {
//        if (!ModList.get().isLoaded("feathers")) return;
//        if (event.getEntity().getTicksFrozen() > 0 && event.getEntity().canFreeze()) {
//            Player player = event.getEntity();
//            MobEffectInstance currentColdEffect = player.getEffect(FeathersEffects.COLD);
//            if (currentColdEffect != null) {
//                int newDuration = Math.min(40, currentColdEffect.getDuration());
//                player.addEffect(new MobEffectInstance(FeathersEffects.COLD.get(), newDuration));
//            } else {
//                player.addEffect(new MobEffectInstance(FeathersEffects.COLD.get(), 40 + player.getRandom().nextInt(40)));
//            }
//        }
//    }

    @SubscribeEvent
    public static void snowParticles(EntityTickEvent.Post event) {
        if (!SFConfig.CLIENT.freezingParticles.get()) return;
        if (event.getEntity() instanceof LivingEntity living && living.getTicksFrozen() > living.getTicksRequiredToFreeze()) {
            Level level = living.level();
            if (level.isClientSide) {
                RandomSource randomsource = level.getRandom();
                if (randomsource.nextInt(7) == 1) {
                    level.addParticle(ParticleTypes.SNOWFLAKE, living.getX(), (double)(living.blockPosition().getY()
                                    + (living.getEyeHeight() * 0.8) + (living.getRandom().nextGaussian() * 0.2)), living.getZ(),
                            (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F),
                            (double)0.05F, (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F));
                }
            }
        }
    }


}