package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.CrossSamplingLayer;

public class SmoothLayer<T> implements CrossSamplingLayer<T> {
    public SmoothLayer() {
    }

    @Override
    public T sample(LayerRandomnessSource context, T n, T e, T s, T w, T center) {
        boolean bl = e == w;
        boolean bl2 = n == s;
        if (bl == bl2) {
            if (bl) {
                return context.nextInt(2) == 0 ? w : n;
            } else {
                return center;
            }
        } else {
            return bl ? w : n;
        }
    }
}
