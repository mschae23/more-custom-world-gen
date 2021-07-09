package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class OceanBiomesConfig {
    public static final Codec<OceanBiomesConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.BOOL.fieldOf("apply_ocean_temperatures").orElse(true).forGetter(OceanBiomesConfig::shouldApplyOceanTemperatures),
            RegistryKeys.BIOME_CODEC.fieldOf("ocean").forGetter(OceanBiomesConfig::getOcean),
            RegistryKeys.BIOME_CODEC.fieldOf("warm_ocean").forGetter(OceanBiomesConfig::getWarmOcean),
            RegistryKeys.BIOME_CODEC.fieldOf("lukewarm_ocean").forGetter(OceanBiomesConfig::getLukewarmOcean),
            RegistryKeys.BIOME_CODEC.fieldOf("cold_ocean").forGetter(OceanBiomesConfig::getColdOcean),
            RegistryKeys.BIOME_CODEC.fieldOf("frozen_ocean").forGetter(OceanBiomesConfig::getFrozenOcean),
            RegistryKeys.BIOME_CODEC.fieldOf("deep_ocean").forGetter(OceanBiomesConfig::getDeepOcean),
            RegistryKeys.BIOME_CODEC.fieldOf("deep_warm_ocean").forGetter(OceanBiomesConfig::getDeepWarmOcean),
            RegistryKeys.BIOME_CODEC.fieldOf("deep_lukewarm_ocean").forGetter(OceanBiomesConfig::getDeepLukewarmOcean),
            RegistryKeys.BIOME_CODEC.fieldOf("deep_cold_ocean").forGetter(OceanBiomesConfig::getDeepColdOcean),
            RegistryKeys.BIOME_CODEC.fieldOf("deep_frozen_ocean").forGetter(OceanBiomesConfig::getDeepFrozenOcean)
        ).apply(instance, instance.stable(OceanBiomesConfig::new))
    );

    private final boolean applyOceanTemperatures;
    private final RegistryKey<Biome> ocean;
    private final RegistryKey<Biome> warmOcean;
    private final RegistryKey<Biome> lukewarmOcean;
    private final RegistryKey<Biome> coldOcean;
    private final RegistryKey<Biome> frozenOcean;
    private final RegistryKey<Biome> deepOcean;
    private final RegistryKey<Biome> deepWarmOcean;
    private final RegistryKey<Biome> deepLukewarmOcean;
    private final RegistryKey<Biome> deepColdOcean;
    private final RegistryKey<Biome> deepFrozenOcean;

    public OceanBiomesConfig(boolean applyOceanTemperatures, RegistryKey<Biome> ocean, RegistryKey<Biome> warmOcean, RegistryKey<Biome> lukewarmOcean, RegistryKey<Biome> coldOcean, RegistryKey<Biome> frozenOcean, RegistryKey<Biome> deepOcean, RegistryKey<Biome> deepWarmOcean, RegistryKey<Biome> deepLukewarmOcean, RegistryKey<Biome> deepColdOcean, RegistryKey<Biome> deepFrozenOcean) {
        this.applyOceanTemperatures = applyOceanTemperatures;
        this.ocean = ocean;
        this.warmOcean = warmOcean;
        this.lukewarmOcean = lukewarmOcean;
        this.coldOcean = coldOcean;
        this.frozenOcean = frozenOcean;
        this.deepOcean = deepOcean;
        this.deepWarmOcean = deepWarmOcean;
        this.deepLukewarmOcean = deepLukewarmOcean;
        this.deepColdOcean = deepColdOcean;
        this.deepFrozenOcean = deepFrozenOcean;
    }

    public boolean shouldApplyOceanTemperatures() {
        return this.applyOceanTemperatures;
    }

    public RegistryKey<Biome> getOcean() {
        return this.ocean;
    }

    public RegistryKey<Biome> getWarmOcean() {
        return this.warmOcean;
    }

    public RegistryKey<Biome> getLukewarmOcean() {
        return this.lukewarmOcean;
    }

    public RegistryKey<Biome> getColdOcean() {
        return this.coldOcean;
    }

    public RegistryKey<Biome> getFrozenOcean() {
        return this.frozenOcean;
    }

    public RegistryKey<Biome> getDeepOcean() {
        return this.deepOcean;
    }

    public RegistryKey<Biome> getDeepWarmOcean() {
        return this.deepWarmOcean;
    }

    public RegistryKey<Biome> getDeepLukewarmOcean() {
        return this.deepLukewarmOcean;
    }

    public RegistryKey<Biome> getDeepColdOcean() {
        return this.deepColdOcean;
    }

    public RegistryKey<Biome> getDeepFrozenOcean() {
        return this.deepFrozenOcean;
    }

    public List<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        List<RegistryKey<Biome>> biomes = new ArrayList<>();

        biomes.add(this.getOcean());
        biomes.add(this.getWarmOcean());
        biomes.add(this.getLukewarmOcean());
        biomes.add(this.getColdOcean());
        biomes.add(this.getFrozenOcean());
        biomes.add(this.getDeepOcean());
        biomes.add(this.getDeepWarmOcean());
        biomes.add(this.getDeepLukewarmOcean());
        biomes.add(this.getDeepColdOcean());
        biomes.add(this.getDeepFrozenOcean());

        return biomes.stream()
            .<Supplier<Biome>>map(key -> () -> biomeRegistry.getOrThrow(key))
            .collect(Collectors.toList());
    }
}
