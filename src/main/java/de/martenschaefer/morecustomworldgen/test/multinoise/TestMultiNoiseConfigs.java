package de.martenschaefer.morecustomworldgen.test.multinoise;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.biome.source.util.MultiNoiseUtil.NoiseHypercube;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import static de.martenschaefer.morecustomworldgen.test.multinoise.TestBiomeParameters.DEFAULT_PARAMETER;

public final class TestMultiNoiseConfigs {
    private TestMultiNoiseConfigs() {
    }

    public static MultiNoiseBiomeSource createTestBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        ImmutableList<Pair<NoiseHypercube, Supplier<Biome>>> parameters = createTestBiomeEntries(biomeRegistry, TestBiomeParameters::writeTestBiomeParameters);

        MultiNoiseBiomeSource.NoiseParameters temperatureParameters = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.5D, 0.0D, 0.0D, 0.0D, 0.0D);
        MultiNoiseBiomeSource.NoiseParameters humidityParameters = new MultiNoiseBiomeSource.NoiseParameters(-7, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        MultiNoiseBiomeSource.NoiseParameters continentalnessParameters = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.0D, 2.0D, 2.0D, 2.0D, 1.0D, 1.0D, 1.0D, 1.0D);
        MultiNoiseBiomeSource.NoiseParameters erosionParameters = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.2D, 0.0D, 1.0D, 0.0D, 0.0D);
        MultiNoiseBiomeSource.NoiseParameters weirdnessParameters = new MultiNoiseBiomeSource.NoiseParameters(-7, 1.0D, 2.0D, 1.0D, 0.0D, 0.0D, 0.0D);

        return new MultiNoiseBiomeSource(seed, new MultiNoiseUtil.Entries<>(parameters),
            temperatureParameters, humidityParameters, continentalnessParameters, erosionParameters, weirdnessParameters,
            -16, 48, false, Optional.empty());
    }

    public static MultiNoiseBiomeSource createNetherBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        ImmutableList<Pair<NoiseHypercube, Supplier<Biome>>> parameters = createTestBiomeEntries(biomeRegistry, NetherBiomeParameters::writeNetherBiomeParameters);

        MultiNoiseBiomeSource.NoiseParameters temperatureParameters = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.5D, 0.0D, 0.0D, 0.0D, 0.0D);
        MultiNoiseBiomeSource.NoiseParameters humidityParameters = new MultiNoiseBiomeSource.NoiseParameters(-7, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        MultiNoiseBiomeSource.NoiseParameters continentalnessParameters = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.0D, 2.0D, 2.0D, 2.0D, 1.0D, 1.0D, 1.0D, 1.0D);
        MultiNoiseBiomeSource.NoiseParameters erosionParameters = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.2D, 0.0D, 1.0D, 0.0D, 0.0D);
        MultiNoiseBiomeSource.NoiseParameters weirdnessParameters = new MultiNoiseBiomeSource.NoiseParameters(-7, 1.0D, 2.0D, 1.0D, 0.0D, 0.0D, 0.0D);

        return new MultiNoiseBiomeSource(seed, new MultiNoiseUtil.Entries<>(parameters),
            temperatureParameters, humidityParameters, continentalnessParameters, erosionParameters, weirdnessParameters,
            -16, 48, false, Optional.empty());
    }

    public static MultiNoiseBiomeSource createTheEndBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        ImmutableList<Pair<NoiseHypercube, Supplier<Biome>>> parameters = createTestBiomeEntries(biomeRegistry, builder ->
            TestBiomeParameters.writeBiomeParameters(builder, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0f, BiomeKeys.THE_END));

        MultiNoiseBiomeSource.NoiseParameters temperatureParameters = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.5D, 0.0D, 0.0D, 0.0D, 0.0D);
        MultiNoiseBiomeSource.NoiseParameters humidityParameters = new MultiNoiseBiomeSource.NoiseParameters(-7, 2.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
        MultiNoiseBiomeSource.NoiseParameters continentalnessParameters = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.0D, 2.0D, 2.0D, 2.0D, 1.0D, 1.0D, 1.0D, 1.0D);
        MultiNoiseBiomeSource.NoiseParameters erosionParameters = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.2D, 0.0D, 1.0D, 0.0D, 0.0D);
        MultiNoiseBiomeSource.NoiseParameters weirdnessParameters = new MultiNoiseBiomeSource.NoiseParameters(-7, 1.0D, 2.0D, 1.0D, 0.0D, 0.0D, 0.0D);

        return new MultiNoiseBiomeSource(seed, new MultiNoiseUtil.Entries<>(parameters),
            temperatureParameters, humidityParameters, continentalnessParameters, erosionParameters, weirdnessParameters,
            -16, 48, false, Optional.empty());
    }

    public static ImmutableList<Pair<NoiseHypercube, Supplier<Biome>>> createTestBiomeEntries(Registry<Biome> biomeRegistry, Consumer<ImmutableList.Builder<Pair<NoiseHypercube, RegistryKey<Biome>>>> writeParameters) {
        ImmutableList.Builder<Pair<NoiseHypercube, RegistryKey<Biome>>> entries = ImmutableList.builder();
        writeParameters.accept(entries);

        //noinspection UnstableApiUsage
        return entries.build().stream().<Pair<NoiseHypercube, Supplier<Biome>>>map(entry -> entry.mapSecond(biomeKey -> () ->
            biomeRegistry.getOrThrow(biomeKey))).collect(ImmutableList.toImmutableList());
    }
}
