package de.martenschaefer.morecustomworldgen.test;

import java.util.List;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.SeedMixer;
import net.minecraft.world.gen.SimpleRandom;

public class TestBiomeSource extends BiomeSource {
    public static final Codec<TestBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.LONG.fieldOf("seed").forGetter(TestBiomeSource::getSeed),
        RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(TestBiomeSource::getBiomeRegistry)
    ).apply(instance, instance.stable(TestBiomeSource::new)));

    private final long seed;
    private final Registry<Biome> biomeRegistry;
    private final DoublePerlinNoiseSampler continentsNoiseSampler;
    private final DoublePerlinNoiseSampler hillinessNoiseSampler;
    private final DoublePerlinNoiseSampler riverNoiseSampler;

    public TestBiomeSource(long seed, Registry<Biome> biomeRegistry) {
        super(ImmutableList.of(biomeRegistry.getOrThrow(BiomeKeys.OCEAN), biomeRegistry.getOrThrow(BiomeKeys.PLAINS)));

        this.seed = seed;
        this.biomeRegistry = biomeRegistry;

        this.continentsNoiseSampler = DoublePerlinNoiseSampler.create(new SimpleRandom(SeedMixer.mixSeed(seed, 1000L)), -8, 1f, 1f, 1f);
        this.hillinessNoiseSampler = DoublePerlinNoiseSampler.create(new SimpleRandom(SeedMixer.mixSeed(seed, 2000L)), -5, 1f, 2f, 1f, 0.5f, 1f);
        this.riverNoiseSampler = DoublePerlinNoiseSampler.create(new SimpleRandom(SeedMixer.mixSeed(seed, 3000L)), -3, 1f, 0.4f, 1f);
    }

    public long getSeed() {
        return this.seed;
    }

    public Registry<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new TestBiomeSource(seed, this.biomeRegistry);
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        double continentsNoise = this.continentsNoiseSampler.sample(biomeX, 0, biomeZ);
        double hillinessNoise = this.hillinessNoiseSampler.sample(biomeX / 8.0, 0, biomeZ / 8.0);
        double riverNoise = this.riverNoiseSampler.sample(biomeX / 8.0, 0, biomeZ / 8.0);

        if (continentsNoise < -0.1f)
            return this.biomeRegistry.getOrThrow(BiomeKeys.OCEAN);

        if (continentsNoise < 0.05f)
            return this.biomeRegistry.getOrThrow(BiomeKeys.BEACH);

        if (riverNoise > -0.01f && riverNoise < 0.01f)
            return this.biomeRegistry.getOrThrow(BiomeKeys.RIVER);

        if (hillinessNoise < 0f)
            return this.biomeRegistry.getOrThrow(BiomeKeys.PLAINS);

        if (hillinessNoise < 0.3f)
            return this.biomeRegistry.getOrThrow(BiomeKeys.DESERT);

        if (hillinessNoise < 0.6f)
            return this.biomeRegistry.getOrThrow(BiomeKeys.FOREST);

        if (hillinessNoise < 0.8f)
            return this.biomeRegistry.getOrThrow(BiomeKeys.MEADOW);

        return this.biomeRegistry.getOrThrow(BiomeKeys.MOUNTAINS);
    }

    @Override
    public double[] method_37612(int x, int z) {
        double continentsNoise = this.continentsNoiseSampler.sample(x, 0, z);
        double hillinessNoise = this.hillinessNoiseSampler.sample(x / 8.0, 0, z / 8.0);
        double riverNoise = this.riverNoiseSampler.sample(x / 8.0, 0, z / 8.0);
        TestSplines.NoisePoint point = TestSplines.createNoisePoint((float) continentsNoise, (float) hillinessNoise, (float) riverNoise);

        return new double[] { TestSplines.getOffset(point), TestSplines.getFactor(point) };
    }

    @Override
    public void addDebugInfo(List<String> info, BlockPos pos) {
        super.addDebugInfo(info, pos);

        double continentsNoise = this.continentsNoiseSampler.sample(pos.getX(), 0, pos.getZ());
        double hillinessNoise = this.hillinessNoiseSampler.sample(pos.getX() / 8.0, 0, pos.getZ() / 8.0);
        double riverNoise = this.riverNoiseSampler.sample(pos.getX() / 8.0, 0, pos.getZ() / 8.0);
        TestSplines.NoisePoint point = TestSplines.createNoisePoint((float) continentsNoise, (float) hillinessNoise, (float) riverNoise);

        info.add("Terrain O: " + String.format("%1$.4f", TestSplines.getOffset(point)) + " F: " + String.format("%1$.4f", TestSplines.getFactor(point)));
        info.add("Test C: " + String.format("%1$.4f", continentsNoise)
            + " H: " + String.format("%1$.4f", hillinessNoise)
            + " R: " + String.format("%1$.4f", riverNoise));
    }
}
