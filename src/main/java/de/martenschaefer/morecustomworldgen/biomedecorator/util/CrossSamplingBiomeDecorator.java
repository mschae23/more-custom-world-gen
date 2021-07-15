package de.martenschaefer.morecustomworldgen.biomedecorator.util;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;

public interface CrossSamplingBiomeDecorator extends ParentedBiomeDecorator {
    @Override
    default BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        return this.sample(random, parent.sample(x, y, z - 1), parent.sample(x + 1, y, z), parent.sample(x, y, z + 1), parent.sample(x - 1, y, z), parent.sample(x, y, z));
    }

    BiomeContext sample(LayerRandomnessSource random, BiomeContext n, BiomeContext e, BiomeContext s, BiomeContext w, BiomeContext center);
}
