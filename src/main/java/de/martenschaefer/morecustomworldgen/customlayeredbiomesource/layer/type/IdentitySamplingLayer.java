package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type;

import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;

public interface IdentitySamplingLayer<T> extends ParentedLayer<T>, IdentityCoordinateTransformer {
    T sample(LayerRandomnessSource context, T value);

    default T sample(LayerSampleContext<T, T, T, ?, ?, ?> context, LayerSampler<T> parent, int x, int z) {
        return this.sample(context, parent.sample(this.transformX(x), this.transformZ(z)));
    }
}
