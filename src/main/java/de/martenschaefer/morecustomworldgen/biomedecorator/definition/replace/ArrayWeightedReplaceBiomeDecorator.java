package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeSetEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.WeightedReplaceBiomeEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public record ArrayWeightedReplaceBiomeDecorator(
    List<WeightedReplaceBiomeEntry> biomes) implements ParentedBiomeDecorator {
    public static final Codec<ArrayWeightedReplaceBiomeDecorator> CODEC = WeightedReplaceBiomeEntry.CODEC.listOf().fieldOf("biomes")
        .xmap(ArrayWeightedReplaceBiomeDecorator::new, ArrayWeightedReplaceBiomeDecorator::biomes).codec();

    @Override
    public Codec<ArrayWeightedReplaceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        BiomeContext biome = parent.sample(x, y, z);

        for (var entry : this.biomes) {
            if (BiomeSetEntry.contains(entry.comparingBiomes(), biome.biome()))
                return WeightedPicker.getAt(entry.biomes(), random.nextInt(WeightedPicker.getWeightSum(entry.biomes())))
                    .orElseThrow().value();
        }

        return biome;
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return this.biomes.stream().flatMap(entry -> entry.getBiomes(biomeRegistry));
    }
}
