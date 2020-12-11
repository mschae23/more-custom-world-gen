package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type;

import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.NorthWestCoordinateTransformer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;

public interface CrossSamplingLayer<T> extends ParentedLayer<T>, NorthWestCoordinateTransformer {
    T sample(LayerRandomnessSource context, T n, T e, T s, T w, T center);

    default T sample(LayerSampleContext<T, T, T, ?, ?, ?> context, LayerSampler<T> parent, int x, int z) {
        return this.sample(context, parent.sample(this.transformX(x + 1), this.transformZ(z)), parent.sample(this.transformX(x + 2), this.transformZ(z + 1)), parent.sample(this.transformX(x + 1), this.transformZ(z + 2)), parent.sample(this.transformX(x), this.transformZ(z + 1)), parent.sample(this.transformX(x + 1), this.transformZ(z + 1)));
    }
}
