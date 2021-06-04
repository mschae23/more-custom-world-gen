package de.martenschaefer.morecustomworldgen.biomedecorator;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.FixedBiomeSource;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;
import com.google.common.collect.ImmutableList;

public final class BiomeDecoratorConfigs {
    private BiomeDecoratorConfigs() {
    }

    public static final BiomeSource FIXED_VOID_BIOME_SOURCE = new FixedBiomeSource(BuiltinBiomes.THE_VOID);

    public static BiomeSource getVanillaBiomeSource(long seed, Registry<Biome> biomeRegistry) {
        return new DecoratedBiomeSource(seed, 1L, FIXED_VOID_BIOME_SOURCE,
            new WeightedBiomeDecorator(
                ImmutableList.of(
                    new ChanceEntry<>(
                        Chance.simple(10),
                        BiomeKeys.PLAINS
                    )
                ),
                BiomeKeys.OCEAN
            ), biomeRegistry
        );
    }
}
