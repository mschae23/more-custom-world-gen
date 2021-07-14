package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;

public interface LayerSampleContext<T, T2, T3, S extends LayerSampler<T>, S2 extends LayerSampler<T2>, S3 extends LayerSampler<T3>> extends LayerRandomnessSource {
    void initSeed(long x, long y);

    S createSampler(LayerOperator<T> operator);

    default S createSampler(LayerOperator<T> operator, S2 parent) {
        return this.createSampler(operator);
    }

    default S createSampler(LayerOperator<T> operator, S2 parent, S3 sampler2) {
        return this.createSampler(operator, parent);
    }
}
