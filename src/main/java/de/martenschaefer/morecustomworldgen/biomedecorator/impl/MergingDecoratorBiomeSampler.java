package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.MergingBiomeDecorator;

public class MergingDecoratorBiomeSampler implements BiomeSampler {
    private final DecoratorRandomnessSource random;
    private final BiomeSampler parent;
    private final BiomeSampler parent2;
    private final MergingBiomeDecorator decorator;

    public MergingDecoratorBiomeSampler(DecoratorRandomnessSource random, BiomeSampler parent, BiomeSampler parent2, MergingBiomeDecorator decorator) {
        this.random = random;
        this.parent = parent;
        this.parent2 = parent2;
        this.decorator = decorator;
    }

    public DecoratorRandomnessSource getRandom() {
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
    public RegistryKey<Biome> sample(int x, int y, int z) {
        return this.decorator.getBiome(this.random, this.parent, this.parent2, x, y, z);
    }
}
