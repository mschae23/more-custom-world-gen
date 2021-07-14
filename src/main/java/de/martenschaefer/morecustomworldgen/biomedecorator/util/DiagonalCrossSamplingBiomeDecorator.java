package de.martenschaefer.morecustomworldgen.biomedecorator.util;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;

public abstract class DiagonalCrossSamplingBiomeDecorator extends BiomeDecorator {
    @Override
    public RegistryKey<Biome> getBiome(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        return this.getBiome(random, parent.sample(x - 1, y, z + 1), parent.sample(x + 1, y, z + 1), parent.sample(x + 1, y, z - 1), parent.sample(x - 1, y, z - 1), parent.sample(x, y, z));
    }

    public abstract RegistryKey<Biome> getBiome(LayerRandomnessSource random, RegistryKey<Biome> sw, RegistryKey<Biome> se, RegistryKey<Biome> ne, RegistryKey<Biome> nw, RegistryKey<Biome> center);
}
