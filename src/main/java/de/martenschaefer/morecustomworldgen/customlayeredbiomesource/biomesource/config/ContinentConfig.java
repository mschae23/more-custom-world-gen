package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ContinentConfig {
    public static final Codec<ContinentConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("continent_chance").forGetter(ContinentConfig::getContinentChance),
            Codec.INT.fieldOf("island_chance").forGetter(ContinentConfig::getContinentChance)
        ).apply(instance, instance.stable(ContinentConfig::new))
    );
    
    private final int continentChance;
    private final int islandChance;
    
    public ContinentConfig(int continentChance, int islandChance) {
        this.continentChance = continentChance;
        this.islandChance = islandChance;
    }
    
    public int getContinentChance() {
        return this.continentChance;
    }

    public int getIslandChance() {
        return this.islandChance;
    }
}
