package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.mojang.serialization.Codec;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.SimpleReplaceBiomeEntry;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class SimpleReplaceBiomeDecorator extends BiomeDecorator {
    public static final Codec<SimpleReplaceBiomeDecorator> CODEC = SimpleReplaceBiomeEntry.CODEC.listOf().fieldOf("biomes")
        .xmap(SimpleReplaceBiomeDecorator::new, SimpleReplaceBiomeDecorator::getBiomes).codec();

    private final List<SimpleReplaceBiomeEntry> biomes;

    public SimpleReplaceBiomeDecorator(List<SimpleReplaceBiomeEntry> biomes) {
        this.biomes = biomes;
    }

    public List<SimpleReplaceBiomeEntry> getBiomes() {
        return biomes;
    }

    @Override
    protected Codec<SimpleReplaceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return this.biomes.stream()
            .map(SimpleReplaceBiomeEntry::biome)
            .map(biomeRegistry::get)
            .collect(Collectors.toList());
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        RegistryKey<Biome> biome = parent.sample(x, y, z);

        for (SimpleReplaceBiomeEntry entry : this.biomes) {
            if (RegistryKeys.equals(biome, entry.comparingBiome()) && entry.chance().get(random))
                return entry.biome();
        }

        return biome;
    }
}
