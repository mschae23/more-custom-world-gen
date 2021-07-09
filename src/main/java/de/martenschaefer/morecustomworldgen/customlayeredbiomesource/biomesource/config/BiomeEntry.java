package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.Optional;
import java.util.function.Function;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class BiomeEntry {
    public static final MapCodec<BiomeEntry> BIOME_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(BiomeEntry::getBiome)
        ).apply(instance, BiomeEntry::new)
    );

    public static final MapCodec<BiomeEntry> CATEGORY_CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Codec.STRING.fieldOf("category").forGetter(BiomeEntry::getCategory)
        ).apply(instance, BiomeEntry::new)
    );

    public static final Codec<BiomeEntry> CODEC = Codec.mapEither(BIOME_CODEC, CATEGORY_CODEC).xmap(
        either -> either.map(Function.identity(), Function.identity()),
        entry -> Optional.ofNullable(entry.biome).<Either<BiomeEntry, BiomeEntry>>map(biome -> Either.left(entry)).orElseGet(() -> Either.right(entry))).codec();

    private final RegistryKey<Biome> biome;
    private final String category;

    public BiomeEntry(RegistryKey<Biome> biome) {
        this.biome = biome;
        this.category = null;
    }

    public BiomeEntry(String category) {
        this.biome = null;
        this.category = category;
    }

    public RegistryKey<Biome> getBiome() {
        return this.biome;
    }

    public String getCategory() {
        return this.category;
    }
}
