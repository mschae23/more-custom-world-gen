package de.martenschaefer.morecustomworldgen.biomedecorator.util;

import java.util.Objects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.ParameterType;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public record BiomeContext(RegistryKey<Biome> biome, double temperature, double humidity,
                           double continentalness, double erosion, double weirdness,
                           double offset, double factor) {
    public static final Codec<BiomeContext> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(BiomeContext::biome),
        Codec.DOUBLE.fieldOf("temperature").forGetter(BiomeContext::temperature),
        Codec.DOUBLE.fieldOf("humidity").forGetter(BiomeContext::humidity),
        Codec.DOUBLE.fieldOf("continentalness").forGetter(BiomeContext::continentalness),
        Codec.DOUBLE.fieldOf("erosion").forGetter(BiomeContext::erosion),
        Codec.DOUBLE.fieldOf("weirdness").forGetter(BiomeContext::weirdness),
        Codec.DOUBLE.fieldOf("offset").forGetter(BiomeContext::offset),
        Codec.DOUBLE.fieldOf("factor").forGetter(BiomeContext::factor)
    ).apply(instance, instance.stable(BiomeContext::new)));

    public BiomeContext(RegistryKey<Biome> biome, double offset, double factor) {
        this(biome, 0, 0, 0, 0, 0, offset, factor);
    }

    public double getParameter(ParameterType type) {
        return switch(type) {
            case TEMPERATURE -> this.temperature;
            case HUMIDITY -> this.humidity;
            case CONTINENTALNESS -> this.continentalness;
            case EROSION -> this.erosion;
            case WEIRDNESS -> this.weirdness;
            case OFFSET -> this.offset;
            case FACTOR -> this.factor;
        };
    }

    public BiomeContext withBiome(RegistryKey<Biome> biome) {
        return new BiomeContext(biome, this.temperature, this.humidity, this.continentalness, this.erosion, this.weirdness, this.offset, this.factor);
    }

    public BiomeContext withOffset(double offset) {
        return new BiomeContext(this.biome, this.temperature, this.humidity, this.continentalness, this.erosion, this.weirdness, offset, this.factor);
    }

    public BiomeContext withFactor(double factor) {
        return new BiomeContext(this.biome, this.temperature, this.humidity, this.continentalness, this.erosion, this.weirdness, this.offset, factor);
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
            Double.doubleToLongBits(this.weirdness) == Double.doubleToLongBits(that.weirdness) &&
            Double.doubleToLongBits(this.offset) == Double.doubleToLongBits(that.offset) &&
            Double.doubleToLongBits(this.factor) == Double.doubleToLongBits(that.factor);
    }
}
