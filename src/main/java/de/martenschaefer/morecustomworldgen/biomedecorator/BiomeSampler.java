package de.martenschaefer.morecustomworldgen.biomedecorator;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

@FunctionalInterface
public interface BiomeSampler {
    RegistryKey<Biome> sample(int x, int y, int z);
}
