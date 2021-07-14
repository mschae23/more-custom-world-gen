package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.CrossSamplingBiomeDecorator;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class ArrayBorderingReplaceBiomeDecorator extends CrossSamplingBiomeDecorator {
    public static final Codec<ArrayBorderingReplaceBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RegistryKeys.BIOME_LIST_CODEC.fieldOf("ignored_biomes").forGetter(ArrayBorderingReplaceBiomeDecorator::getIgnoredBiomes),
            BorderingReplaceBiomeEntry.CODEC.listOf().fieldOf("entries").forGetter(ArrayBorderingReplaceBiomeDecorator::getEntries)
        ).apply(instance, instance.stable(ArrayBorderingReplaceBiomeDecorator::new)));

    private final List<Identifier> ignoredBiomes;
    private final List<BorderingReplaceBiomeEntry> entries;

    public ArrayBorderingReplaceBiomeDecorator(List<RegistryKey<Biome>> ignoredBiomes, List<BorderingReplaceBiomeEntry> entries) {
        this.ignoredBiomes = ignoredBiomes.stream().map(RegistryKey::getValue).collect(Collectors.toList());
        this.entries = entries;
    }

    public List<RegistryKey<Biome>> getIgnoredBiomes() {
        return this.ignoredBiomes.stream()
            .map(id -> RegistryKey.of(Registry.BIOME_KEY, id))
            .collect(Collectors.toList());
    }

    public List<BorderingReplaceBiomeEntry> getEntries() {
        return this.entries;
    }

    @Override
    protected Codec<? extends BiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryKey<Biome> getBiome(LayerRandomnessSource random, RegistryKey<Biome> n, RegistryKey<Biome> e, RegistryKey<Biome> s, RegistryKey<Biome> w, RegistryKey<Biome> center) {
        if (this.ignoredBiomes.contains(center.getValue()))
            return center;

        for (var entry : this.entries) {
            Optional<RegistryKey<Biome>> result = entry.sample(random, n, e, s, w, center);

            if (result.isPresent()) {
                return result.get();
            }
        }

        return center;
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return this.entries.stream()
            .flatMap(entry -> entry.getBiomes(biomeRegistry).stream())
            .collect(Collectors.toList());
    }
}
