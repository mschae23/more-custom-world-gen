package de.martenschaefer.morecustomworldgen.terrainlayered;

import java.util.Optional;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

@FunctionalInterface
public interface TerrainBiomeSampler {
    Optional<RegistryKey<Biome>> sample(int x, int y, int z, TerrainSamplerContext context);
}
