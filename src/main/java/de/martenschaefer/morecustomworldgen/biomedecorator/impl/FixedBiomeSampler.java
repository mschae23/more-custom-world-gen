package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;

public class FixedBiomeSampler implements BiomeSampler {
    private final RegistryKey<Biome> biome;

    public FixedBiomeSampler(RegistryKey<Biome> biome) {
        this.biome = biome;
    }

    public RegistryKey<Biome> getBiome() {
        return biome;
    }

    @Override
    public RegistryKey<Biome> sample(int x, int y, int z) {
        return this.biome;
    }
}
