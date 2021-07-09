package de.martenschaefer.morecustomworldgen.biomedecorator.replace;

import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.CachingBiomeDecorator;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;
import de.martenschaefer.morecustomworldgen.util.WeightEntry;

public class WeightedReplaceBiomeDecorator extends BiomeDecorator {
    public static final Codec<WeightedReplaceBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RegistryKeys.BIOME_LIST_CODEC.fieldOf("ignored_biomes").forGetter(WeightedReplaceBiomeDecorator::getIgnoredBiomes),
            RegistryKeys.BIOME_LIST_CODEC.fieldOf("whitelisted_biomes").orElseGet(ImmutableList::of).forGetter(WeightedReplaceBiomeDecorator::getWhitelistedBiomes),
            WeightEntry.createCodec("biome", RegistryKeys.BIOME_CODEC).listOf().fieldOf("biomes").forGetter(WeightedReplaceBiomeDecorator::getBiomes)
        ).apply(instance, instance.stable(WeightedReplaceBiomeDecorator::new))
    );

    private final List<RegistryKey<Biome>> ignoredBiomes;
    private final List<RegistryKey<Biome>> whitelistedBiomes;
    private final List<WeightEntry<RegistryKey<Biome>>> biomes;

    public WeightedReplaceBiomeDecorator(List<RegistryKey<Biome>> ignoredBiomes, List<RegistryKey<Biome>> whitelistedBiomes, List<WeightEntry<RegistryKey<Biome>>> biomes) {
        this.ignoredBiomes = ignoredBiomes;
        this.whitelistedBiomes = whitelistedBiomes;
        this.biomes = biomes;
    }

    public WeightedReplaceBiomeDecorator(List<RegistryKey<Biome>> ignoredBiomes, List<WeightEntry<RegistryKey<Biome>>> biomes) {
        this(ignoredBiomes, ImmutableList.of(), biomes);
    }

    public List<RegistryKey<Biome>> getIgnoredBiomes() {
        return ignoredBiomes;
    }

    public List<RegistryKey<Biome>> getWhitelistedBiomes() {
        return whitelistedBiomes;
    }

    public List<WeightEntry<RegistryKey<Biome>>> getBiomes() {
        return biomes;
    }

    @Override
    protected Codec<WeightedReplaceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        RegistryKey biome = parent.sample(x, y, z);

        if (this.ignoredBiomes.contains(biome))
            return biome;

        if (!this.whitelistedBiomes.isEmpty() && !this.whitelistedBiomes.contains(biome))
            return biome;

        return WeightedPicker.getAt(this.biomes, random.nextInt(WeightedPicker.getWeightSum(this.biomes)))
            .orElseThrow().value();
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return this.biomes.stream().map(WeightEntry::value).map(biomeRegistry::get).collect(Collectors.toList());
    }
}
