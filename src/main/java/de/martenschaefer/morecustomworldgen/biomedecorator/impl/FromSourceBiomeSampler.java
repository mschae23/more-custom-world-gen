package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;

public class FromSourceBiomeSampler implements BiomeSampler {
    private final BiomeAccess.Storage source;
    private final Registry<Biome> biomeRegistry;

    public FromSourceBiomeSampler(BiomeAccess.Storage source, Registry<Biome> biomeRegistry) {
        this.source = source;
        this.biomeRegistry = biomeRegistry;
    }

    public BiomeAccess.Storage getSource() {
        return this.source;
    }

    public Registry<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    @Override
    public RegistryKey<Biome> sample(int x, int y, int z) {
        return RegistryKey.of(Registry.BIOME_KEY, this.biomeRegistry.getId(this.source.getBiomeForNoiseGen(x, y, z)));
    }
}
