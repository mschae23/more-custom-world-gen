package de.martenschaefer.morecustomworldgen.biomedecorator;

import net.minecraft.util.registry.Registry;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenMod;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenRegistries;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.FromBiomeSourceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.IncreaseEdgeCurvatureBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.NopeBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.ScaleBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.WeightedInitBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.ArrayBorderingReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.ArrayWeightedReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.BorderingReplaceBiomeDecorator;
import com.mojang.serialization.Codec;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.SimpleReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.WeightedReplaceBiomeDecorator;

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
        register("array_bordering_replace", ArrayBorderingReplaceBiomeDecorator.CODEC);
        register("weighted_replace", WeightedReplaceBiomeDecorator.CODEC);
        register("array_weighted_replace", ArrayWeightedReplaceBiomeDecorator.CODEC);
        register("simple_replace", SimpleReplaceBiomeDecorator.CODEC);
    }
}
