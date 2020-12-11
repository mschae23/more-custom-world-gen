package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util;

public class BiomeLayerSampler<T> {
    private final CachingLayerSampler<T> sampler;

    public BiomeLayerSampler(LayerFactory<T, CachingLayerSampler<T>> layerFactory) {
        this.sampler = layerFactory.make();
    }

    public T sample(int x, int z) {
        return this.sampler.sample(x, z);
    }
    
    public CachingLayerSampler<T> getSampler() {
        return this.sampler;
    }
}
