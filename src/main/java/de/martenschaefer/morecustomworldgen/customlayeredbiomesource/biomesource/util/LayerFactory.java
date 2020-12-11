package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util;

public interface LayerFactory<T, A extends LayerSampler<T>> {
    A make();
}
