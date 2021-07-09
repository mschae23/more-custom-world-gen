package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class InnerBiomeConfig {
    public static final Codec<InnerBiomeConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(InnerBiomeConfig::getBiome),
            RegistryKeys.BIOME_CODEC.fieldOf("inner_biome").forGetter(InnerBiomeConfig::getInnerBiome),
            Codec.INT.fieldOf("chance").forGetter(InnerBiomeConfig::getChance)
        ).apply(instance, instance.stable(InnerBiomeConfig::new))
    );

    private final RegistryKey<Biome> biome;
    private final RegistryKey<Biome> innerBiome;
    private final int chance;

    public InnerBiomeConfig(Identifier biome, Identifier innerBiome, int chance) {
        this(RegistryKey.of(Registry.BIOME_KEY, biome), RegistryKey.of(Registry.BIOME_KEY, innerBiome), chance);
    }

    public InnerBiomeConfig(RegistryKey<Biome> biome, RegistryKey<Biome> innerBiome, int chance) {
        this.biome = biome;
        this.innerBiome = innerBiome;
        this.chance = chance;
    }

    public RegistryKey<Biome> getBiome() {
        return this.biome;
    }

    public RegistryKey<Biome> getInnerBiome() {
        return this.innerBiome;
    }

    public int getChance() {
        return this.chance;
    }

    public static List<Supplier<Biome>> getBiomes(List<InnerBiomeConfig> innerBiomes, Registry<Biome> biomeRegistry) {
        return innerBiomes.stream()
            .map(InnerBiomeConfig::getBiome)
            .<Supplier<Biome>>map(key -> () -> biomeRegistry.getOrThrow(key))
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
