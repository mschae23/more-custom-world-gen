package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource;

import java.util.List;
import net.minecraft.world.biome.BiomeKeys;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BaseBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeLayoutConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeSizeConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeWeightEntry;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.ClimateConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.ContinentConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.CustomLayeredBiomeSourceConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.EdgeBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.HillBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.HillBiomesConfig.ComplexHillBiomeEntry;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.HillBiomesConfig.HillBiomeEntry;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.HillBiomesConfig.SpecialHillBiomeEntry;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.InnerBiomeConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.OceanBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.RiverConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.ShoreBiomesConfig;
import com.google.common.collect.ImmutableList;

public final class CustomLayeredPresets {
    private CustomLayeredPresets() {
    }

    public static final List<BiomeCategory> VANILLA_CATEGORIES = ImmutableList.of( // Biome categories
        new BiomeCategory(BiomeKeys.BEACH, "beach"),
        new BiomeCategory(BiomeKeys.SNOWY_BEACH, "beach"),
        new BiomeCategory(BiomeKeys.DESERT, "desert"),
        new BiomeCategory(BiomeKeys.DESERT_HILLS, "desert"),
        new BiomeCategory(BiomeKeys.DESERT_LAKES, "desert"),
        new BiomeCategory(BiomeKeys.GRAVELLY_MOUNTAINS, "mountains"),
        new BiomeCategory(BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS, "mountains"),
        new BiomeCategory(BiomeKeys.MOUNTAIN_EDGE, "mountains"),
        new BiomeCategory(BiomeKeys.MOUNTAINS, "mountains"),
        new BiomeCategory(BiomeKeys.WOODED_MOUNTAINS, "mountains"),
        new BiomeCategory(BiomeKeys.BIRCH_FOREST, "forest"),
        new BiomeCategory(BiomeKeys.BIRCH_FOREST_HILLS, "forest"),
        new BiomeCategory(BiomeKeys.DARK_FOREST, "forest"),
        new BiomeCategory(BiomeKeys.DARK_FOREST_HILLS, "forest"),
        new BiomeCategory(BiomeKeys.FLOWER_FOREST, "forest"),
        new BiomeCategory(BiomeKeys.FOREST, "forest"),
        new BiomeCategory(BiomeKeys.TALL_BIRCH_FOREST, "forest"),
        new BiomeCategory(BiomeKeys.TALL_BIRCH_HILLS, "forest"),
        new BiomeCategory(BiomeKeys.ICE_SPIKES, "icy"),
        new BiomeCategory(BiomeKeys.SNOWY_MOUNTAINS, "icy"),
        new BiomeCategory(BiomeKeys.SNOWY_TUNDRA, "icy"),
        new BiomeCategory(BiomeKeys.BAMBOO_JUNGLE, "jungle"),
        new BiomeCategory(BiomeKeys.BAMBOO_JUNGLE_HILLS, "jungle"),
        new BiomeCategory(BiomeKeys.JUNGLE, "jungle"),
        new BiomeCategory(BiomeKeys.JUNGLE_EDGE, "jungle"),
        new BiomeCategory(BiomeKeys.JUNGLE_HILLS, "jungle"),
        new BiomeCategory(BiomeKeys.MODIFIED_JUNGLE, "jungle"),
        new BiomeCategory(BiomeKeys.MODIFIED_JUNGLE_EDGE, "jungle"),
        new BiomeCategory(BiomeKeys.BADLANDS, "badlands"),
        new BiomeCategory(BiomeKeys.ERODED_BADLANDS, "badlands"),
        new BiomeCategory(BiomeKeys.MODIFIED_BADLANDS_PLATEAU, "badlands"),
        new BiomeCategory(BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU, "badlands"),
        new BiomeCategory(BiomeKeys.BADLANDS_PLATEAU, "badlands_plateau"),
        new BiomeCategory(BiomeKeys.WOODED_BADLANDS_PLATEAU, "badlands_plateau"),
        new BiomeCategory(BiomeKeys.MUSHROOM_FIELDS, "mushroom_fields"),
        new BiomeCategory(BiomeKeys.MUSHROOM_FIELD_SHORE, "mushroom_fields"),
        new BiomeCategory(BiomeKeys.STONE_SHORE, "none"),
        new BiomeCategory(BiomeKeys.OCEAN, "ocean"),
        new BiomeCategory(BiomeKeys.WARM_OCEAN, "ocean"),
        new BiomeCategory(BiomeKeys.LUKEWARM_OCEAN, "ocean"),
        new BiomeCategory(BiomeKeys.COLD_OCEAN, "ocean"),
        new BiomeCategory(BiomeKeys.FROZEN_OCEAN, "ocean"),
        new BiomeCategory(BiomeKeys.DEEP_OCEAN, "ocean"),
        new BiomeCategory(BiomeKeys.DEEP_WARM_OCEAN, "ocean"),
        new BiomeCategory(BiomeKeys.DEEP_LUKEWARM_OCEAN, "ocean"),
        new BiomeCategory(BiomeKeys.DEEP_COLD_OCEAN, "ocean"),
        new BiomeCategory(BiomeKeys.DEEP_FROZEN_OCEAN, "ocean"),
        new BiomeCategory(BiomeKeys.PLAINS, "plains"),
        new BiomeCategory(BiomeKeys.SUNFLOWER_PLAINS, "plains"),
        new BiomeCategory(BiomeKeys.FROZEN_RIVER, "river"),
        new BiomeCategory(BiomeKeys.RIVER, "river"),
        new BiomeCategory(BiomeKeys.SAVANNA, "savanna"),
        new BiomeCategory(BiomeKeys.SAVANNA_PLATEAU, "savanna"),
        new BiomeCategory(BiomeKeys.SHATTERED_SAVANNA, "savanna"),
        new BiomeCategory(BiomeKeys.SHATTERED_SAVANNA_PLATEAU, "savanna"),
        new BiomeCategory(BiomeKeys.SWAMP, "swamp"),
        new BiomeCategory(BiomeKeys.SWAMP_HILLS, "swamp"),
        new BiomeCategory(BiomeKeys.GIANT_SPRUCE_TAIGA, "taiga"),
        new BiomeCategory(BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS, "taiga"),
        new BiomeCategory(BiomeKeys.GIANT_TREE_TAIGA, "taiga"),
        new BiomeCategory(BiomeKeys.GIANT_TREE_TAIGA_HILLS, "taiga"),
        new BiomeCategory(BiomeKeys.SNOWY_TAIGA, "taiga"),
        new BiomeCategory(BiomeKeys.SNOWY_TAIGA_HILLS, "taiga"),
        new BiomeCategory(BiomeKeys.SNOWY_TAIGA_MOUNTAINS, "taiga"),
        new BiomeCategory(BiomeKeys.TAIGA, "taiga"),
        new BiomeCategory(BiomeKeys.TAIGA_HILLS, "taiga"),
        new BiomeCategory(BiomeKeys.TAIGA_MOUNTAINS, "taiga")
    );

