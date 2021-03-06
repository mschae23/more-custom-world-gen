package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import java.util.List;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class BiomeSetEntry {
    public static final Codec<Either<RegistryKey<Biome>, List<RegistryKey<Biome>>>> CODEC = Codec.either(RegistryKeys.BIOME_CODEC, RegistryKeys.BIOME_LIST_CODEC);

    public static boolean contains(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> biomeSetEntry, RegistryKey<Biome> comparingBiome) {
        return biomeSetEntry.map(biome -> RegistryKeys.equals(biome, comparingBiome),
            list -> list.stream().map(RegistryKey::getValue).anyMatch(biome -> biome.equals(comparingBiome.getValue())));
    }

    public static boolean isEmpty(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> biomeSetEntry) {
        return biomeSetEntry.map(biome -> false, List::isEmpty);
    }
}
