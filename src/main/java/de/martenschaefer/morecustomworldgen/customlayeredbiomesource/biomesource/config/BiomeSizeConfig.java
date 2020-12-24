package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class BiomeSizeConfig {
    public static final Codec<BiomeSizeConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("biome_size").forGetter(BiomeSizeConfig::getBiomeSize),
            Codec.INT.fieldOf("river_size").forGetter(BiomeSizeConfig::getRiverSize),
            Codec.INT.fieldOf("ocean_climate_size").forGetter(BiomeSizeConfig::getOceanClimateSize)
        ).apply(instance, instance.stable(BiomeSizeConfig::new))
    );

    private final int biomeSize;
    private final int riverSize;
    private final int oceanClimateSize;

    public BiomeSizeConfig(int biomeSize, int riverSize, int oceanClimateSize) {
        this.biomeSize = biomeSize;
        this.riverSize = riverSize;
        this.oceanClimateSize = oceanClimateSize;
    }

    public int getBiomeSize() {
        return this.biomeSize;
    }

    public int getRiverSize() {
        return this.riverSize;
    }

    public int getOceanClimateSize() {
        return this.oceanClimateSize;
    }
}
