package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.List;
import java.util.Optional;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.biome.MoreCustomWorldGenBiomes;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeDecoratorEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.SimpleReplaceBiomeEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.WeightedReplaceBiomeEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.IncreaseEdgeCurvatureBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.ScaleBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.WeightedInitBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.ArrayBorderingReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.ArrayWeightedReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.BorderingReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.BorderingReplaceBiomeEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.SimpleReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace.WeightedReplaceBiomeDecorator;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;
import de.martenschaefer.morecustomworldgen.util.WeightEntry;

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

    public static final BiomeDecorator CONTINENT_INIT_DECORATOR = new WeightedInitBiomeDecorator(
        ImmutableList.of(
            new ChanceEntry<>(
                Chance.simple(10),
                BiomeKeys.PLAINS
            )
        ),
        BiomeKeys.OCEAN,
        ImmutableList.of(
            new WeightedInitBiomeDecorator.PositionBiomeOverride(
                Optional.of(0),
                Optional.empty(),
                Optional.of(0),
                BiomeKeys.PLAINS
            )
        )
    );

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
                    1L,
                    IncreaseEdgeCurvatureBiomeDecorator.INSTANCE
                ),
                new BiomeDecoratorEntry(
                    2001L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    2L,
                    IncreaseEdgeCurvatureBiomeDecorator.INSTANCE
                ),
                new BiomeDecoratorEntry(
                    50L,
                    IncreaseEdgeCurvatureBiomeDecorator.INSTANCE
                ),
                new BiomeDecoratorEntry(
                    70L,
                    IncreaseEdgeCurvatureBiomeDecorator.INSTANCE
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new BorderingReplaceBiomeDecorator(new BorderingReplaceBiomeEntry(
                        Either.left(BiomeKeys.OCEAN),
                        Either.left(BiomeKeys.OCEAN),
                        Either.right(ImmutableList.of()),
                        Chance.simple(2),
                        BiomeKeys.PLAINS
                    ))
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
                    IncreaseEdgeCurvatureBiomeDecorator.INSTANCE
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new BorderingReplaceBiomeDecorator(new BorderingReplaceBiomeEntry(
                        Either.left(MoreCustomWorldGenBiomes.DRY),
                        Either.right(ImmutableList.of(
                            MoreCustomWorldGenBiomes.COOL,
                            MoreCustomWorldGenBiomes.SNOWY
                        )),
                        Chance.always(),
                        MoreCustomWorldGenBiomes.TEMPERATE
                    ))
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new BorderingReplaceBiomeDecorator(new BorderingReplaceBiomeEntry(
                        Either.left(MoreCustomWorldGenBiomes.SNOWY),
                        Either.right(ImmutableList.of(
                            MoreCustomWorldGenBiomes.DRY,
                            MoreCustomWorldGenBiomes.TEMPERATE
                        )),
                        Chance.always(),
                        MoreCustomWorldGenBiomes.COOL
                    ))
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
                    IncreaseEdgeCurvatureBiomeDecorator.INSTANCE
                ),
                new BiomeDecoratorEntry(
                    5L,
                    new BorderingReplaceBiomeDecorator(new BorderingReplaceBiomeEntry(
                        Either.left(BiomeKeys.OCEAN),
                        Either.left(BiomeKeys.OCEAN),
                        Either.right(ImmutableList.of()),
                        Chance.simple(100),
                        BiomeKeys.MUSHROOM_FIELDS
                    ))
                ),
                new BiomeDecoratorEntry(
                    4L,
                    new BorderingReplaceBiomeDecorator(new BorderingReplaceBiomeEntry(
                        Either.left(BiomeKeys.OCEAN),
                        Either.left(BiomeKeys.OCEAN),
                        Either.right(ImmutableList.of()),
                        Chance.always(),
                        BiomeKeys.DEEP_OCEAN
                    ))
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
                    new SimpleReplaceBiomeDecorator(
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
                ),
                new BiomeDecoratorEntry(
                    1000L,
                    new ArrayBorderingReplaceBiomeDecorator(
                        ImmutableList.of(
                            BiomeKeys.GRAVELLY_MOUNTAINS,
                            BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS,
                            BiomeKeys.MOUNTAIN_EDGE,
                            BiomeKeys.MOUNTAINS,
                            BiomeKeys.WOODED_MOUNTAINS
                        ),
                        ImmutableList.of(
                            new BorderingReplaceBiomeEntry(
                                true, true,
                                Either.right(ImmutableList.of(
                                    BiomeKeys.BADLANDS_PLATEAU,
                                    BiomeKeys.WOODED_BADLANDS_PLATEAU
                                )),
                                Chance.always(),
                                BiomeKeys.BADLANDS
                            ),
                            new BorderingReplaceBiomeEntry(
                                true, true,
                                Either.left(BiomeKeys.GIANT_TREE_TAIGA),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.GIANT_SPRUCE_TAIGA,
                                    BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS,
                                    BiomeKeys.GIANT_TREE_TAIGA,
                                    BiomeKeys.GIANT_TREE_TAIGA_HILLS,
                                    BiomeKeys.SNOWY_TAIGA,
                                    BiomeKeys.SNOWY_TAIGA_HILLS,
                                    BiomeKeys.SNOWY_TAIGA_MOUNTAINS,
                                    BiomeKeys.TAIGA,
                                    BiomeKeys.TAIGA_HILLS,
                                    BiomeKeys.TAIGA_MOUNTAINS
                                )),
                                Chance.always(),
                                BiomeKeys.TAIGA
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.left(BiomeKeys.DESERT),
                                Either.left(BiomeKeys.SNOWY_TUNDRA),
                                Chance.always(),
                                BiomeKeys.WOODED_MOUNTAINS
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.left(BiomeKeys.SWAMP),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.DESERT,
                                    BiomeKeys.SNOWY_TAIGA,
                                    BiomeKeys.SNOWY_TUNDRA
                                )),
                                Chance.always(),
                                BiomeKeys.PLAINS
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.left(BiomeKeys.SWAMP),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.JUNGLE,
                                    BiomeKeys.BAMBOO_JUNGLE
                                )),
                                Chance.always(),
                                BiomeKeys.JUNGLE_EDGE
                            )
                        )
                    )
                ), // Hills decorator here
                new BiomeDecoratorEntry(
                    1001L,
                    new SimpleReplaceBiomeDecorator(
                        ImmutableList.of(
                            new SimpleReplaceBiomeEntry(
                                BiomeKeys.PLAINS,
                                Chance.simple(57),
                                BiomeKeys.SUNFLOWER_PLAINS
                            )
                        )
                    )
                ),
                new BiomeDecoratorEntry(
                    1000L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    3L,
                    IncreaseEdgeCurvatureBiomeDecorator.INSTANCE
                ),
                new BiomeDecoratorEntry(
                    1001L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    1000L,
                    new ArrayBorderingReplaceBiomeDecorator(
                        ImmutableList.of(),
                        ImmutableList.of(
                            new BorderingReplaceBiomeEntry(
                                Either.left(BiomeKeys.MUSHROOM_FIELDS),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.OCEAN,
                                    BiomeKeys.WARM_OCEAN,
                                    BiomeKeys.LUKEWARM_OCEAN,
                                    BiomeKeys.COLD_OCEAN,
                                    BiomeKeys.FROZEN_OCEAN
                                )),
                                Chance.always(),
                                BiomeKeys.MUSHROOM_FIELD_SHORE
                            ),
                            new BorderingReplaceBiomeEntry(
                                true, true,
                                Either.right(ImmutableList.of(
                                    BiomeKeys.BAMBOO_JUNGLE,
                                    BiomeKeys.BAMBOO_JUNGLE_HILLS,
                                    BiomeKeys.JUNGLE,
                                    BiomeKeys.JUNGLE_HILLS,
                                    BiomeKeys.JUNGLE_EDGE,
                                    BiomeKeys.MODIFIED_JUNGLE,
                                    BiomeKeys.MODIFIED_JUNGLE_EDGE
                                )),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.BAMBOO_JUNGLE,
                                    BiomeKeys.BAMBOO_JUNGLE_HILLS,
                                    BiomeKeys.JUNGLE,
                                    BiomeKeys.JUNGLE_HILLS,
                                    BiomeKeys.JUNGLE_EDGE,
                                    BiomeKeys.MODIFIED_JUNGLE,
                                    BiomeKeys.MODIFIED_JUNGLE_EDGE,
                                    BiomeKeys.FOREST,
                                    BiomeKeys.TAIGA
                                )),
                                Chance.always(),
                                BiomeKeys.JUNGLE_EDGE
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.right(ImmutableList.of(
                                    BiomeKeys.MOUNTAINS,
                                    BiomeKeys.WOODED_MOUNTAINS,
                                    BiomeKeys.MOUNTAIN_EDGE
                                )),
                                Either.right(OCEAN_BIOMES),
                                Chance.always(),
                                BiomeKeys.STONE_SHORE
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.right(ImmutableList.of(
                                    BiomeKeys.SNOWY_BEACH,
                                    BiomeKeys.FROZEN_RIVER,
                                    BiomeKeys.SNOWY_TUNDRA,
                                    BiomeKeys.SNOWY_MOUNTAINS,
                                    BiomeKeys.ICE_SPIKES,
                                    BiomeKeys.SNOWY_TAIGA,
                                    BiomeKeys.SNOWY_TAIGA_HILLS,
                                    BiomeKeys.SNOWY_TAIGA_MOUNTAINS,
                                    BiomeKeys.FROZEN_OCEAN
                                )),
                                Either.right(OCEAN_BIOMES),
                                Chance.always(),
                                BiomeKeys.SNOWY_BEACH
                            ),
                            new BorderingReplaceBiomeEntry(
                                true, true,
                                Either.right(ImmutableList.of(
                                    BiomeKeys.BADLANDS,
                                    BiomeKeys.WOODED_BADLANDS_PLATEAU
                                )),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.OCEAN,
                                    BiomeKeys.WARM_OCEAN,
                                    BiomeKeys.LUKEWARM_OCEAN,
                                    BiomeKeys.COLD_OCEAN,
                                    BiomeKeys.FROZEN_OCEAN,
                                    BiomeKeys.DEEP_OCEAN,
                                    BiomeKeys.DEEP_WARM_OCEAN,
                                    BiomeKeys.DEEP_LUKEWARM_OCEAN,
                                    BiomeKeys.DEEP_COLD_OCEAN,
                                    BiomeKeys.DEEP_FROZEN_OCEAN
                                )),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.BADLANDS,
                                    BiomeKeys.WOODED_BADLANDS_PLATEAU,
                                    BiomeKeys.BADLANDS_PLATEAU,
                                    BiomeKeys.ERODED_BADLANDS,
                                    BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU,
                                    BiomeKeys.MODIFIED_BADLANDS_PLATEAU
                                )),
                                Chance.always(),
                                BiomeKeys.DESERT
                            ),
                            new BorderingReplaceBiomeEntry(
                                true, true, false,
                                Either.right(ImmutableList.of(
                                    BiomeKeys.OCEAN,
                                    BiomeKeys.WARM_OCEAN,
                                    BiomeKeys.LUKEWARM_OCEAN,
                                    BiomeKeys.COLD_OCEAN,
                                    BiomeKeys.FROZEN_OCEAN,
                                    BiomeKeys.DEEP_OCEAN,
                                    BiomeKeys.DEEP_WARM_OCEAN,
                                    BiomeKeys.DEEP_LUKEWARM_OCEAN,
                                    BiomeKeys.DEEP_COLD_OCEAN,
                                    BiomeKeys.DEEP_FROZEN_OCEAN,
                                    BiomeKeys.RIVER,
                                    BiomeKeys.SWAMP
                                )),
                                Either.right(OCEAN_BIOMES),
                                Chance.always(),
                                BiomeKeys.BEACH
                            )
                        )
                    )
                ),
                new BiomeDecoratorEntry(
                    1002L,
                    ScaleBiomeDecorator.normal()
                ),
                new BiomeDecoratorEntry(
                    1003L,
                    ScaleBiomeDecorator.normal()
                ) // Smooth decorator here
            ),
            biomeRegistry
        );
    }

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
