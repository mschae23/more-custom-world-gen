package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;

public class DecoratorBiomeSampler implements BiomeSampler {
    private final DecoratorRandomnessSource random;
    private final BiomeSampler parent;
    private final BiomeDecorator decorator;

    public DecoratorBiomeSampler(DecoratorRandomnessSource random, BiomeSampler parent, BiomeDecorator decorator) {
        this.random = random;
        this.parent = parent;
        this.decorator = decorator;
    }

    public DecoratorRandomnessSource getRandom() {
        return this.random;
    }

    public BiomeSampler getParent() {
        return this.parent;
    }

    public BiomeDecorator getDecorator() {
        return this.decorator;
    }

    @Override
    public RegistryKey<Biome> sample(int x, int y, int z) {
        return this.decorator.getBiome(this.random, this.parent, x, y, z);
    }
}
