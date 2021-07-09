package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import java.util.List;
import java.util.stream.Stream;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;
import de.martenschaefer.morecustomworldgen.util.WeightEntry;

public record WeightedReplaceBiomeEntry(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> comparingBiomes,
                                        List<WeightEntry<RegistryKey<Biome>>> biomes) {
    public static final Codec<WeightedReplaceBiomeEntry> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BiomeSetEntry.CODEC.fieldOf("comparing_biomes").forGetter(WeightedReplaceBiomeEntry::comparingBiomes),
            WeightEntry.createCodec("biome", RegistryKeys.BIOME_CODEC).listOf().fieldOf("biomes")
                .forGetter(WeightedReplaceBiomeEntry::biomes)
        ).apply(instance, instance.stable(WeightedReplaceBiomeEntry::new))
    );

    public Stream<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return this.biomes.stream()
            .map(WeightEntry::value)
            .map(biomeRegistry::get);
    }
}
