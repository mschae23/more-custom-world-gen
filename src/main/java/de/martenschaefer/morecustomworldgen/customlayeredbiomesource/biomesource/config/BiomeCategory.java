package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.List;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class BiomeCategory {
    public static final Codec<BiomeCategory> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(BiomeCategory::getBiome),
            Codec.STRING.fieldOf("category").forGetter(BiomeCategory::getCategory)
        ).apply(instance, instance.stable(BiomeCategory::new))
    );

    private final RegistryKey<Biome> biome;
    private final String category;

    public BiomeCategory(RegistryKey<Biome> biome, @NotNull String category) {
        this.biome = biome;
        this.category = category;
    }

    public RegistryKey<Biome> getBiome() {
        return this.biome;
    }

    public String getCategory() {
        return this.category;
    }

    public static String getCategory(List<BiomeCategory> categories, RegistryKey<Biome> biome) {
        return categories.stream()
            .filter(entry -> entry.getBiome().getValue().equals(biome.getValue()))
            .findAny()
            .map(BiomeCategory::getCategory)
            .orElse(null);
    }
}
