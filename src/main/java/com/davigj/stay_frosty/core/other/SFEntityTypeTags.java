package com.davigj.stay_frosty.core.other;

import com.davigj.stay_frosty.core.StayFrosty;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class SFEntityTypeTags {
    public static final TagKey<EntityType<?>> FROST_DAMAGE_DEALERS = entityTypeTag("frost_damage_dealers");

    private static TagKey<EntityType<?>> entityTypeTag(String name) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(StayFrosty.MOD_ID, name));
    }
}
