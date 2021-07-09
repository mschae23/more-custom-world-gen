package de.martenschaefer.morecustomworldgen.util;

import java.util.List;
import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class RegistryKeys {
    public static final Codec<RegistryKey<Biome>> BIOME_CODEC = codec(Registry.BIOME_KEY);
    public static final Codec<List<RegistryKey<Biome>>> BIOME_LIST_CODEC = listCodec(Registry.BIOME_KEY);

    public static <T> Codec<RegistryKey<T>> codec(RegistryKey<? extends Registry<T>> registryKey) {
        return Identifier.CODEC.xmap(
            id -> RegistryKey.of(registryKey, id),
            RegistryKey::getValue
        );
    }

    public static <T> Codec<List<RegistryKey<T>>> listCodec(RegistryKey<? extends Registry<T>> registryKey) {
        return Identifier.CODEC.xmap(
            id -> RegistryKey.of(registryKey, id),
            RegistryKey::getValue
        ).listOf();
    }

    public static boolean equals(RegistryKey<?> key1, RegistryKey<?> key2) {
        return key1.getValue().equals(key2.getValue());
    }
}
