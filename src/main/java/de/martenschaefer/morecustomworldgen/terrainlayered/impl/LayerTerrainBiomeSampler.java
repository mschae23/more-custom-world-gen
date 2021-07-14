package de.martenschaefer.morecustomworldgen.terrainlayered.impl;

import java.util.Optional;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainBiomeSampler;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainSamplerContext;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainLayer;

public class LayerTerrainBiomeSampler implements TerrainBiomeSampler {
    private final LayerRandomnessSource random;
    private final TerrainBiomeSampler parent;
    private final TerrainLayer layer;

    public LayerTerrainBiomeSampler(LayerRandomnessSource random, TerrainBiomeSampler parent, TerrainLayer layer) {
        this.random = random;
        this.parent = parent;
        this.layer = layer;
    }

    public LayerRandomnessSource getRandom() {
        return this.random;
    }

    public TerrainBiomeSampler getParent() {
        return this.parent;
    }

    public TerrainLayer getLayer() {
        return this.layer;
    }

    @Override
    public Optional<RegistryKey<Biome>> sample(int x, int y, int z, TerrainSamplerContext context) {
        this.random.initSeed(x, y, z);
        return this.layer.getBiome(this.random, this.parent, x, y, z, context);
    }
}
