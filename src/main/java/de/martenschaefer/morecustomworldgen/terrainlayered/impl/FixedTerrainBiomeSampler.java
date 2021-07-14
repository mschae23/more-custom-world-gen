package de.martenschaefer.morecustomworldgen.terrainlayered.impl;

import java.util.Optional;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainBiomeSampler;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainSamplerContext;

public record FixedTerrainBiomeSampler(RegistryKey<Biome> biome) implements TerrainBiomeSampler {
    public RegistryKey<Biome> getBiome() {
        return biome;
    }

    @Override
    public Optional<RegistryKey<Biome>> sample(int x, int y, int z, TerrainSamplerContext context) {
        return Optional.of(this.biome);
    }
}
