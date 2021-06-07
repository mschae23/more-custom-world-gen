package de.martenschaefer.morecustomworldgen.biomedecorator;

import net.minecraft.util.registry.Registry;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenMod;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenRegistries;
import de.martenschaefer.morecustomworldgen.biomedecorator.replace.BorderingReplaceBiomeDecorator;
import com.mojang.serialization.Codec;
import de.martenschaefer.morecustomworldgen.biomedecorator.replace.SimpleReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.replace.WeightedReplaceBiomeDecorator;

public final class BiomeDecorators {
    private BiomeDecorators() {
    }

    private static void register(String name, Codec<? extends BiomeDecorator> codec) {
        Registry.register(MoreCustomWorldGenRegistries.BIOME_DECORATOR, MoreCustomWorldGenMod.id(name), codec);
    }

    public static void register() {
        register("nope", NopeBiomeDecorator.CODEC);
        register("from_biome_source_init", FromBiomeSourceBiomeDecorator.CODEC);
        register("weighted_init", WeightedInitBiomeDecorator.CODEC);
        register("scale", ScaleBiomeDecorator.CODEC);
        register("increase_edge_curvature", IncreaseEdgeCurvatureBiomeDecorator.CODEC);
        register("bordering_replace", BorderingReplaceBiomeDecorator.CODEC);
        register("weighted_replace", WeightedReplaceBiomeDecorator.CODEC);
        register("simple_replace", SimpleReplaceBiomeDecorator.CODEC);
    }
}
