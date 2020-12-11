package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class ShoreBiomesConfig {
    public static final Codec<ShoreBiomesConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.STRING.listOf().fieldOf("ignored_categories").forGetter(ShoreBiomesConfig::getIgnoredCategories),
            Override.CODEC.listOf().fieldOf("overrides").forGetter(ShoreBiomesConfig::getOverrides),
            RegistryKeys.BIOME_CODEC.fieldOf("default_beach").forGetter(ShoreBiomesConfig::getDefaultBeach)
        ).apply(instance, instance.stable(ShoreBiomesConfig::new))
    );

    private final List<String> ignoredCategories;
    private final List<Override> overrides;
    private final RegistryKey<Biome> defaultBeach;

    public ShoreBiomesConfig(List<String> ignoredCategories, List<Override> overrides, RegistryKey<Biome> defaultBeach) {
        this.ignoredCategories = ignoredCategories;
        this.overrides = overrides;
        this.defaultBeach = defaultBeach;
    }

    public List<String> getIgnoredCategories() {
        return this.ignoredCategories;
    }

    public List<Override> getOverrides() {
        return this.overrides;
    }

    public RegistryKey<Biome> getDefaultBeach() {
        return this.defaultBeach;
    }

    public List<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        List<Supplier<Biome>> shoreBiomes = this.getOverrides().stream()
            .map(Override::getShoreBiome)
            .<Supplier<Biome>>map(shoreBiome -> () -> biomeRegistry.get(shoreBiome))
            .collect(Collectors.toList());

        shoreBiomes.add(() -> biomeRegistry.get(this.getDefaultBeach()));

        return shoreBiomes;
    }

    public static class Override {
        public static final Codec<Override> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                RegistryKeys.BIOME_LIST_CODEC.fieldOf("biomes").forGetter(Override::getBiomes),
                RegistryKeys.BIOME_LIST_CODEC.fieldOf("bordering_biomes").forGetter(Override::getAndBorderingBiomes),
                RegistryKeys.BIOME_LIST_CODEC.fieldOf("or_bordering_biomes").orElse(Collections.emptyList()).forGetter(Override::getOrBorderingBiomes),
                RegistryKeys.BIOME_CODEC.fieldOf("shore_biome").forGetter(Override::getShoreBiome),
                Codec.BOOL.fieldOf("negative").orElse(false).forGetter(Override::isNegative),
                Codec.BOOL.fieldOf("check_if_bordering_ocean").orElse(true).forGetter(Override::isCheckIfBorderingOcean),
                Codec.BOOL.fieldOf("continue").orElse(false).forGetter(Override::isContinue)
            ).apply(instance, instance.stable(Override::new))
        );

        private final List<RegistryKey<Biome>> biomes;
        private final List<RegistryKey<Biome>> andBorderingBiomes;
        private final List<RegistryKey<Biome>> orBorderingBiomes;
        private final RegistryKey<Biome> shoreBiome;
        private final boolean negative;
        private final boolean checkIfBorderingOcean;
        private final boolean continueAfterOverride;

        public Override(List<RegistryKey<Biome>> biomes, List<RegistryKey<Biome>> andBorderingBiomes, List<RegistryKey<Biome>> orBorderingBiomes, RegistryKey<Biome> shoreBiome, boolean negative) {
            this(biomes, andBorderingBiomes, orBorderingBiomes, shoreBiome, negative, true, false);
        }

        public Override(List<RegistryKey<Biome>> biomes, List<RegistryKey<Biome>> andBorderingBiomes, List<RegistryKey<Biome>> orBorderingBiomes, RegistryKey<Biome> shoreBiome, boolean negative, boolean checkIfBorderingOcean, boolean continueAfterOverride) {
            this.biomes = biomes;
            this.andBorderingBiomes = andBorderingBiomes;
            this.orBorderingBiomes = orBorderingBiomes;
            this.shoreBiome = shoreBiome;
            this.negative = negative;
            this.checkIfBorderingOcean = checkIfBorderingOcean;
            this.continueAfterOverride = continueAfterOverride;
        }

        public List<RegistryKey<Biome>> getBiomes() {
            return this.biomes;
        }

        public List<RegistryKey<Biome>> getAndBorderingBiomes() {
            return this.andBorderingBiomes;
        }

        public List<RegistryKey<Biome>> getOrBorderingBiomes() {
            return this.orBorderingBiomes;
        }

        public RegistryKey<Biome> getShoreBiome() {
            return this.shoreBiome;
        }

        public boolean isNegative() {
            return this.negative;
        }

        public boolean isCheckIfBorderingOcean() {
            return this.checkIfBorderingOcean;
        }

        public boolean isContinue() {
            return this.continueAfterOverride;
        }
    }
}
