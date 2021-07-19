package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import java.util.Random;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.util.Chance;

public record SimpleReplaceBiomeEntry(BiomeContextPredicate comparingBiome, Chance chance, BiomeContext biome) {
    public static final Codec<SimpleReplaceBiomeEntry> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BiomeContextPredicate.CODEC.fieldOf("comparing_biome").forGetter(SimpleReplaceBiomeEntry::comparingBiome),
            Chance.CODEC.fieldOf("chance").forGetter(SimpleReplaceBiomeEntry::chance),
            BiomeContext.CODEC.fieldOf("biome").forGetter(SimpleReplaceBiomeEntry::biome)
        ).apply(instance, instance.stable(SimpleReplaceBiomeEntry::new))
    );

    public boolean test(BiomeContext context, Random random) {
        return this.comparingBiome().test(context, random) && this.chance.get(random);
    }

    public boolean test(BiomeContext context, LayerRandomnessSource random) {
        return this.comparingBiome().test(context, random) && this.chance.get(random);
    }
}
