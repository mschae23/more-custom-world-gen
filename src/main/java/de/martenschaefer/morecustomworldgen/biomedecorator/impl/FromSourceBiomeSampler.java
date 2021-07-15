package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public record FromSourceBiomeSampler(BiomeAccess.Storage source, Registry<Biome> biomeRegistry) implements BiomeSampler {
    @Override
    public BiomeContext sample(int x, int y, int z) {
        RegistryKey<Biome> key = RegistryKey.of(Registry.BIOME_KEY, this.biomeRegistry.getId(this.source.getBiomeForNoiseGen(x, y, z)));

        return new BiomeContext(key, 0, 0, 0, 0, 0);
    }
}
