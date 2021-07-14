package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;
import de.martenschaefer.morecustomworldgen.util.NorthWestCoordinateTransformer;

public interface SouthEastSamplingLayer<T> extends ParentedLayer<T>, NorthWestCoordinateTransformer {
    T sample(LayerRandomnessSource context, T se);

    default T sample(LayerSampleContext<T, T, T, ?, ?, ?> context, LayerSampler<T> parent, int x, int z) {
        T i = parent.sample(this.transformX(x + 1), this.transformZ(z + 1));
        return this.sample(context, i);
    }
}
