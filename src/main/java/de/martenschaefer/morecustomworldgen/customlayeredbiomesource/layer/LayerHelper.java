package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer;

import java.util.function.Function;
import java.util.function.LongFunction;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.BiomeLayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.CachingLayerContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.CachingLayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerFactory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;

public final class LayerHelper {
    public static <T, T2, T3> LongFunction<LayerSampleContext<T, T2, T3, CachingLayerSampler<T>, CachingLayerSampler<T2>, CachingLayerSampler<T3>>> createContextProvider(long seed) {
        return salt -> new CachingLayerContext<>(25, seed, salt);
    }

    public static <T, T2, T3> BiomeLayerSampler<T> createLayerSampler(long seed, Function<LongFunction<LayerSampleContext<T, T2, T3, CachingLayerSampler<T>, CachingLayerSampler<T2>, CachingLayerSampler<T3>>>, LayerFactory<T, CachingLayerSampler<T>>> layers) {
        LayerFactory<T, CachingLayerSampler<T>> layerFactory = layers.apply(
            LayerHelper.createContextProvider(seed));
        return new BiomeLayerSampler<>(layerFactory);
    }
}
