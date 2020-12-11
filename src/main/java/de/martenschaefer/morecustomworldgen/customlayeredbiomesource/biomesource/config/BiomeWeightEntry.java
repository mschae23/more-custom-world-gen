package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.Objects;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;
import org.jetbrains.annotations.NotNull;

public class BiomeWeightEntry extends WeightedPicker.Entry {
    public static final Codec<BiomeWeightEntry> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(BiomeWeightEntry::getBiome),
            Codec.INT.fieldOf("weight").forGetter(BiomeWeightEntry::getWeight)
        ).apply(instance, instance.stable(BiomeWeightEntry::new))
    );

    private final RegistryKey<Biome> biome;

    public BiomeWeightEntry(@NotNull RegistryKey<Biome> biome, int weight) {
        super(weight);
        this.biome = biome;
    }

    public RegistryKey<Biome> getBiome() {
        return this.biome;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BiomeWeightEntry that = (BiomeWeightEntry) o;
        return this.weight == that.weight &&
            this.biome.equals(that.biome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(biome, weight);
    }
}
