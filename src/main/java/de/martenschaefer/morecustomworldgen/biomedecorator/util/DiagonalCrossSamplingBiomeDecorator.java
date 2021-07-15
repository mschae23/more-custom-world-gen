package de.martenschaefer.morecustomworldgen.biomedecorator.util;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;

public interface DiagonalCrossSamplingBiomeDecorator extends ParentedBiomeDecorator {
    @Override
    default BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        return this.sample(random, parent.sample(x - 1, y, z + 1), parent.sample(x + 1, y, z + 1), parent.sample(x + 1, y, z - 1), parent.sample(x - 1, y, z - 1), parent.sample(x, y, z));
    }

    BiomeContext sample(LayerRandomnessSource random, BiomeContext sw, BiomeContext se, BiomeContext ne, BiomeContext nw, BiomeContext center);
}
