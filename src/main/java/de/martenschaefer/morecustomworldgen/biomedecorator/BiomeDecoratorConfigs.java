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
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
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

    public static final ParentedBiomeDecorator CONTINENT_INIT_DECORATOR = new WeightedInitBiomeDecorator(
        ImmutableList.of(
            new ChanceEntry<>(
                Chance.simple(10),
                new BiomeContext(
                    BiomeKeys.PLAINS,
                    0, 0, 1, 0, 0
                )
            )
        ),
        new BiomeContext(
            BiomeKeys.OCEAN,
            0, 0, -0.335, 0, 0
        ),
        ImmutableList.of(
            new WeightedInitBiomeDecorator.PositionBiomeOverride(
                Optional.of(0),
                Optional.empty(),
                Optional.of(0),
                new BiomeContext(
                    BiomeKeys.PLAINS,
                    0, 0, 1, 0.5, 0
                )
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
                        new BiomeContext(
                            BiomeKeys.PLAINS,
                            0, 0, 0.3, 0.7, 0
                        )
                    ))
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new WeightedReplaceBiomeDecorator(
                        ImmutableList.of(BiomeKeys.OCEAN),
                        ImmutableList.of(
                            new WeightEntry<>(1, new BiomeContext(
                                MoreCustomWorldGenBiomes.COOL,
                                -0.4, 0, 0.5, 0, 0
                            )),
                            new WeightEntry<>(1, new BiomeContext(
                                MoreCustomWorldGenBiomes.SNOWY,
                                -1, 0.3, 0.5, 0, 0
                            )),
                            new WeightEntry<>(4, new BiomeContext(
                                MoreCustomWorldGenBiomes.DRY,
                                1, -1, 0.5, 0, 0
                            ))
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
                        new BiomeContext(
                            MoreCustomWorldGenBiomes.TEMPERATE,
                            0.5, 0, 0.5, 0, 0
                        )
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
                        new BiomeContext(
                            MoreCustomWorldGenBiomes.COOL,
                            -0.4, 0, 0.5, 0.4, 0
                        )
                    ))
                ),
                new BiomeDecoratorEntry(
                    3L,
                    new SimpleReplaceBiomeDecorator(
                        ImmutableList.of(
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.DRY,
                                Chance.simple(13),
                                new BiomeContext(
                                    MoreCustomWorldGenBiomes.SPECIAL_DRY,
                                    1, -1, 0.5, -0.4, 0
                                )
                            ),
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.TEMPERATE,
                                Chance.simple(13),
                                new BiomeContext(
                                    MoreCustomWorldGenBiomes.SPECIAL_TEMPERATE,
                                    0.5, 0, 0.5, -0.4, 0
                                )
                            ),
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.COOL,
                                Chance.simple(13),
                                new BiomeContext(
                                    MoreCustomWorldGenBiomes.SPECIAL_COOL,
                                    -0.4, 0, 0.5, 0, 0
                                )
                            ),
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.SNOWY,
                                Chance.simple(13),
                                new BiomeContext(
                                    MoreCustomWorldGenBiomes.SPECIAL_SNOWY,
                                    -1, 0.3, 0.5, -0.4, 0
                                )
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
                        new BiomeContext(
                            BiomeKeys.MUSHROOM_FIELDS,
                            0, 0.4, -1, -0.35, 0.1
                        )
                    ))
                ),
                new BiomeDecoratorEntry(
                    4L,
                    new BorderingReplaceBiomeDecorator(new BorderingReplaceBiomeEntry(
                        Either.left(BiomeKeys.OCEAN),
                        Either.left(BiomeKeys.OCEAN),
                        Either.right(ImmutableList.of()),
                        Chance.always(),
                        new BiomeContext(
                            BiomeKeys.MUSHROOM_FIELDS,
                            -0.3, 1, -0.75, 0, 0.2
                        )
                    ))
                ),
                new BiomeDecoratorEntry(
                    200L,
                    new ArrayWeightedReplaceBiomeDecorator(
                        ImmutableList.of(
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.DRY),
                                ImmutableList.of(
                                    WeightEntry.of(3, new BiomeContext(
                                        BiomeKeys.DESERT,
                                        1, -0.5, 0.3, -0.1, 0.3
                                    )),
                                    WeightEntry.of(2, new BiomeContext(
                                        BiomeKeys.SAVANNA,
                                        0.8, -0.3, 0.2, -0.35, 0.2
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.PLAINS,
                                        0.6, 0, 0.3, 0.2, 0.25
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.SPECIAL_DRY),
                                ImmutableList.of(
                                    WeightEntry.of(2, new BiomeContext(
                                        BiomeKeys.WOODED_BADLANDS_PLATEAU,
                                        1, 0.5, 0.6, -0.1, 0.5
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.BADLANDS_PLATEAU,
                                        1, 0, 0.6, -0.1, 0.5
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.TEMPERATE),
                                ImmutableList.of(
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.FOREST,
                                        0, 0.4, 0.3, -0.4, 0.3
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.DARK_FOREST,
                                        0, 0.5, 0.3, -0.3, 0.3
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.MOUNTAINS,
                                        -0.5, 0.1, 0.7, -0.7, 0.5
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.PLAINS,
                                        0, 0.2, 0.3, 0.2, 0.2
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.BIRCH_FOREST,
                                        0, 0.3, 0.3, -0.3, 0.3
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.SWAMP,
                                        0, 0.9, 0, 1, 0.2
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.SPECIAL_TEMPERATE),
                                ImmutableList.of(
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.JUNGLE,
                                        0.2, 0.6, 0.5, -0.6, 0.4
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.COOL),
                                ImmutableList.of(
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.FOREST,
                                        0, 0.4, 0.3, -0.2, 0.3
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.MOUNTAINS,
                                        -0.5, 0.1, 0.8, -0.8, 0.5
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.TAIGA,
                                        0, 0.4, 0.4, 0.1, 0.275
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.PLAINS,
                                        0, 0.2, 0.3, 0.3, 0.1
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.SPECIAL_COOL),
                                ImmutableList.of(
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.FOREST,
                                        0, 0.4, 0.4, 0, 0.3
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.right(ImmutableList.of(
                                    MoreCustomWorldGenBiomes.SNOWY,
                                    MoreCustomWorldGenBiomes.SPECIAL_SNOWY
                                )),
                                ImmutableList.of(
                                    WeightEntry.of(3, new BiomeContext(
                                        BiomeKeys.SNOWY_TUNDRA,
                                        -1, 0.3, 0.4, 0, 0.2
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.SNOWY_TAIGA,
                                        -0.9, 0.3, 0.5, 0.1, 0.2
                                    ))
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
                                new BiomeContext(
                                    BiomeKeys.JUNGLE,
                                    0.2, 0.65, 0.5, -0.65, 0.4
                                )
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
                                new BiomeContext(
                                    BiomeKeys.BADLANDS,
                                    1, 0, 0.3, -0.1, 0.5
                                )
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
                                new BiomeContext(
                                    BiomeKeys.TAIGA,
                                    0, 0.4, 0.2, 0, 0.2
                                )
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.left(BiomeKeys.DESERT),
                                Either.left(BiomeKeys.SNOWY_TUNDRA),
                                Chance.always(),
                                new BiomeContext(
                                    BiomeKeys.WOODED_MOUNTAINS,
                                    -0.5, 0.1, 0.6, -0.65, 0.5
                                )
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.left(BiomeKeys.SWAMP),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.DESERT,
                                    BiomeKeys.SNOWY_TAIGA,
                                    BiomeKeys.SNOWY_TUNDRA
                                )),
                                Chance.always(),
                                new BiomeContext(
                                    BiomeKeys.PLAINS,
                                    0, 0.2, 0.2, 0.35, 0.15
                                )
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.left(BiomeKeys.SWAMP),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.JUNGLE,
                                    BiomeKeys.BAMBOO_JUNGLE
                                )),
                                Chance.always(),
                                new BiomeContext(
                                    BiomeKeys.JUNGLE_EDGE,
                                    0.2, 0.6, 0.3, -0.3, 0.4
                                )
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
                                new BiomeContext(
                                    BiomeKeys.SUNFLOWER_PLAINS,
                                    0, 0.2, 0.25, 0.35, 0.2
                                )
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
                                new BiomeContext(
                                    BiomeKeys.MUSHROOM_FIELD_SHORE,
                                    0, 0.4, -0.9, -0.45, 0.05
                                )
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
                                new BiomeContext(
                                    BiomeKeys.JUNGLE_EDGE,
                                    0.2, 0.6, 0.3, -0.2, 0.35
                                )
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.right(ImmutableList.of(
                                    BiomeKeys.MOUNTAINS,
                                    BiomeKeys.WOODED_MOUNTAINS,
                                    BiomeKeys.MOUNTAIN_EDGE
                                )),
                                Either.right(OCEAN_BIOMES),
                                Chance.always(),
                                new BiomeContext(
                                    BiomeKeys.STONE_SHORE,
                                    -0.1, 0.2, 0, -0.5, 0.3
                                )
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
                                new BiomeContext(
                                    BiomeKeys.SNOWY_BEACH,
                                    -0.4, 0.3, -0.15, 0.4, 0.05
                                )
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
                                new BiomeContext(
                                    BiomeKeys.DESERT,
                                    1, 0, 0.2, -0.1, 0.5
                                )
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
                                new BiomeContext(
                                    BiomeKeys.BEACH,
                                    -0.4, 0.3, -0.15, 0.4, 0.05
                                )
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
                        new BiomeContext(
                            BiomeKeys.PLAINS,
                            0, 0, 0.3, 0.7, 0
                        )
                    ))
                ),
                new BiomeDecoratorEntry(
                    2L,
                    new WeightedReplaceBiomeDecorator(
                        ImmutableList.of(BiomeKeys.OCEAN),
                        ImmutableList.of(
                            new WeightEntry<>(1, new BiomeContext(
                                MoreCustomWorldGenBiomes.COOL,
                                -0.4, 0, 0.5, 0, 0
                            )),
                            new WeightEntry<>(1, new BiomeContext(
                                MoreCustomWorldGenBiomes.SNOWY,
                                -1, 0.3, 0.5, 0, 0
                            )),
                            new WeightEntry<>(4, new BiomeContext(
                                MoreCustomWorldGenBiomes.DRY,
                                1, -1, 0.5, 0, 0
                            ))
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
                        new BiomeContext(
                            MoreCustomWorldGenBiomes.TEMPERATE,
                            0.5, 0, 0.5, 0, 0
                        )
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
                        new BiomeContext(
                            MoreCustomWorldGenBiomes.COOL,
                            -0.4, 0, 0.5, 0.4, 0
                        )
                    ))
                ),
                new BiomeDecoratorEntry(
                    3L,
                    new SimpleReplaceBiomeDecorator(
                        ImmutableList.of(
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.DRY,
                                Chance.simple(13),
                                new BiomeContext(
                                    MoreCustomWorldGenBiomes.SPECIAL_DRY,
                                    1, -1, 0.5, -0.4, 0
                                )
                            ),
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.TEMPERATE,
                                Chance.simple(13),
                                new BiomeContext(
                                    MoreCustomWorldGenBiomes.SPECIAL_TEMPERATE,
                                    0.5, 0, 0.5, -0.4, 0
                                )
                            ),
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.COOL,
                                Chance.simple(13),
                                new BiomeContext(
                                    MoreCustomWorldGenBiomes.SPECIAL_COOL,
                                    -0.4, 0, 0.5, 0, 0
                                )
                            ),
                            new SimpleReplaceBiomeEntry(
                                MoreCustomWorldGenBiomes.SNOWY,
                                Chance.simple(13),
                                new BiomeContext(
                                    MoreCustomWorldGenBiomes.SPECIAL_SNOWY,
                                    -1, 0.3, 0.5, -0.4, 0
                                )
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
                        new BiomeContext(
                            BiomeKeys.MUSHROOM_FIELDS,
                            0, 0.4, -1, -0.35, 0.1
                        )
                    ))
                ),
                new BiomeDecoratorEntry(
                    4L,
                    new BorderingReplaceBiomeDecorator(new BorderingReplaceBiomeEntry(
                        Either.left(BiomeKeys.OCEAN),
                        Either.left(BiomeKeys.OCEAN),
                        Either.right(ImmutableList.of()),
                        Chance.always(),
                        new BiomeContext(
                            BiomeKeys.MUSHROOM_FIELDS,
                            -0.3, 1, -0.75, 0, 0.2
                        )
                    ))
                ),
                new BiomeDecoratorEntry(
                    200L,
                    new ArrayWeightedReplaceBiomeDecorator(
                        ImmutableList.of(
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.DRY),
                                ImmutableList.of(
                                    WeightEntry.of(3, new BiomeContext(
                                        BiomeKeys.DESERT,
                                        1, -0.5, 0.3, -0.1, 0.3
                                    )),
                                    WeightEntry.of(2, new BiomeContext(
                                        BiomeKeys.SAVANNA,
                                        0.8, -0.3, 0.2, -0.35, 0.2
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.PLAINS,
                                        0.6, 0, 0.3, 0.2, 0.25
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.SPECIAL_DRY),
                                ImmutableList.of(
                                    WeightEntry.of(2, new BiomeContext(
                                        BiomeKeys.WOODED_BADLANDS_PLATEAU,
                                        1, 0.5, 0.6, -0.1, 0.5
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.BADLANDS_PLATEAU,
                                        1, 0, 0.6, -0.1, 0.5
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.TEMPERATE),
                                ImmutableList.of(
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.FOREST,
                                        0, 0.4, 0.3, -0.4, 0.3
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.DARK_FOREST,
                                        0, 0.5, 0.3, -0.3, 0.3
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.MOUNTAINS,
                                        -0.5, 0.1, 0.7, -0.7, 0.5
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.PLAINS,
                                        0, 0.2, 0.3, 0.2, 0.2
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.BIRCH_FOREST,
                                        0, 0.3, 0.3, -0.3, 0.3
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.SWAMP,
                                        0, 0.9, 0, 1, 0.2
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.SPECIAL_TEMPERATE),
                                ImmutableList.of(
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.JUNGLE,
                                        0.2, 0.6, 0.5, -0.6, 0.4
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.COOL),
                                ImmutableList.of(
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.FOREST,
                                        0, 0.4, 0.3, -0.2, 0.3
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.LOFTY_PEAKS,
                                        -0.5, 0.1, 0.8, -0.9, 0.6
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.TAIGA,
                                        0, 0.4, 0.4, 0.1, 0.275
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.PLAINS,
                                        0, 0.2, 0.3, 0.3, 0.1
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.left(MoreCustomWorldGenBiomes.SPECIAL_COOL),
                                ImmutableList.of(
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.FOREST,
                                        0, 0.4, 0.4, 0, 0.3
                                    ))
                                )
                            ),
                            new WeightedReplaceBiomeEntry(
                                Either.right(ImmutableList.of(
                                    MoreCustomWorldGenBiomes.SNOWY,
                                    MoreCustomWorldGenBiomes.SPECIAL_SNOWY
                                )),
                                ImmutableList.of(
                                    WeightEntry.of(3, new BiomeContext(
                                        BiomeKeys.SNOWY_TUNDRA,
                                        -1, 0.3, 0.4, 0, 0.2
                                    )),
                                    WeightEntry.one(new BiomeContext(
                                        BiomeKeys.SNOWY_TAIGA,
                                        -0.9, 0.3, 0.5, 0.1, 0.2
                                    ))
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
                                new BiomeContext(
                                    BiomeKeys.JUNGLE,
                                    0.2, 0.65, 0.5, -0.65, 0.4
                                )
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
                                new BiomeContext(
                                    BiomeKeys.BADLANDS,
                                    1, 0, 0.3, -0.1, 0.5
                                )
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
                                new BiomeContext(
                                    BiomeKeys.TAIGA,
                                    0, 0.4, 0.2, 0, 0.2
                                )
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.left(BiomeKeys.DESERT),
                                Either.left(BiomeKeys.SNOWY_TUNDRA),
                                Chance.always(),
                                new BiomeContext(
                                    BiomeKeys.WOODED_MOUNTAINS,
                                    -0.5, 0.1, 0.6, -0.65, 0.5
                                )
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.left(BiomeKeys.SWAMP),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.DESERT,
                                    BiomeKeys.SNOWY_TAIGA,
                                    BiomeKeys.SNOWY_TUNDRA
                                )),
                                Chance.always(),
                                new BiomeContext(
                                    BiomeKeys.PLAINS,
                                    0, 0.2, 0.2, 0.35, 0.15
                                )
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.left(BiomeKeys.SWAMP),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.JUNGLE,
                                    BiomeKeys.BAMBOO_JUNGLE
                                )),
                                Chance.always(),
                                new BiomeContext(
                                    BiomeKeys.JUNGLE_EDGE,
                                    0.2, 0.6, 0.3, -0.3, 0.4
                                )
                            ),
                            new BorderingReplaceBiomeEntry(
                                true, true,
                                Either.left(BiomeKeys.LOFTY_PEAKS),
                                Either.right(ImmutableList.of(
                                    BiomeKeys.LOFTY_PEAKS
                                )),
                                Chance.always(),
                                new BiomeContext(
                                    BiomeKeys.SNOWY_SLOPES,
                                    -0.5, 0.1, 0.8, -0.9, 0.5
                                )
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
                                new BiomeContext(
                                    BiomeKeys.SUNFLOWER_PLAINS,
                                    0, 0.2, 0.25, 0.35, 0.2
                                )
                            ),
                            new SimpleReplaceBiomeEntry(
                                BiomeKeys.SNOWY_SLOPES,
                                Chance.simple(17),
                                new BiomeContext(
                                    BiomeKeys.GROVE,
                                    -0.5, 0.1, 0.8, -0.9, 0.4
                                )
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
                                new BiomeContext(
                                    BiomeKeys.MUSHROOM_FIELD_SHORE,
                                    0, 0.4, -0.9, -0.45, 0.05
                                )
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
                                new BiomeContext(
                                    BiomeKeys.JUNGLE_EDGE,
                                    0.2, 0.6, 0.3, -0.2, 0.35
                                )
                            ),
                            new BorderingReplaceBiomeEntry(
                                Either.right(ImmutableList.of(
                                    BiomeKeys.MOUNTAINS,
                                    BiomeKeys.WOODED_MOUNTAINS,
                                    BiomeKeys.MOUNTAIN_EDGE
                                )),
                                Either.right(OCEAN_BIOMES),
                                Chance.always(),
                                new BiomeContext(
                                    BiomeKeys.STONE_SHORE,
                                    -0.1, 0.2, 0, -0.5, 0.3
                                )
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
                                new BiomeContext(
                                    BiomeKeys.SNOWY_BEACH,
                                    -0.4, 0.3, -0.15, 0.4, 0.05
                                )
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
                                new BiomeContext(
                                    BiomeKeys.DESERT,
                                    1, 0, 0.2, -0.1, 0.5
                                )
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
                                new BiomeContext(
                                    BiomeKeys.BEACH,
                                    -0.4, 0.3, -0.15, 0.4, 0.05
                                )
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
