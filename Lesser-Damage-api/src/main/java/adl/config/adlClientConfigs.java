package adl.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class adlClientConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> CITRINE_ORE_VEINS_PER_CHUNK;


    static {
        BUILDER.push("Configs for Tutorial Mod");

        CITRINE_ORE_VEINS_PER_CHUNK = BUILDER.comment("How many Citrine Ore Veins spawn per chunk!")
                .define("Veins Per Chunk", 7);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