    public static final ContinentConfig VANILLA_CONTINENTS = new ContinentConfig(10, true, 2);

    public static final ClimateConfig VANILLA_CLIMATES = new ClimateConfig(
        1, 1, 4,
        2,
        100
    );

    public static final BaseBiomesConfig VANILLA_BASE_BIOMES = new BaseBiomesConfig(
        ImmutableList.of( // Dry biomes
            new BiomeWeightEntry(BiomeKeys.DESERT, 3),
            new BiomeWeightEntry(BiomeKeys.SAVANNA, 2),
            new BiomeWeightEntry(BiomeKeys.PLAINS, 1)
        ),
        ImmutableList.of( // Temperate biomes
            new BiomeWeightEntry(BiomeKeys.FOREST, 1),
            new BiomeWeightEntry(BiomeKeys.DARK_FOREST, 1),
            new BiomeWeightEntry(BiomeKeys.MOUNTAINS, 1),
            new BiomeWeightEntry(BiomeKeys.PLAINS, 1),
            new BiomeWeightEntry(BiomeKeys.BIRCH_FOREST, 1),
            new BiomeWeightEntry(BiomeKeys.SWAMP, 1)
        ),
        ImmutableList.of( // Cool biomes
            new BiomeWeightEntry(BiomeKeys.FOREST, 1),
            new BiomeWeightEntry(BiomeKeys.MOUNTAINS, 1),
            new BiomeWeightEntry(BiomeKeys.TAIGA, 1),
            new BiomeWeightEntry(BiomeKeys.PLAINS, 1)
        ),
        ImmutableList.of( // Snowy biomes
            new BiomeWeightEntry(BiomeKeys.SNOWY_TUNDRA, 3),
            new BiomeWeightEntry(BiomeKeys.SNOWY_TAIGA, 1)
        ),
        ImmutableList.of( // Special dry biomes
            new BiomeWeightEntry(BiomeKeys.WOODED_BADLANDS_PLATEAU, 2),
            new BiomeWeightEntry(BiomeKeys.BADLANDS_PLATEAU, 1)
        ),
        ImmutableList.of( // Special temperate biomes
            new BiomeWeightEntry(BiomeKeys.JUNGLE, 1)
        ),
        ImmutableList.of( // Special cool biomes
            new BiomeWeightEntry(BiomeKeys.GIANT_TREE_TAIGA, 1)
        ),
        ImmutableList.of( // Special snowy biomes
        ),
        ImmutableList.of( // Rare island biomes
            new BiomeWeightEntry(BiomeKeys.MUSHROOM_FIELDS, 1)
        ),
        BiomeKeys.PLAINS
    );

