package de.martenschaefer.morecustomworldgen.terrainlayered.impl;

import java.util.Optional;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainBiomeSampler;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainSamplerContext;

public class EmptyTerrainBiomeSampler implements TerrainBiomeSampler {
    public static final EmptyTerrainBiomeSampler INSTANCE = new EmptyTerrainBiomeSampler();

    private EmptyTerrainBiomeSampler() {
    }

    @Override
    public Optional<RegistryKey<Biome>> sample(int x, int y, int z, TerrainSamplerContext context) {
        return Optional.empty();
    }
}
