package de.martenschaefer.morecustomworldgen.biomedecorator;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.FixedBiomeSource;
import com.mojang.datafixers.util.Either;
import de.martenschaefer.morecustomworldgen.biomedecorator.biome.MoreCustomWorldGenBiomes;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeDecoratorEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.ScaleType;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.SimpleReplaceBiomeEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.replace.BorderingReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.replace.SimpleReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.replace.WeightedReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;
import com.google.common.collect.ImmutableList;
import de.martenschaefer.morecustomworldgen.util.WeightEntry;

public final class BiomeDecoratorConfigs {
    private BiomeDecoratorConfigs() {
    }

    @SuppressWarnings("unused")
    public static final BiomeSource FIXED_VOID_BIOME_SOURCE = new FixedBiomeSource(BuiltinBiomes.THE_VOID);

    public static final BiomeDecorator CONTINENT_INIT_DECORATOR = new WeightedInitBiomeDecorator(
        ImmutableList.of(
            new ChanceEntry<>(
                Chance.simple(10),
                BiomeKeys.PLAINS
            )
        ),
        BiomeKeys.OCEAN
    );

    public static final BiomeDecorator SCALE = new ScaleBiomeDecorator(ScaleType.NORMAL);

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
                    SCALE
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
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new BorderingReplaceBiomeDecorator(
                        Either.left(BiomeKeys.OCEAN),
                        Chance.simple(2),
                        BiomeKeys.PLAINS
                    )
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new WeightedReplaceBiomeDecorator(
                        ImmutableList.of(BiomeKeys.OCEAN),
                        ImmutableList.of(
                            new WeightEntry<>(1, MoreCustomWorldGenBiomes.COOL),
                            new WeightEntry<>(1, MoreCustomWorldGenBiomes.SNOWY),
                            new WeightEntry<>(4, MoreCustomWorldGenBiomes.DRY)
                        )
                    )
                ),
                new BiomeDecoratorEntry(
                    3L,
                    INCREASE_EDGE_CURVATURE
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new BorderingReplaceBiomeDecorator(false,
                        Either.left(MoreCustomWorldGenBiomes.DRY),
                        Either.right(ImmutableList.of(
                            MoreCustomWorldGenBiomes.COOL,
                            MoreCustomWorldGenBiomes.SNOWY
                        )),
                        Chance.always(),
                        MoreCustomWorldGenBiomes.TEMPERATE
                    )
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new BorderingReplaceBiomeDecorator(false,
                        Either.left(MoreCustomWorldGenBiomes.SNOWY),
                        Either.right(ImmutableList.of(
                            MoreCustomWorldGenBiomes.DRY,
                            MoreCustomWorldGenBiomes.TEMPERATE
                        )),
                        Chance.always(),
                        MoreCustomWorldGenBiomes.COOL
                    )
                ),
                new BiomeDecoratorEntry(
                    3L,
                    new SimpleReplaceBiomeDecorator(
                        ImmutableList.of(
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.DRY,
                                Chance.simple(13),
                                MoreCustomWorldGenBiomes.SPECIAL_DRY
                            ),
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.TEMPERATE,
                                Chance.simple(13),
                                MoreCustomWorldGenBiomes.SPECIAL_TEMPERATE
                            ),
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.COOL,
                                Chance.simple(13),
                                MoreCustomWorldGenBiomes.SPECIAL_COOL
                            ),
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.SNOWY,
                                Chance.simple(13),
                                MoreCustomWorldGenBiomes.SPECIAL_SNOWY
                            )
                        )
                    )
                ),
                new BiomeDecoratorEntry(
                    2002L,
                    SCALE
                ),
                new BiomeDecoratorEntry(
                    2003L,
                    SCALE
                ),
                new BiomeDecoratorEntry(
                    4L,
                    INCREASE_EDGE_CURVATURE
                )
            ),
            biomeRegistry
        );
    }
}
