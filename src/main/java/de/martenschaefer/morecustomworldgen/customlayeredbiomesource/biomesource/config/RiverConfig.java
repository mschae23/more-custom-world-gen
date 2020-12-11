package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class RiverConfig {
    public static final Codec<RiverConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Override.CODEC.listOf().fieldOf("overrides").forGetter(RiverConfig::getOverrides),
            RegistryKeys.BIOME_CODEC.fieldOf("river").forGetter(RiverConfig::getRiver),
            Codec.INT.fieldOf("river_size").forGetter(RiverConfig::getRiverSize)
        ).apply(instance, instance.stable(RiverConfig::new)));
    
    private final List<Override> overrides;
    private final RegistryKey<Biome> river;
    private final int riverSize;

    public RiverConfig(List<Override> overrides, RegistryKey<Biome> river, int riverSize) {
        this.overrides = overrides;
        this.river = river;
        this.riverSize = riverSize;
    }

    public List<Override> getOverrides() {
        return this.overrides;
    }

    public RegistryKey<Biome> getRiver() {
        return this.river;
    }

    public int getRiverSize() {
        return this.riverSize;
    }

    public List<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        List<Supplier<Biome>> riverBiomes = this.getOverrides().stream()
            .map(Override::getRiver)
            .<Supplier<Biome>>map(riverBiome -> () -> biomeRegistry.get(riverBiome))
            .collect(Collectors.toList());

        riverBiomes.add(() -> biomeRegistry.get(this.getRiver()));

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
