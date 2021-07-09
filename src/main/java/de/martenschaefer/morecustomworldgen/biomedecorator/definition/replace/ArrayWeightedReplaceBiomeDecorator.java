package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.List;
import java.util.stream.Collectors;
import com.mojang.serialization.Codec;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeSetEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.WeightedReplaceBiomeEntry;

public class ArrayWeightedReplaceBiomeDecorator extends BiomeDecorator {
    public static final Codec<ArrayWeightedReplaceBiomeDecorator> CODEC = WeightedReplaceBiomeEntry.CODEC.listOf().fieldOf("biomes")
        .xmap(ArrayWeightedReplaceBiomeDecorator::new, ArrayWeightedReplaceBiomeDecorator::getBiomes).codec();

    private final List<WeightedReplaceBiomeEntry> biomes;

    public ArrayWeightedReplaceBiomeDecorator(List<WeightedReplaceBiomeEntry> biomes) {
        this.biomes = biomes;
    }

    public List<WeightedReplaceBiomeEntry> getBiomes() {
        return biomes;
    }

    @Override
    protected Codec<ArrayWeightedReplaceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        RegistryKey<Biome> biome = parent.sample(x, y, z);

        for (var entry : this.biomes) {
            if (BiomeSetEntry.contains(entry.comparingBiomes(), biome))
                return WeightedPicker.getAt(entry.biomes(), random.nextInt(WeightedPicker.getWeightSum(entry.biomes())))
                    .orElseThrow().value();
        }

        return biome;
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return this.biomes.stream().flatMap(entry -> entry.getBiomes(biomeRegistry)).collect(Collectors.toList());
    }
}
