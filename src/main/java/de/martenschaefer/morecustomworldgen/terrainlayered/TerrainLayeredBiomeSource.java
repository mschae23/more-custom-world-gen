package de.martenschaefer.morecustomworldgen.terrainlayered;

import java.util.List;
import java.util.Optional;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.SharedConstants;
import net.minecraft.class_6466;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import de.martenschaefer.morecustomworldgen.terrainlayered.impl.EmptyTerrainBiomeSampler;

public class TerrainLayeredBiomeSource extends BiomeSource {
    public static final Codec<TerrainLayeredBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.LONG.fieldOf("salt").forGetter(TerrainLayeredBiomeSource::getSeed),
        MultiNoiseBiomeSource.NoiseParameters.CODEC.fieldOf("continentalness_noise").forGetter(TerrainLayeredBiomeSource::getContinentalnessNoiseParameters),
        MultiNoiseBiomeSource.NoiseParameters.CODEC.fieldOf("erosion_noise").forGetter(TerrainLayeredBiomeSource::getErosionNoiseParameters),
        MultiNoiseBiomeSource.NoiseParameters.CODEC.fieldOf("weirdness_noise").forGetter(TerrainLayeredBiomeSource::getWeirdnessNoiseParameters),
        TerrainLayerEntry.CODEC.listOf().fieldOf("layers").forGetter(TerrainLayeredBiomeSource::getLayers),
        RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(TerrainLayeredBiomeSource::getBiomeRegistry)
    ).apply(instance, instance.stable(TerrainLayeredBiomeSource::new)));
    private static final class_6466 TERRAIN_SHAPER = new class_6466();
    private static final Logger LOGGER = LogManager.getLogger();

    private final long seed;
    private final MultiNoiseBiomeSource.NoiseParameters continentalnessNoiseParameters;
    private final MultiNoiseBiomeSource.NoiseParameters erosionNoiseParameters;
    private final MultiNoiseBiomeSource.NoiseParameters weirdnessNoiseParameters;
    private final DoublePerlinNoiseSampler continentalnessNoise;
    private final DoublePerlinNoiseSampler erosionNoise;
    private final DoublePerlinNoiseSampler weirdnessNoise;
    private final List<TerrainLayerEntry> layers;
    private final TerrainBiomeSampler sampler;
    private final Registry<Biome> biomeRegistry;

    protected TerrainLayeredBiomeSource(long seed, MultiNoiseBiomeSource.NoiseParameters continentalnessNoiseParameters, MultiNoiseBiomeSource.NoiseParameters erosionNoiseParameters, MultiNoiseBiomeSource.NoiseParameters weirdnessNoiseParameters, List<TerrainLayerEntry> layers, Registry<Biome> biomeRegistry) {
        super(layers.stream().flatMap(entry -> entry.layer().getBiomes(biomeRegistry)));

        this.seed = seed;
        this.continentalnessNoiseParameters = continentalnessNoiseParameters;
        this.erosionNoiseParameters = erosionNoiseParameters;
        this.weirdnessNoiseParameters = weirdnessNoiseParameters;
        this.continentalnessNoise = DoublePerlinNoiseSampler.create(new ChunkRandom(seed + 2L), continentalnessNoiseParameters.getFirstOctave(), continentalnessNoiseParameters.getAmplitudes());
        this.erosionNoise = DoublePerlinNoiseSampler.create(new ChunkRandom(seed + 3L), erosionNoiseParameters.getFirstOctave(), erosionNoiseParameters.getAmplitudes());
        this.weirdnessNoise = DoublePerlinNoiseSampler.create(new ChunkRandom(seed + 4L), weirdnessNoiseParameters.getFirstOctave(), weirdnessNoiseParameters.getAmplitudes());
        this.layers = layers;
        this.biomeRegistry = biomeRegistry;

        this.sampler = createSampler(this.seed, this.layers);
    }

    public long getSeed() {
        return this.seed;
    }

    public MultiNoiseBiomeSource.NoiseParameters getContinentalnessNoiseParameters() {
        return this.continentalnessNoiseParameters;
    }

    public MultiNoiseBiomeSource.NoiseParameters getErosionNoiseParameters() {
        return this.erosionNoiseParameters;
    }

    public MultiNoiseBiomeSource.NoiseParameters getWeirdnessNoiseParameters() {
        return this.weirdnessNoiseParameters;
    }

    public DoublePerlinNoiseSampler getContinentalnessNoise() {
        return this.continentalnessNoise;
    }

    public DoublePerlinNoiseSampler getErosionNoise() {
        return this.erosionNoise;
    }

    public DoublePerlinNoiseSampler getWeirdnessNoise() {
        return this.weirdnessNoise;
    }

    public List<TerrainLayerEntry> getLayers() {
        return this.layers;
    }

    public Registry<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long l) {
        return new TerrainLayeredBiomeSource(seed, this.continentalnessNoiseParameters, this.erosionNoiseParameters, this.weirdnessNoiseParameters, this.layers, this.biomeRegistry);
    }

    @Override
    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        float continentalness = (float) this.sampleContinentalnessNoise(x, 0, z);
        float erosion = (float) this.sampleWeirdnessNoise(x, 0, z);
        float weirdness = (float) this.sampleErosionNoise(x, 0, z);

        Optional<RegistryKey<Biome>> optionalBiome = this.sampler.sample(x, y, z, new TerrainSamplerContext(continentalness, erosion, weirdness));

        if (optionalBiome.isEmpty()) {
            LOGGER.warn("No biome emitted by layers (continentalness: " + continentalness + ", erosion: " + erosion + ", weirdness: " + weirdness + ")");
            return this.biomeRegistry.get(BiomeKeys.THE_VOID);
        } else {
            RegistryKey<Biome> key = optionalBiome.get();

            Biome biome = this.biomeRegistry.get(key);

            if (biome == null) {
                if (SharedConstants.isDevelopment) {
                    throw Util.throwOrPause(new IllegalStateException("Unknown biome id: " + key.getValue()));
                } else {
                    LOGGER.warn("Unknown biome id: " + key.getValue());
                    return this.biomeRegistry.get(BiomeKeys.THE_VOID);
                }
            } else {
                return biome;
            }
        }
    }

    @Override
    public double[] method_37612(int x, int z) {
        float continentalness = (float) this.sampleContinentalnessNoise(x, 0, z);
        float erosion = (float) this.sampleWeirdnessNoise(x, 0, z);
        float weirdness = (float) this.sampleErosionNoise(x, 0, z);
        class_6466.TerrainNoisePoint lv = TERRAIN_SHAPER.createTerrainNoisePoint(continentalness, erosion, weirdness);

        return new double[] { TERRAIN_SHAPER.getOffset(lv), TERRAIN_SHAPER.getFactor(lv) };
    }

    public double sampleContinentalnessNoise(double x, double y, double z) {
        if (SharedConstants.field_34061) {
            return SharedConstants.method_37481((int) x * 4, (int) z * 4) ? -1.0D : MathHelper.fractionalPart(x / 2048.0D) * 2.0D - 1.0D;
        } else {
            return this.continentalnessNoise.sample(x, y, z);
        }
    }

    public double sampleErosionNoise(double x, double y, double z) {
        if (SharedConstants.field_34061) {
            return SharedConstants.method_37481((int) x * 4, (int) z * 4) ? -1.0D : MathHelper.fractionalPart(z / 256.0D) * 2.0D - 1.0D;
        } else {
            return this.erosionNoise.sample(x, y, z);
        }
    }

    public double sampleWeirdnessNoise(double x, double y, double z) {
        return this.weirdnessNoise.sample(x, y, z);
    }

    public static TerrainBiomeSampler createSampler(long seed, List<TerrainLayerEntry> layers) {
        TerrainBiomeSampler parent = EmptyTerrainBiomeSampler.INSTANCE;

        for (TerrainLayerEntry entry : layers) {
            parent = entry.layer().createSampler(seed, entry.salt(), parent);
        }

        return parent;
    }
}