    public static final EdgeBiomesConfig VANILLA_EDGE_BIOMES = new EdgeBiomesConfig(
        ImmutableList.of( // Ignored categories
            "mountains"
        ),
        ImmutableList.of(
            new EdgeBiomesConfig.CategoryEdgeBiome(
                BiomeKeys.WOODED_BADLANDS_PLATEAU,
                BiomeKeys.BADLANDS
            ),
            new EdgeBiomesConfig.CategoryEdgeBiome(
                BiomeKeys.BADLANDS_PLATEAU,
                BiomeKeys.BADLANDS
            ),
            new EdgeBiomesConfig.CategoryEdgeBiome(
                BiomeKeys.GIANT_TREE_TAIGA,
                BiomeKeys.TAIGA
            )
        ),
        ImmutableList.of(
            new EdgeBiomesConfig.EdgeBiome(
                BiomeKeys.DESERT,
                ImmutableList.of(
                    BiomeKeys.SNOWY_TUNDRA
                ),
                BiomeKeys.WOODED_MOUNTAINS
            ),
            new EdgeBiomesConfig.EdgeBiome(
                BiomeKeys.SWAMP,
                ImmutableList.of(
                    BiomeKeys.DESERT,
                    BiomeKeys.SNOWY_TAIGA,
                    BiomeKeys.SNOWY_TUNDRA
                ),
                BiomeKeys.PLAINS
            ),
            new EdgeBiomesConfig.EdgeBiome(
                BiomeKeys.SWAMP,
                ImmutableList.of(
                    BiomeKeys.JUNGLE,
                    BiomeKeys.BAMBOO_JUNGLE
                ),
                BiomeKeys.JUNGLE_EDGE
            )
        )
    );

    public static final List<SpecialHillBiomeEntry> VANILLA_SPECIAL_HILL_BIOMES = ImmutableList.of(
        new SpecialHillBiomeEntry(BiomeKeys.PLAINS, BiomeKeys.SUNFLOWER_PLAINS),
        new SpecialHillBiomeEntry(BiomeKeys.DESERT, BiomeKeys.DESERT_LAKES),
        new SpecialHillBiomeEntry(BiomeKeys.MOUNTAINS, BiomeKeys.GRAVELLY_MOUNTAINS),
        new SpecialHillBiomeEntry(BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST),
        new SpecialHillBiomeEntry(BiomeKeys.TAIGA, BiomeKeys.TAIGA_MOUNTAINS),
        new SpecialHillBiomeEntry(BiomeKeys.SWAMP, BiomeKeys.SWAMP_HILLS),
        new SpecialHillBiomeEntry(BiomeKeys.SNOWY_TUNDRA, BiomeKeys.ICE_SPIKES),
        new SpecialHillBiomeEntry(BiomeKeys.JUNGLE, BiomeKeys.MODIFIED_JUNGLE),
        new SpecialHillBiomeEntry(BiomeKeys.JUNGLE_EDGE, BiomeKeys.MODIFIED_JUNGLE_EDGE),
        new SpecialHillBiomeEntry(BiomeKeys.BIRCH_FOREST, BiomeKeys.TALL_BIRCH_FOREST),
        new SpecialHillBiomeEntry(BiomeKeys.BIRCH_FOREST_HILLS, BiomeKeys.TALL_BIRCH_HILLS),
        new SpecialHillBiomeEntry(BiomeKeys.DARK_FOREST, BiomeKeys.DARK_FOREST_HILLS),
        new SpecialHillBiomeEntry(BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_TAIGA_MOUNTAINS),
        new SpecialHillBiomeEntry(BiomeKeys.GIANT_TREE_TAIGA, BiomeKeys.GIANT_SPRUCE_TAIGA),
        new SpecialHillBiomeEntry(BiomeKeys.GIANT_TREE_TAIGA_HILLS, BiomeKeys.GIANT_SPRUCE_TAIGA_HILLS),
        new SpecialHillBiomeEntry(BiomeKeys.WOODED_MOUNTAINS, BiomeKeys.MODIFIED_GRAVELLY_MOUNTAINS),
        new SpecialHillBiomeEntry(BiomeKeys.SAVANNA, BiomeKeys.SHATTERED_SAVANNA),
        new SpecialHillBiomeEntry(BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.SHATTERED_SAVANNA_PLATEAU),
        new SpecialHillBiomeEntry(BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS),
        new SpecialHillBiomeEntry(BiomeKeys.WOODED_BADLANDS_PLATEAU, BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU),
        new SpecialHillBiomeEntry(BiomeKeys.BADLANDS_PLATEAU, BiomeKeys.MODIFIED_BADLANDS_PLATEAU)
    );

