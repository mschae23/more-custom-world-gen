package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class BiomeSizeConfig {
    public static final Codec<BiomeSizeConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("biome_scale").forGetter(BiomeSizeConfig::getBiomeScale),
            Codec.INT.fieldOf("biome_and_river_scale").forGetter(BiomeSizeConfig::getBiomeAndRiverScale),
            Codec.INT.fieldOf("ocean_climate_size").forGetter(BiomeSizeConfig::getOceanClimateSize)
        ).apply(instance, instance.stable(BiomeSizeConfig::new))
    );

    private final int biomeScale;
    private final int biomeAndRiverScale;
    private final int oceanClimateSize;

    public BiomeSizeConfig(int biomeScale, int biomeAndRiverScale, int oceanClimateSize) {
        this.biomeScale = biomeScale;
        this.biomeAndRiverScale = biomeAndRiverScale;
        this.oceanClimateSize = oceanClimateSize;
    }

    public int getBiomeScale() {
        return this.biomeScale;
    }

    public int getBiomeAndRiverScale() {
        return this.biomeAndRiverScale;
    }

    public int getOceanClimateSize() {
        return this.oceanClimateSize;
    }
}
