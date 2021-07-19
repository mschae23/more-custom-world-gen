package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public record FromSourceBiomeSampler(BiomeSource source, Registry<Biome> biomeRegistry) implements BiomeSampler {
    @Override
    public BiomeContext sample(int x, int y, int z) {
        RegistryKey<Biome> key = RegistryKey.of(Registry.BIOME_KEY, this.biomeRegistry.getId(this.source.getBiomeForNoiseGen(x, y, z)));

        double[] offsetAndFactor = this.source.method_37612(x, z);
        double offset = offsetAndFactor[0];
        double factor = offsetAndFactor[1];

        return new BiomeContext(key, 0, 0, 0, 0, 0, offset, factor);
    }
}
