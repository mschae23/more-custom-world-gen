package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public record FixedBiomeSampler(BiomeContext biome) implements BiomeSampler {
    @Override
    public BiomeContext sample(int x, int y, int z) {
        return this.biome;
    }
}
