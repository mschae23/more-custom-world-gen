package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type;

import net.minecraft.world.biome.layer.util.CoordinateTransformer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerFactory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;

public interface ParentedLayer<T> extends CoordinateTransformer {
    default <R extends LayerSampler<T>> LayerFactory<T, R> create(LayerSampleContext<T, T, T, R, R, R> context, LayerFactory<T, R> parent) {
        return () -> {
            R layerSampler = parent.make();
            return context.createSampler((i, j) -> {
                context.initSeed(i, j);
                return this.sample(context, layerSampler, i, j);
            }, layerSampler);
        };
    }

    T sample(LayerSampleContext<T, T, T, ?, ?, ?> context, LayerSampler<T> parent, int x, int z);
}
