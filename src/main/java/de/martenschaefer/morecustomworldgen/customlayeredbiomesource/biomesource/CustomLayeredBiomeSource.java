package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.SharedConstants;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenMod;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.CustomLayeredBiomeSourceConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.BiomeLayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla.VanillaLayers;
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
                this.config.getBiomeSizeConfig().getBiomeScale(),
                this.config.getOceanCategory()
            ),
            VanillaLayers.buildRiverLayer(seed,
                noiseLayer,
                this.config.getBiomeSizeConfig().getBiomeScale()
            ),
            VanillaLayers.buildOceanLayer(seed,
                this.config.getBiomeSizeConfig().getOceanClimateSize()
            ),
            this.config.getBiomeCategories(),
            this.config.getRivers(),
            this.config.getBiomeSizeConfig().getBiomeAndRiverScale(),
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
                CustomLayeredPresets.VANILLA_OVERWORLD,
                registry, Pair.of(registry, preset)));

        public static final Preset LARGE_RIVERS = new Preset(new Identifier(MoreCustomWorldGenMod.MODID, "large_rivers"), (preset, registry, seed) ->
            new CustomLayeredBiomeSource(seed,
                CustomLayeredPresets.LARGE_RIVERS_WORLD_TYPE,
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
