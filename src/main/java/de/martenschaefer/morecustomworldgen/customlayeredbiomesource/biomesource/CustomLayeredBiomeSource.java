package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.SharedConstants;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenMod;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
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
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.BiomeLayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla.VanillaLayers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLayeredBiomeSource extends BiomeSource {
    public static final MapCodec<CustomLayeredBiomeSource> CUSTOM_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.LONG.fieldOf("seed").stable().forGetter(biomeSource -> biomeSource.seed),
            CustomLayeredBiomeSourceConfig.CODEC.fieldOf("config").forGetter(biomeSource -> biomeSource.config),
            RegistryLookupCodec.of(Registry.BIOME_KEY).stable().forGetter(biomeSource -> biomeSource.biomeRegistry)
        ).apply(instance, CustomLayeredBiomeSource::new));

    public static final Codec<CustomLayeredBiomeSource> CODEC = Codec.mapEither(Instance.CODEC, CUSTOM_CODEC).xmap(
        either -> either.map(Instance::getBiomeSource, Function.identity()),
        biomeSource -> biomeSource.getInstance().<Either<Instance, CustomLayeredBiomeSource>>map(Either::left).orElseGet(() -> Either.right(biomeSource))).codec();

    private final static Logger LOGGER = LogManager.getLogger();

    private final long seed;
    private final CustomLayeredBiomeSourceConfig config;
    private final BiomeLayerSampler<RegistryKey<Biome>> sampler;
    private final Registry<Biome> biomeRegistry;
    private final Pair<Registry<Biome>, Preset> instance;

    public CustomLayeredBiomeSource(long seed, CustomLayeredBiomeSourceConfig config, Registry<Biome> biomeRegistry) {
        this(seed, config, biomeRegistry, null);
    }

    public CustomLayeredBiomeSource(long seed, CustomLayeredBiomeSourceConfig config, Registry<Biome> biomeRegistry, Pair<Registry<Biome>, Preset> instance) {
        super(config.getBiomes(biomeRegistry));
        this.seed = seed;
        this.biomeRegistry = biomeRegistry;
        this.config = config;

        BiomeLayerSampler<ClimateCategory> climateLayer = VanillaLayers.buildClimateLayer(seed,
            VanillaLayers.buildContinentLayer(
                seed,
                this.config.getContinentConfig()
            ),
            this.config.getClimateConfig()
        );

        BiomeLayerSampler<Integer> noiseLayer = VanillaLayers.buildNoiseLayer(seed,
            climateLayer);

        this.sampler = VanillaLayers.buildBiomeLayer(seed,
            VanillaLayers.buildBiomeLayoutLayer(seed,
                climateLayer,
                VanillaLayers.buildHillLayer(seed,
                    noiseLayer
                ),
                this.config.getBiomeCategories(),
                this.config.getOceanBiomes(),
                this.config.getBiomeLayout(),
                this.config.getBiomeSizeConfig().getBiomeSize(),
                this.config.getOceanCategory()
            ),
            VanillaLayers.buildRiverLayer(seed,
                noiseLayer,
                this.config.getBiomeSizeConfig().getBiomeSize()
            ),
            VanillaLayers.buildOceanLayer(seed,
                this.config.getBiomeSizeConfig().getOceanClimateSize()
            ),
            this.config.getBiomeCategories(),
            this.config.getRivers(),
            this.config.getBiomeSizeConfig().getRiverSize(),
            this.config.getOceanCategory(),
            this.config.getOceanBiomes()
        );
        this.instance = instance;
    }

    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Environment(EnvType.CLIENT)
    public BiomeSource withSeed(long seed) {
        return new CustomLayeredBiomeSource(seed, this.config, this.biomeRegistry);
    }

    private Optional<Instance> getInstance() {
        return Optional.ofNullable(this.instance).map(pair ->
            new Instance(pair.getSecond(), pair.getFirst(), this.seed));
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        RegistryKey<Biome> key = this.sampler.sample(biomeX, biomeZ);
        if (key == null) {
            throw new IllegalStateException("No biome emitted by layers");
        } else {
            Biome biome = this.biomeRegistry.get(key);
            if (biome == null) {
                if (SharedConstants.isDevelopment) {
                    throw Util.throwOrPause(new IllegalStateException("Unknown biome id: " + key.getValue()));
                } else {
                    LOGGER.warn("Unknown biome id: " + key.getValue());
                    return this.biomeRegistry.get(BiomeKeys.OCEAN);
                }
            } else {
                return biome;
            }
        }
    }

    public static class Preset {
        private static final Map<Identifier, Preset> BY_IDENTIFIER = Maps.newHashMap();
        public static final Preset VANILLA_OVERWORLD = new Preset(new Identifier(MoreCustomWorldGenMod.MODID, "vanilla_overworld"), (preset, registry, seed) ->
            new CustomLayeredBiomeSource(seed,
                new CustomLayeredBiomeSourceConfig(
                    ImmutableList.of( // Biome categories
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
                    ),
                    new ContinentConfig(10, true, 2),
                    new ClimateConfig(
                        1, 1, 4,
                        2,
                        100
                    ),
                    new BiomeLayoutConfig(
                        new BaseBiomesConfig(
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
                        ),
                        ImmutableList.of( // Large inner biomes
                            new InnerBiomeConfig(
                                BiomeKeys.JUNGLE,
                                BiomeKeys.BAMBOO_JUNGLE,
                                10
                            )
                        ),
                        new EdgeBiomesConfig(
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
                        ),
                        new HillBiomesConfig(
                            ImmutableList.of( // Ignored categories
                            ),
                            ImmutableList.of( // Special hill biomes
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
                            ),
                            ImmutableList.of( // Hill biomes
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
                            ),
                            ImmutableList.of(
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
                            )
                        ),
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
                            ImmutableList.of(
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
                            ),
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
                    ),
                    new BiomeSizeConfig(
                        4, 1, 6
                    ),
                    "ocean",
                    new RiverConfig(
                        true,
                        ImmutableList.of(
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
                        ),
                        BiomeKeys.RIVER
                    ),
                    new OceanBiomesConfig( // Ocean biomes
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
                    )
                ),
                registry, Pair.of(registry, preset)));
        private final Identifier id;
        private final Function3<Preset, Registry<Biome>, Long, CustomLayeredBiomeSource> biomeSourceFunction;

        public Preset(Identifier id, Function3<Preset, Registry<Biome>, Long, CustomLayeredBiomeSource> biomeSourceFunction) {
            this.id = id;
            this.biomeSourceFunction = biomeSourceFunction;
            BY_IDENTIFIER.put(id, this);
        }

        public CustomLayeredBiomeSource getBiomeSource(Registry<Biome> biomeRegistry, long seed) {
            return this.biomeSourceFunction.apply(this, biomeRegistry, seed);
        }
    }

    public static class Instance {
        public static final MapCodec<Instance> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                Identifier.CODEC.flatXmap(identifier ->
                    Optional.ofNullable(Preset.BY_IDENTIFIER.get(identifier)).map(DataResult::success).orElseGet(
                        () -> DataResult.error("Unknown preset: " + identifier)), preset ->
                    DataResult.success(preset.id)).fieldOf("preset").stable().forGetter(Instance::getPreset),
                RegistryLookupCodec.of(Registry.BIOME_KEY).stable().forGetter(Instance::getBiomeRegistry),
                Codec.LONG.fieldOf("seed").stable().forGetter(Instance::getSeed)
            ).apply(instance, instance.stable(Instance::new)));
        private final Preset preset;
        private final Registry<Biome> biomeRegistry;
        private final long seed;

        private Instance(Preset preset, Registry<Biome> biomeRegistry, long seed) {
            this.preset = preset;
            this.biomeRegistry = biomeRegistry;
            this.seed = seed;
        }

        public Preset getPreset() {
            return this.preset;
        }

        public Registry<Biome> getBiomeRegistry() {
            return this.biomeRegistry;
        }

        public long getSeed() {
            return this.seed;
        }

        public CustomLayeredBiomeSource getBiomeSource() {
            return this.preset.getBiomeSource(this.biomeRegistry, this.seed);
        }
    }
}
