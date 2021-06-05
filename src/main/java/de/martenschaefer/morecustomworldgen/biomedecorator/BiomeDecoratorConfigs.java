package de.martenschaefer.morecustomworldgen.biomedecorator;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.FixedBiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeDecoratorEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.ScaleType;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;
import com.google.common.collect.ImmutableList;

public final class BiomeDecoratorConfigs {
    private BiomeDecoratorConfigs() {
    }

    @SuppressWarnings("unused")
    public static final BiomeSource FIXED_VOID_BIOME_SOURCE = new FixedBiomeSource(BuiltinBiomes.THE_VOID);

    public static final BiomeDecorator CONTINENT_INIT_DECORATOR = new WeightedBiomeDecorator(
        ImmutableList.of(
            new ChanceEntry<>(
                Chance.simple(10),
                BiomeKeys.PLAINS
            )
        ),
        BiomeKeys.OCEAN
    );

    public static final BiomeDecorator INCREASE_EDGE_CURVATURE = new IncreaseEdgeCurvatureBiomeDecorator();

    public static BiomeSource getVanillaBiomeSource(long seed, Registry<Biome> biomeRegistry) {
        return new ArrayDecoratedBiomeSource(seed,
            ImmutableList.of(
                new BiomeDecoratorEntry(
                    1L,
                    CONTINENT_INIT_DECORATOR
                ),
                new BiomeDecoratorEntry(
                    2000L,
                    new ScaleBiomeDecorator(ScaleType.FUZZY)
                ),
                new BiomeDecoratorEntry(
                    1L,
                    INCREASE_EDGE_CURVATURE
                ),
                new BiomeDecoratorEntry(
                    2001L,
                    new ScaleBiomeDecorator(ScaleType.NORMAL)
                ),
                new BiomeDecoratorEntry(
                    2L,
                    INCREASE_EDGE_CURVATURE
                ),
                new BiomeDecoratorEntry(
                    50L,
                    INCREASE_EDGE_CURVATURE
                ),
                new BiomeDecoratorEntry(
                    70L,
                    INCREASE_EDGE_CURVATURE
                )
            ),
            biomeRegistry
        );
    }
}