    public static final List<HillBiomeEntry> VANILLA_NORMAL_HILL_BIOMES = ImmutableList.of(
        new HillBiomeEntry(BiomeKeys.DESERT, BiomeKeys.DESERT_HILLS),
        new HillBiomeEntry(BiomeKeys.FOREST, BiomeKeys.WOODED_HILLS),
        new HillBiomeEntry(BiomeKeys.BIRCH_FOREST, BiomeKeys.BIRCH_FOREST_HILLS),
        new HillBiomeEntry(BiomeKeys.DARK_FOREST, BiomeKeys.PLAINS),
        new HillBiomeEntry(BiomeKeys.TAIGA, BiomeKeys.TAIGA_HILLS),
        new HillBiomeEntry(BiomeKeys.GIANT_TREE_TAIGA, BiomeKeys.GIANT_TREE_TAIGA_HILLS),
        new HillBiomeEntry(BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_TAIGA_HILLS),
        new HillBiomeEntry(BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_MOUNTAINS),
        new HillBiomeEntry(BiomeKeys.JUNGLE, BiomeKeys.JUNGLE_HILLS),
        new HillBiomeEntry(BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.BAMBOO_JUNGLE_HILLS),
        new HillBiomeEntry(BiomeKeys.OCEAN, BiomeKeys.DEEP_OCEAN),
        new HillBiomeEntry(BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN),
        new HillBiomeEntry(BiomeKeys.COLD_OCEAN, BiomeKeys.DEEP_COLD_OCEAN),
        new HillBiomeEntry(BiomeKeys.FROZEN_RIVER, BiomeKeys.DEEP_FROZEN_OCEAN),
        new HillBiomeEntry(BiomeKeys.MOUNTAINS, BiomeKeys.WOODED_MOUNTAINS),
        new HillBiomeEntry(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU),
        new HillBiomeEntry("badlands_plateau", BiomeKeys.BADLANDS)
    );

    public static final List<ComplexHillBiomeEntry> VANILLA_COMPLEX_HILL_BIOMES = ImmutableList.of(
        new ComplexHillBiomeEntry(
            ImmutableList.of(
                BiomeKeys.PLAINS
            ),
            1,
            ImmutableList.of(
                BiomeKeys.FOREST,
                BiomeKeys.FOREST,
                BiomeKeys.WOODED_HILLS
            )
        ),
        new ComplexHillBiomeEntry(
            ImmutableList.of(
                BiomeKeys.DEEP_OCEAN,
                BiomeKeys.DEEP_LUKEWARM_OCEAN,
                BiomeKeys.DEEP_COLD_OCEAN,
                BiomeKeys.DEEP_FROZEN_OCEAN
            ),
            3,
            ImmutableList.of(
                BiomeKeys.PLAINS,
                BiomeKeys.FOREST
            )
        )
    );

    public static final HillBiomesConfig VANILLA_HILL_BIOMES = new HillBiomesConfig(
        ImmutableList.of( // Ignored categories
        ),
        VANILLA_SPECIAL_HILL_BIOMES,
        VANILLA_NORMAL_HILL_BIOMES,
        VANILLA_COMPLEX_HILL_BIOMES
    );

