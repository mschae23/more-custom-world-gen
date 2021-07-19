package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.SimpleReplaceBiomeEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public record SimpleReplaceBiomeDecorator(
    List<SimpleReplaceBiomeEntry> biomes) implements ParentedBiomeDecorator {
    public static final Codec<SimpleReplaceBiomeDecorator> CODEC = SimpleReplaceBiomeEntry.CODEC.listOf().fieldOf("biomes")
        .xmap(SimpleReplaceBiomeDecorator::new, SimpleReplaceBiomeDecorator::biomes).codec();

    @Override
    public Codec<SimpleReplaceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return this.biomes.stream()
            .map(SimpleReplaceBiomeEntry::biome)
            .map(biome -> () -> biomeRegistry.get(biome.biome()));
    }

    @Override
    public BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        BiomeContext biome = parent.sample(x, y, z);

        for (SimpleReplaceBiomeEntry entry : this.biomes) {
            if (entry.test(biome, random))
                return entry.biome();
        }

        return biome;
    }
}
