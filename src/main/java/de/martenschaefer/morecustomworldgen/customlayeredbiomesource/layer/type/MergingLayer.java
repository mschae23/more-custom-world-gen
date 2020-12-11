package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type;

import net.minecraft.world.biome.layer.util.CoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerFactory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;

public interface MergingLayer<T, T2> extends CoordinateTransformer {
    default <R extends LayerSampler<T>, R2 extends LayerSampler<T2>> LayerFactory<T, R> create(LayerSampleContext<T, T, T2, R, R, R2> context, LayerFactory<T, R> layer1, LayerFactory<T2, R2> layer2) {
        return () -> {
            R layerSampler = layer1.make();
            R2 layerSampler2 = layer2.make();
            return context.createSampler((i, j) -> {
                context.initSeed(i, j);
                return this.sample(context, layerSampler, layerSampler2, i, j);
            }, layerSampler, layerSampler2);
        };
    }

    T sample(LayerRandomnessSource context, LayerSampler<T> sampler1, LayerSampler<T2> sampler2, int x, int z);
}