    public static final List<ShoreBiomesConfig.Override> VANILLA_SHORE_BIOME_OVERRIDES = ImmutableList.of(
        new ShoreBiomesConfig.Override(
            ImmutableList.of(
                BiomeKeys.MUSHROOM_FIELDS
            ),
            ImmutableList.of(
            ),
            ImmutableList.of(
                BiomeKeys.OCEAN,
                BiomeKeys.WARM_OCEAN,
                BiomeKeys.LUKEWARM_OCEAN,
                BiomeKeys.COLD_OCEAN,
                BiomeKeys.FROZEN_OCEAN
            ),
            BiomeKeys.MUSHROOM_FIELD_SHORE,
            false,
            true,
            false
        ),
        new ShoreBiomesConfig.Override(
            ImmutableList.of(
                BiomeKeys.BAMBOO_JUNGLE,
                BiomeKeys.BAMBOO_JUNGLE_HILLS,
                BiomeKeys.JUNGLE,
                BiomeKeys.JUNGLE_HILLS,
                BiomeKeys.JUNGLE_EDGE,
                BiomeKeys.MODIFIED_JUNGLE,
                BiomeKeys.MODIFIED_JUNGLE_EDGE
            ),
            ImmutableList.of(
            ),
            ImmutableList.of(
                BiomeKeys.BAMBOO_JUNGLE,
                BiomeKeys.BAMBOO_JUNGLE_HILLS,
                BiomeKeys.JUNGLE,
                BiomeKeys.JUNGLE_HILLS,
                BiomeKeys.JUNGLE_EDGE,
                BiomeKeys.MODIFIED_JUNGLE,
                BiomeKeys.MODIFIED_JUNGLE_EDGE,
                BiomeKeys.FOREST,
                BiomeKeys.TAIGA
            ),
            BiomeKeys.JUNGLE_EDGE,
            true,
            true,
            true
        ),
        new ShoreBiomesConfig.Override(
            ImmutableList.of(
                BiomeKeys.MOUNTAINS,
                BiomeKeys.WOODED_MOUNTAINS,
                BiomeKeys.MOUNTAIN_EDGE
            ),
            ImmutableList.of(
            ),
            ImmutableList.of(
            ),
            BiomeKeys.STONE_SHORE,
            false
        ),
        new ShoreBiomesConfig.Override(
            ImmutableList.of(
                BiomeKeys.SNOWY_BEACH,
                BiomeKeys.FROZEN_RIVER,
                BiomeKeys.SNOWY_TUNDRA,
                BiomeKeys.SNOWY_MOUNTAINS,
                BiomeKeys.ICE_SPIKES,
                BiomeKeys.SNOWY_TAIGA,
                BiomeKeys.SNOWY_TAIGA_HILLS,
                BiomeKeys.SNOWY_TAIGA_MOUNTAINS,
                BiomeKeys.FROZEN_OCEAN
            ),
            ImmutableList.of(
            ),
            ImmutableList.of(
            ),
            BiomeKeys.SNOWY_BEACH,
            false
        ),
        new ShoreBiomesConfig.Override(
            ImmutableList.of(
                BiomeKeys.BADLANDS,
                BiomeKeys.WOODED_BADLANDS_PLATEAU
            ),
            ImmutableList.of(
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
            ),
            ImmutableList.of(
                BiomeKeys.BADLANDS,
                BiomeKeys.WOODED_BADLANDS_PLATEAU,
                BiomeKeys.BADLANDS_PLATEAU,
                BiomeKeys.ERODED_BADLANDS,
                BiomeKeys.MODIFIED_WOODED_BADLANDS_PLATEAU,
                BiomeKeys.MODIFIED_BADLANDS_PLATEAU
            ),
            BiomeKeys.DESERT,
            true,
            false,
            false
        ),
        new ShoreBiomesConfig.Override(
            ImmutableList.of(
                BiomeKeys.RIVER
            ),
            ImmutableList.of(
            ),
            ImmutableList.of(
            ),
            BiomeKeys.RIVER,
            false,
            false,
            false
        ),
        new ShoreBiomesConfig.Override(
            ImmutableList.of(
                BiomeKeys.SWAMP
            ),
            ImmutableList.of(
            ),
            ImmutableList.of(
            ),
            BiomeKeys.SWAMP,
            false,
            false,
            false
        )
    );

