package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.List;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.MergingBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeDecoratorEntry;

public class SplitBiomeDecorator extends BiomeDecorator {
    // TODO

    private final List<BiomeDecoratorEntry> decoratorsA;
    private final List<BiomeDecoratorEntry> decoratorsB;
    private final MergingBiomeDecorator mergingDecorator;

    public SplitBiomeDecorator(List<BiomeDecoratorEntry> decoratorsA, List<BiomeDecoratorEntry> decoratorsB, MergingBiomeDecorator mergingDecorator) {
        this.decoratorsA = decoratorsA;
        this.decoratorsB = decoratorsB;
        this.mergingDecorator = mergingDecorator;
    }

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
    protected Codec<SplitBiomeDecorator> getCodec() {
        return null;
    }

    @Override
    public RegistryKey<Biome> getBiome(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        return null;
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return null;
    }
}
