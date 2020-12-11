package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util;

@FunctionalInterface
public interface LayerSampler<T> {
    T sample(int x, int z);
}
