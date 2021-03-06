package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class RiverConfig {
    public static final Codec<RiverConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.BOOL.fieldOf("generate_rivers").orElse(true).forGetter(RiverConfig::shouldGenerateRivers),
            Override.CODEC.listOf().fieldOf("overrides").forGetter(RiverConfig::getOverrides),
            RegistryKeys.BIOME_CODEC.fieldOf("river").forGetter(RiverConfig::getRiver)
        ).apply(instance, instance.stable(RiverConfig::new)));

    private final boolean generateRivers;
    private final List<Override> overrides;
    private final RegistryKey<Biome> river;

    public RiverConfig(boolean generateRivers, List<Override> overrides, RegistryKey<Biome> river) {
        this.generateRivers = generateRivers;
        this.overrides = overrides;
        this.river = river;
    }

    public boolean shouldGenerateRivers() {
        return this.generateRivers;
    }

    public List<Override> getOverrides() {
        return this.overrides;
    }

    public RegistryKey<Biome> getRiver() {
        return this.river;
    }

    public List<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        List<Supplier<Biome>> riverBiomes = this.getOverrides().stream()
            .map(Override::getRiver)
            .<Supplier<Biome>>map(riverBiome -> () -> biomeRegistry.getOrThrow(riverBiome))
            .collect(Collectors.toList());

        riverBiomes.add(() -> biomeRegistry.getOrThrow(this.getRiver()));

        return riverBiomes;
    }

    public static class Override {
        public static final Codec<Override> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                RegistryKeys.BIOME_LIST_CODEC.fieldOf("biomes").forGetter(Override::getBiomes),
                RegistryKeys.BIOME_CODEC.fieldOf("river").forGetter(Override::getRiver)
            ).apply(instance, instance.stable(Override::new)));

        private final List<RegistryKey<Biome>> biomes;
        private final RegistryKey<Biome> river;

        public Override(List<RegistryKey<Biome>> biomes, RegistryKey<Biome> river) {
            this.biomes = biomes;
            this.river = river;
        }

        public List<RegistryKey<Biome>> getBiomes() {
            return this.biomes;
        }

        public RegistryKey<Biome> getRiver() {
            return this.river;
        }
    }
}
