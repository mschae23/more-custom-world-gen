package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerFactory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;

public interface InitLayer<T> {
    default <R extends LayerSampler<T>> LayerFactory<T, R> create(LayerSampleContext<T, T, T, R, R, R> context) {
        return () -> context.createSampler((x, z) -> {
            context.initSeed(x, z);
            return this.sample(context, x, z);
        });
    }

    T sample(LayerRandomnessSource context, int x, int z);
}
