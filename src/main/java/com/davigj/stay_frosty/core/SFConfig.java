package com.davigj.stay_frosty.core;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class SFConfig {
    public static class Common {
        public final ModConfigSpec.ConfigValue<Integer> strayFreezeTicks;
        public final ModConfigSpec.ConfigValue<Boolean> straySlownessDisable;
        public final ModConfigSpec.ConfigValue<Integer> snowGolemFreezeTicks;
        public final ModConfigSpec.ConfigValue<Integer> snowballFreezeTicks;
        public final ModConfigSpec.ConfigValue<Integer> otherFreezeTicks;
        public final ModConfigSpec.ConfigValue<Integer> bloviatorFreezeTicks;
        public final ModConfigSpec.ConfigValue<Integer> frostCap;

        Common (ModConfigSpec.Builder builder) {
            builder.push("changes");
            strayFreezeTicks = builder.comment("Ticks of frost added to an entity upon getting hit by a stray").define("Stray freeze ticks", 200);
            snowGolemFreezeTicks = builder.comment("Ticks of frost added to an entity upon getting hit by a snow golem").define("Snow Golem freeze ticks", 150);
            snowballFreezeTicks = builder.comment("Ticks of frost added to an entity upon getting hit by a snowball").define("Snowball freeze ticks", 50);
            otherFreezeTicks = builder.comment("Ticks of frost added to an entity upon getting hurt by entities in the frost_damage_dealers tag").define("Frost damage dealer ticks", 150);
            bloviatorFreezeTicks = builder.comment("Frost ticks upon getting hit by the wind of a Bloviator from Cloud Storage").define("Bloviator freeze ticks", 3);
            frostCap = builder.comment("Upper bound for ticks of frost accrued by mob attacks").define("Freeze cap", 300);
            straySlownessDisable = builder.comment("Stray arrows no longer give Slowness").define("Stray slowness disabled", true);
            builder.pop();
        }
    }

    public static class Client {
        public final ModConfigSpec.ConfigValue<Boolean> snowParticles;
        public final ModConfigSpec.ConfigValue<Boolean> freezingParticles;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("client");
            snowParticles = builder.comment("Stray arrows emit snowflake particles").define("Stray snowflake particles", true);
            freezingParticles = builder.comment("Freezing entities emit snowflake particles").define("Freezing snowflake particles", true);
            builder.pop();
        }
    }

    static final ModConfigSpec COMMON_SPEC;
    public static final SFConfig.Common COMMON;

    public static final ModConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;


    static {
        final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(SFConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();

        Pair<Client, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