    public static final BiomeLayoutConfig VANILLA_BIOME_LAYOUT = new BiomeLayoutConfig(
        VANILLA_BASE_BIOMES,
        ImmutableList.of( // Large inner biomes
            new InnerBiomeConfig(
                BiomeKeys.JUNGLE,
                BiomeKeys.BAMBOO_JUNGLE,
                10
            )
        ),
        VANILLA_EDGE_BIOMES,
        VANILLA_HILL_BIOMES,
        ImmutableList.of( // Spot inner biomes
            new InnerBiomeConfig(
                BiomeKeys.PLAINS,
                BiomeKeys.SUNFLOWER_PLAINS,
                57
            )
        ),
        new ShoreBiomesConfig(
            ImmutableList.of( // Ignored categories
            ),
            VANILLA_SHORE_BIOME_OVERRIDES,
            BiomeKeys.BEACH

        ),
        ImmutableList.of( // Shallow ocean biomes
            BiomeKeys.OCEAN,
            BiomeKeys.WARM_OCEAN,
            BiomeKeys.LUKEWARM_OCEAN,
            BiomeKeys.COLD_OCEAN,
            BiomeKeys.FROZEN_OCEAN
        ),
        BiomeKeys.FOREST,
        BiomeKeys.PLAINS
    );

    public static final BiomeSizeConfig VANILLA_BIOME_SIZE = new BiomeSizeConfig(
        4, 0, 6
    );

    @SuppressWarnings("unused")
    public static final BiomeSizeConfig LARGE_BIOME_SIZE = new BiomeSizeConfig(
        6, 0, 6
    );

    public static final List<RiverConfig.Override> VANILLA_RIVER_OVERRIDES = ImmutableList.of(
        new RiverConfig.Override(
            ImmutableList.of(
                BiomeKeys.SNOWY_TUNDRA
            ),
            BiomeKeys.FROZEN_RIVER
        ),
        new RiverConfig.Override(
            ImmutableList.of(
                BiomeKeys.MUSHROOM_FIELDS,
                BiomeKeys.MUSHROOM_FIELD_SHORE
            ),
            BiomeKeys.MUSHROOM_FIELD_SHORE
        )
    );

    public static final OceanBiomesConfig VANILLA_OCEAN_BIOMES = new OceanBiomesConfig( // Ocean biomes
        true,
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
    );

    public static final CustomLayeredBiomeSourceConfig VANILLA_OVERWORLD = new CustomLayeredBiomeSourceConfig(
        VANILLA_CATEGORIES,
        VANILLA_CONTINENTS,
        VANILLA_CLIMATES,
        VANILLA_BIOME_LAYOUT,
        VANILLA_BIOME_SIZE,
        "ocean",
        new RiverConfig(
            true,
            VANILLA_RIVER_OVERRIDES,
            BiomeKeys.RIVER
        ),
        VANILLA_OCEAN_BIOMES
    );

    public static final CustomLayeredBiomeSourceConfig LARGE_RIVERS_WORLD_TYPE = new CustomLayeredBiomeSourceConfig(
        VANILLA_CATEGORIES,
        VANILLA_CONTINENTS,
        VANILLA_CLIMATES,
        VANILLA_BIOME_LAYOUT,
        new BiomeSizeConfig(
            2, 2, 6
        ),
        "ocean",
        new RiverConfig(
            true,
            VANILLA_RIVER_OVERRIDES,
            BiomeKeys.RIVER
        ),
        VANILLA_OCEAN_BIOMES
    );

    public static final ContinentConfig CONTINENTS_CONTINENTS = new ContinentConfig(10, true, 200);

    public static final CustomLayeredBiomeSourceConfig CONTINENTS_OVERWORLD = new CustomLayeredBiomeSourceConfig(
        VANILLA_CATEGORIES,
        CONTINENTS_CONTINENTS,
        VANILLA_CLIMATES,
        VANILLA_BIOME_LAYOUT,
        VANILLA_BIOME_SIZE,
        "ocean",
        new RiverConfig(
            true,
            VANILLA_RIVER_OVERRIDES,
            BiomeKeys.RIVER
        ),
        VANILLA_OCEAN_BIOMES
    );
}
