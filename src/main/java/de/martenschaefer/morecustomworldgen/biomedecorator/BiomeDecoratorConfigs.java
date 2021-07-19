package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.List;
import java.util.Optional;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeContextPredicate;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeDecoratorEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomePredicate;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.ScaleBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.WeightedInitBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.BorderingReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.BorderingReplaceBiomeEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;

public final class BiomeDecoratorConfigs {
    private BiomeDecoratorConfigs() {
    }

    public static final List<RegistryKey<Biome>> OCEAN_BIOMES = ImmutableList.of(
        BiomeKeys.OCEAN,
        BiomeKeys.WARM_OCEAN,
        BiomeKeys.LUKEWARM_OCEAN,
        BiomeKeys.COLD_OCEAN,
        BiomeKeys.FROZEN_OCEAN
    );

    public static final ParentedBiomeDecorator CONTINENT_INIT_DECORATOR = new WeightedInitBiomeDecorator(
        ImmutableList.of(
            new ChanceEntry<>(
                Chance.simple(10),
                new BiomeContext(
                    BiomeKeys.PLAINS,
                    0, 0, 1, 0, 0, 0.06, 580
                )
            )
        ),
        new BiomeContext(
            BiomeKeys.OCEAN,
            0, 0, -1, 0, 0, -0.105, 505
        ),
        ImmutableList.of(
            new WeightedInitBiomeDecorator.PositionBiomeOverride(
                Optional.of(0),
                Optional.empty(),
                Optional.of(0),
                new BiomeContext(
                    BiomeKeys.PLAINS,
                    0, 0, 1, 0.5, 0, 0.06, 540
                )
            )
        )
    );

    public static BiomeSource getCustomBiomeSource(long seed, Registry<Biome> biomeRegistry) {
        return new ArrayDecoratedBiomeSource(seed,
            ImmutableList.of(
                new BiomeDecoratorEntry(
                    1L,
                    CONTINENT_INIT_DECORATOR
                ),
                new BiomeDecoratorEntry(
                    2000L,
                    ScaleBiomeDecorator.fuzzy()
                ),
                new BiomeDecoratorEntry(
                    2001L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    3000L,
                    new BorderingReplaceBiomeDecorator(new BorderingReplaceBiomeEntry(
                        new BiomeContextPredicate(new BiomePredicate(BiomeKeys.PLAINS), ImmutableList.of(), Chance.simple(7)),
                        Chance.always(),
                        new BiomeContext(BiomeKeys.MOUNTAINS, 0.3, 580)
                    ))
                ),
                new BiomeDecoratorEntry(
                    2002L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2003L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2004L,
                    ScaleBiomeDecorator.normal()
                )
            ),
            biomeRegistry
        );
    }

    public static BiomeSource getPlainsContinentsBiomeSource(long seed, Registry<Biome> biomeRegistry) {
        return new ArrayDecoratedBiomeSource(seed,
            ImmutableList.of(
                new BiomeDecoratorEntry(
                    1L,
                    CONTINENT_INIT_DECORATOR
                ),
                new BiomeDecoratorEntry(
                    2000L,
                    ScaleBiomeDecorator.fuzzy()
                ),
                new BiomeDecoratorEntry(
                    2001L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2002L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2003L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2004L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2005L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2006L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2007L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2008L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2009L,
                    ScaleBiomeDecorator.normal()
                )
            ),
            biomeRegistry
        );
    }
}
