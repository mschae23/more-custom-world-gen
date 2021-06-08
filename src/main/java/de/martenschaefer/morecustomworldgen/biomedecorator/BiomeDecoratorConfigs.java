package de.martenschaefer.morecustomworldgen.biomedecorator;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.FixedBiomeSource;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import de.martenschaefer.morecustomworldgen.biomedecorator.biome.MoreCustomWorldGenBiomes;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeDecoratorEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.SimpleReplaceBiomeEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.WeightedReplaceBiomeEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.replace.ArrayWeightedReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.replace.BorderingReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.replace.SimpleReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.replace.WeightedReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;
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

    public static BiomeSource getVanillaBiomeSourceX(long seed, Registry<Biome> biomeRegistry) {
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
                    1L,
                    new IncreaseEdgeCurvatureBiomeDecorator()
                ),
                new BiomeDecoratorEntry(
                    2001L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new IncreaseEdgeCurvatureBiomeDecorator()
                ),
                new BiomeDecoratorEntry(
                    50L,
                    new IncreaseEdgeCurvatureBiomeDecorator()
                ),
                new BiomeDecoratorEntry(
                    70L,
                    new IncreaseEdgeCurvatureBiomeDecorator()
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new BorderingReplaceBiomeDecorator(true,
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
                    new IncreaseEdgeCurvatureBiomeDecorator()
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new BorderingReplaceBiomeDecorator(
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
                    new BorderingReplaceBiomeDecorator(
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
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2003L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    4L,
                    new IncreaseEdgeCurvatureBiomeDecorator()
                ),
                new BiomeDecoratorEntry(
                    5L,
                    new BorderingReplaceBiomeDecorator(true,
                        Either.left(BiomeKeys.OCEAN),
                        Chance.simple(100),
                        BiomeKeys.MUSHROOM_FIELDS
                    )
                ),
                new BiomeDecoratorEntry(
                    4L,
                    new BorderingReplaceBiomeDecorator(true,
                        Either.left(BiomeKeys.OCEAN),
                        Chance.always(),
                        BiomeKeys.DEEP_OCEAN
                    )
                ),
                new BiomeDecoratorEntry(
                    200L,
                    new ArrayWeightedReplaceBiomeDecorator(
                        ImmutableList.of(
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.DRY),
                                ImmutableList.of(
                                    WeightEntry.of(3, BiomeKeys.DESERT),
                                    WeightEntry.of(2, BiomeKeys.SAVANNA),
                                    WeightEntry.one(BiomeKeys.PLAINS)
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.SPECIAL_DRY),
                                ImmutableList.of(
                                    WeightEntry.of(2, BiomeKeys.WOODED_BADLANDS_PLATEAU),
                                    WeightEntry.one(BiomeKeys.BADLANDS_PLATEAU)
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.TEMPERATE),
                                ImmutableList.of(
                                    WeightEntry.one(BiomeKeys.FOREST),
                                    WeightEntry.one(BiomeKeys.DARK_FOREST),
                                    WeightEntry.one(BiomeKeys.MOUNTAINS),
                                    WeightEntry.one(BiomeKeys.PLAINS),
                                    WeightEntry.one(BiomeKeys.BIRCH_FOREST),
                                    WeightEntry.one(BiomeKeys.SWAMP)
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.SPECIAL_TEMPERATE),
                                ImmutableList.of(
                                    WeightEntry.one(BiomeKeys.JUNGLE)
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.COOL),
                                ImmutableList.of(
                                    WeightEntry.one(BiomeKeys.FOREST),
                                    WeightEntry.one(BiomeKeys.MOUNTAINS),
                                    WeightEntry.one(BiomeKeys.TAIGA),
                                    WeightEntry.one(BiomeKeys.PLAINS)
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.SPECIAL_COOL),
                                ImmutableList.of(
                                    WeightEntry.one(BiomeKeys.GIANT_TREE_TAIGA)
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.right(ImmutableList.of(
                                    MoreCustomWorldGenBiomes.SNOWY,
                                    MoreCustomWorldGenBiomes.SPECIAL_SNOWY
                                )),
                                ImmutableList.of(
                                    WeightEntry.of(3, BiomeKeys.SNOWY_TUNDRA),
                                    WeightEntry.one(BiomeKeys.SNOWY_TAIGA)
                                )
                            )
                        )
                    )
                ),
                new BiomeDecoratorEntry(
                    1001L,
                    new SimpleReplaceBiomeDecorator( // TODO Replace with a south east sampling replace decorator
                        ImmutableList.of(
                            new SimpleReplaceBiomeEntry(
                                BiomeKeys.JUNGLE,
                                Chance.simple(10),
                                BiomeKeys.BAMBOO_JUNGLE
                            )
                        )
                    )
                ),
                new BiomeDecoratorEntry(
                    1000L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    1001L,
                    ScaleBiomeDecorator.normal()
                )
            ),
            biomeRegistry
        );
    }

    public static BiomeSource getVanillaBiomeSource(long seed, Registry<Biome> biomeRegistry) {
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
