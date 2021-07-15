package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;
import de.martenschaefer.morecustomworldgen.util.WeightEntry;

public record WeightedReplaceBiomeDecorator(List<RegistryKey<Biome>> ignoredBiomes,
                                            List<RegistryKey<Biome>> whitelistedBiomes,
                                            List<WeightEntry<BiomeContext>> biomes) implements ParentedBiomeDecorator {
    public static final Codec<WeightedReplaceBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RegistryKeys.BIOME_LIST_CODEC.fieldOf("ignored_biomes").forGetter(WeightedReplaceBiomeDecorator::ignoredBiomes),
            RegistryKeys.BIOME_LIST_CODEC.fieldOf("whitelisted_biomes").orElseGet(ImmutableList::of).forGetter(WeightedReplaceBiomeDecorator::whitelistedBiomes),
            WeightEntry.createCodec("biome", BiomeContext.CODEC).listOf().fieldOf("biomes").forGetter(WeightedReplaceBiomeDecorator::biomes)
        ).apply(instance, instance.stable(WeightedReplaceBiomeDecorator::new))
    );

    public WeightedReplaceBiomeDecorator(List<RegistryKey<Biome>> ignoredBiomes, List<WeightEntry<BiomeContext>> biomes) {
        this(ignoredBiomes, ImmutableList.of(), biomes);
    }

    @Override
    public Codec<WeightedReplaceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        BiomeContext biome = parent.sample(x, y, z);

        if (this.ignoredBiomes.contains(biome.biome()))
            return biome;

        if (!this.whitelistedBiomes.isEmpty() && !this.whitelistedBiomes.contains(biome.biome()))
            return biome;

        return WeightedPicker.getAt(this.biomes, random.nextInt(WeightedPicker.getWeightSum(this.biomes)))
            .orElseThrow().value();
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return this.biomes.stream().map(WeightEntry::value).map(biome -> () -> biomeRegistry.get(biome.biome()));
    }
}
