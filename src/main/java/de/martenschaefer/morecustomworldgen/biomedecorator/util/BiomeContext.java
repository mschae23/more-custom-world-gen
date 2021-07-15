package de.martenschaefer.morecustomworldgen.biomedecorator.util;

import java.util.Objects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public record BiomeContext(RegistryKey<Biome> biome, double temperature, double humidity,
                           double continentalness, double erosion, double weirdness) {
    public static final Codec<BiomeContext> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(BiomeContext::biome),
        Codec.DOUBLE.fieldOf("temperature").forGetter(BiomeContext::temperature),
        Codec.DOUBLE.fieldOf("humidity").forGetter(BiomeContext::humidity),
        Codec.DOUBLE.fieldOf("continentalness").forGetter(BiomeContext::continentalness),
        Codec.DOUBLE.fieldOf("erosion").forGetter(BiomeContext::erosion),
        Codec.DOUBLE.fieldOf("weirdness").forGetter(BiomeContext::weirdness)
    ).apply(instance, instance.stable(BiomeContext::new)));

    public BiomeContext withBiome(RegistryKey<Biome> biome) {
        return new BiomeContext(biome, this.temperature, this.humidity, this.continentalness, this.erosion, this.weirdness);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BiomeContext) obj;
        return Objects.equals(this.biome.getValue(), that.biome.getValue()) &&
            Double.doubleToLongBits(this.temperature) == Double.doubleToLongBits(that.temperature) &&
            Double.doubleToLongBits(this.humidity) == Double.doubleToLongBits(that.humidity) &&
            Double.doubleToLongBits(this.continentalness) == Double.doubleToLongBits(that.continentalness) &&
            Double.doubleToLongBits(this.erosion) == Double.doubleToLongBits(that.erosion) &&
            Double.doubleToLongBits(this.weirdness) == Double.doubleToLongBits(that.weirdness);
    }

}
