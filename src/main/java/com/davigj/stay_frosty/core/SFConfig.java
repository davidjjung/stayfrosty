package com.davigj.stay_frosty.core;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class SFConfig {
    public static class Common {
        public final ForgeConfigSpec.ConfigValue<Integer> strayFreezeTicks;
        public final ForgeConfigSpec.ConfigValue<Boolean> straySlownessDisable;
        public final ForgeConfigSpec.ConfigValue<Integer> snowGolemFreezeTicks;
        public final ForgeConfigSpec.ConfigValue<Integer> snowballFreezeTicks;
        public final ForgeConfigSpec.ConfigValue<Integer> otherFreezeTicks;
        public final ForgeConfigSpec.ConfigValue<Integer> frostCap;

        Common (ForgeConfigSpec.Builder builder) {
            builder.push("changes");
            strayFreezeTicks = builder.comment("Ticks of frost added to an entity upon getting hit by a stray").define("Stray freeze ticks", 200);
            snowGolemFreezeTicks = builder.comment("Ticks of frost added to an entity upon getting hit by a snow golem").define("Snow Golem freeze ticks", 150);
            snowballFreezeTicks = builder.comment("Ticks of frost added to an entity upon getting hit by a snowball").define("Snowball freeze ticks", 50);
            otherFreezeTicks = builder.comment("Ticks of frost added to an entity upon getting hurt by entities in the frost_damage_dealers tag").define("Frost damage dealer ticks", 150);
            frostCap = builder.comment("Upper bound for ticks of frost accrued by mob attacks").define("Freeze cap", 300);
            straySlownessDisable = builder.comment("Stray arrows no longer give Slowness").define("Stray slowness disabled", true);
            builder.pop();
        }
    }

    public static class Client {
        public final ForgeConfigSpec.ConfigValue<Boolean> snowParticles;
        public final ForgeConfigSpec.ConfigValue<Boolean> freezingParticles;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("client");
            snowParticles = builder.comment("Stray arrows emit snowflake particles").define("Stray snowflake particles", true);
            freezingParticles = builder.comment("Freezing entities emit snowflake particles").define("Freezing snowflake particles", true);
            builder.pop();
        }
    }

    static final ForgeConfigSpec COMMON_SPEC;
    public static final SFConfig.Common COMMON;

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;


    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SFConfig.Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();

        Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
