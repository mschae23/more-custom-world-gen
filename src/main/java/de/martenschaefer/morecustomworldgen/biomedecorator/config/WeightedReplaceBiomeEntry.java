package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.util.WeightEntry;

public record WeightedReplaceBiomeEntry(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> comparingBiomes,
                                        List<WeightEntry<BiomeContext>> biomes) {
    public static final Codec<WeightedReplaceBiomeEntry> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BiomeSetEntry.CODEC.fieldOf("comparing_biomes").forGetter(WeightedReplaceBiomeEntry::comparingBiomes),
            WeightEntry.createCodec("biome", BiomeContext.CODEC).listOf().fieldOf("biomes")
                .forGetter(WeightedReplaceBiomeEntry::biomes)
        ).apply(instance, instance.stable(WeightedReplaceBiomeEntry::new))
    );

    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return this.biomes.stream()
            .map(WeightEntry::value)
            .map(biome -> () -> biomeRegistry.get(biome.biome()));
    }
}
