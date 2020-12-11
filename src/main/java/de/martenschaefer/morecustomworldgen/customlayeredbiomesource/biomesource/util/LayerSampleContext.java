package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util;

import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public interface LayerSampleContext<T, T2, T3, S extends LayerSampler<T>, S2 extends LayerSampler<T2>, S3 extends LayerSampler<T3>> extends LayerRandomnessSource {
    void initSeed(long x, long y);

    S createSampler(LayerOperator<T> operator);

    default S createSampler(LayerOperator<T> operator, S2 parent) {
        return this.createSampler(operator);
    }

    default S createSampler(LayerOperator<T> operator, S2 parent, S3 sampler2) {
        return this.createSampler(operator, parent);
    }

    default T choose(T a, T b) {
        return this.nextInt(2) == 0 ? a : b;
    }

    default T choose(T a, T b, T c, T d) {
        int i = this.nextInt(4);
        if (i == 0) {
            return a;
        } else if (i == 1) {
            return b;
        } else {
            return i == 2 ? c : d;
        }
    }
}
