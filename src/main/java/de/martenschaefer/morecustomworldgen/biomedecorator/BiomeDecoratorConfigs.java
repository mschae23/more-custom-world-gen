package de.martenschaefer.morecustomworldgen.biomedecorator;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.FixedBiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.ScaleType;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;
import com.google.common.collect.ImmutableList;

public final class BiomeDecoratorConfigs {
    private BiomeDecoratorConfigs() {
    }

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
        return new DecoratedBiomeSource(seed, 70L,
            new DecoratedBiomeSource(seed, 50L,
                new DecoratedBiomeSource(seed, 2L,
                    new DecoratedBiomeSource(seed, 2001L,
                        new DecoratedBiomeSource(seed, 1L,
                            new DecoratedBiomeSource(seed, 2000L,
                                new DecoratedBiomeSource(seed, 1L, FIXED_VOID_BIOME_SOURCE, CONTINENT_INIT_DECORATOR, biomeRegistry),
                                new ScaleBiomeDecorator(ScaleType.FUZZY), biomeRegistry
                            ),
                            INCREASE_EDGE_CURVATURE, biomeRegistry
                        ),
                        new ScaleBiomeDecorator(ScaleType.NORMAL), biomeRegistry
                    ),
                    INCREASE_EDGE_CURVATURE, biomeRegistry
                ),
                INCREASE_EDGE_CURVATURE, biomeRegistry
            ),
            INCREASE_EDGE_CURVATURE, biomeRegistry
        );
    }
}
