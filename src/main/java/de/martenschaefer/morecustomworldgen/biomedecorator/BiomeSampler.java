package de.martenschaefer.morecustomworldgen.biomedecorator;

import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

@FunctionalInterface
public interface BiomeSampler {
    BiomeContext sample(int x, int y, int z);
}
