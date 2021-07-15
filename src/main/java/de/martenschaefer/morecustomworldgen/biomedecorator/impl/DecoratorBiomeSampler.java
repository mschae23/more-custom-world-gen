package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public class DecoratorBiomeSampler implements BiomeSampler {
    private final LayerRandomnessSource random;
    private final BiomeSampler parent;
    private final ParentedBiomeDecorator decorator;

    public DecoratorBiomeSampler(LayerRandomnessSource random, BiomeSampler parent, ParentedBiomeDecorator decorator) {
        this.random = random;
        this.parent = parent;
        this.decorator = decorator;
    }

    public LayerRandomnessSource getRandom() {
        return this.random;
    }

    public BiomeSampler getParent() {
        return this.parent;
    }

    public BiomeDecorator getDecorator() {
        return this.decorator;
    }

    @Override
    public BiomeContext sample(int x, int y, int z) {
        this.random.initSeed(x, y, z);
        return this.decorator.sample(this.random, this.parent, x, y, z);
    }
}
