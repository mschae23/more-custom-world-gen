package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type;

import net.minecraft.world.biome.layer.util.CoordinateTransformer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerFactory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;

public interface ConvertingLayer<T, T2> extends CoordinateTransformer {
    default <R extends LayerSampler<T>, R2 extends LayerSampler<T2>> LayerFactory<T, R> create(LayerSampleContext<T, T2, T2, R, R2, R2> context, LayerFactory<T2, R2> parent) {
        return () -> {
            R2 layerSampler = parent.make();
            return context.createSampler((i, j) -> {
                context.initSeed(i, j);
                return this.sample(context, layerSampler, i, j);
            }, layerSampler);
        };
    }

    T sample(LayerSampleContext<T, T2, T2, ?, ?, ?> context, LayerSampler<T2> parent, int x, int z);
}
