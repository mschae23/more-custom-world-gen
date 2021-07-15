package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.MergingBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public class MergingDecoratorBiomeSampler implements BiomeSampler {
    private final LayerRandomnessSource random;
    private final BiomeSampler parent;
    private final BiomeSampler parent2;
    private final MergingBiomeDecorator decorator;

    public MergingDecoratorBiomeSampler(LayerRandomnessSource random, BiomeSampler parent, BiomeSampler parent2, MergingBiomeDecorator decorator) {
        this.random = random;
        this.parent = parent;
        this.parent2 = parent2;
        this.decorator = decorator;
    }

    public LayerRandomnessSource getRandom() {
        return this.random;
    }

    public BiomeSampler getParent() {
        return this.parent;
    }

    public BiomeSampler getParent2() {
        return this.parent2;
    }

    public MergingBiomeDecorator getDecorator() {
        return this.decorator;
    }

    @Override
    public BiomeContext sample(int x, int y, int z) {
        return this.decorator.sample(this.random, this.parent, this.parent2, x, y, z);
    }
}
