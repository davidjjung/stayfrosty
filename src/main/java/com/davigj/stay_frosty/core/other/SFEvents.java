package com.davigj.stay_frosty.core.other;

import com.davigj.stay_frosty.core.StayFrosty;
import com.davigj.stay_frosty.core.SFConfig;
import com.elenai.feathers.effect.FeathersEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = StayFrosty.MOD_ID)
public class SFEvents {
    @SubscribeEvent
    public static void freezeDance(LivingAttackEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim.level.isClientSide || victim.isDeadOrDying() || !victim.canFreeze()) {
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


    @SubscribeEvent
    public static void featherFrost(TickEvent.PlayerTickEvent event) {
        if (!ModList.get().isLoaded("feathers")) return;
        if (event.player.getTicksFrozen() > 0 && event.player.canFreeze()) {
            Player player = event.player;
            MobEffectInstance currentColdEffect = player.getEffect(FeathersEffects.COLD.get());
            if (currentColdEffect != null) {
                int newDuration = Math.min(40, currentColdEffect.getDuration());
                player.addEffect(new MobEffectInstance(FeathersEffects.COLD.get(), newDuration));
            } else {
                player.addEffect(new MobEffectInstance(FeathersEffects.COLD.get(), 40 + player.getRandom().nextInt(40)));
            }
        }
    }

    @SubscribeEvent
    public static void snowParticles(LivingEvent.LivingTickEvent event) {
        if (!SFConfig.CLIENT.freezingParticles.get()) return;
        LivingEntity entity = event.getEntity();
        if (entity.getTicksFrozen() > entity.getTicksRequiredToFreeze()) {
            Level level = entity.level;
            if (level.isClientSide) {
                RandomSource randomsource = level.getRandom();
                if (randomsource.nextInt(7) == 1) {
                    level.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), (double)(entity.blockPosition().getY()
                                    + (entity.getEyeHeight() * 0.8) + (entity.getRandom().nextGaussian() * 0.2)), entity.getZ(),
                            (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F),
                            (double)0.05F, (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F));
                }
            }
        }
    }


}