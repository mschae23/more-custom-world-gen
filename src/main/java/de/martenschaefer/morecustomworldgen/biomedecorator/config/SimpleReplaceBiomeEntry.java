package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public record SimpleReplaceBiomeEntry(RegistryKey<Biome> comparingBiome, Chance chance, BiomeContext biome) {
    public static final Codec<SimpleReplaceBiomeEntry> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RegistryKeys.BIOME_CODEC.fieldOf("comparing_biome").forGetter(SimpleReplaceBiomeEntry::comparingBiome),
            Chance.CODEC.fieldOf("chance").forGetter(SimpleReplaceBiomeEntry::chance),
            BiomeContext.CODEC.fieldOf("biome").forGetter(SimpleReplaceBiomeEntry::biome)
        ).apply(instance, instance.stable(SimpleReplaceBiomeEntry::new))
    );
}
