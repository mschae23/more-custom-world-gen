package de.martenschaefer.morecustomworldgen.biomedecorator.util;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;

public abstract class CrossSamplingBiomeDecorator extends BiomeDecorator {
    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        return this.getBiome(random, parent.sample(x, y, z - 1), parent.sample(x + 1, y, z), parent.sample(x, y, z + 1), parent.sample(x - 1, y, z), parent.sample(x, y, z));
    }

    public abstract RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, RegistryKey<Biome> n, RegistryKey<Biome> e, RegistryKey<Biome> s, RegistryKey<Biome> w, RegistryKey<Biome> center);
}
