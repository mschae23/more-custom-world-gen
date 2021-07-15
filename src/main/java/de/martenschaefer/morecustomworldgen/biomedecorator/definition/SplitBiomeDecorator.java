package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.MergingBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeDecoratorEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public record SplitBiomeDecorator(List<BiomeDecoratorEntry> decoratorsA, List<BiomeDecoratorEntry> decoratorsB,
                                  MergingBiomeDecorator mergingDecorator) implements ParentedBiomeDecorator {
    // TODO

    public List<BiomeDecoratorEntry> getDecoratorsA() {
        return this.decoratorsA;
    }

    public List<BiomeDecoratorEntry> getDecoratorsB() {
        return this.decoratorsB;
    }

    public MergingBiomeDecorator getMergingDecorator() {
        return this.mergingDecorator;
    }

    @Override
    public Codec<SplitBiomeDecorator> getCodec() {
        return null;
    }

    @Override
    public BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        return null;
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return Stream.empty();
    }
}
