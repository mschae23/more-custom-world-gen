package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;

public class DecoratorBiomeSampler implements BiomeSampler {
    private final LayerRandomnessSource random;
    private final BiomeSampler parent;
    private final BiomeDecorator decorator;

    public DecoratorBiomeSampler(LayerRandomnessSource random, BiomeSampler parent, BiomeDecorator decorator) {
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
    public RegistryKey<Biome> sample(int x, int y, int z) {
        this.random.initSeed(x, y, z);
        return this.decorator.getBiome(this.random, this.parent, x, y, z);
    }
}
