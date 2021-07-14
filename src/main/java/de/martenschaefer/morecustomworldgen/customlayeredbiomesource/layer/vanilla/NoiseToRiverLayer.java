package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.CrossSamplingLayer;

public enum NoiseToRiverLayer implements CrossSamplingLayer<Integer> {
    INSTANCE;

    @Override
    public Integer sample(LayerRandomnessSource context, Integer n, Integer e, Integer s, Integer w, Integer center) {
        int i = isValidForRiver(center);
        return i == isValidForRiver(w) && i == isValidForRiver(n) && i == isValidForRiver(e) && i == isValidForRiver(s) ? -1 : 1;
    }

    private static int isValidForRiver(int value) {
        return value >= 2 ? 2 + (value & 1) : value;
    }
}
