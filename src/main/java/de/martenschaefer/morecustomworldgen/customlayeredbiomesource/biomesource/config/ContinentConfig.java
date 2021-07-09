package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ContinentConfig {
    public static final Codec<ContinentConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("continent_chance").forGetter(ContinentConfig::getContinentChance),
            Codec.BOOL.fieldOf("origin_continent").orElse(true).forGetter(ContinentConfig::shouldGenerateOriginContinent),
            Codec.INT.fieldOf("island_chance").forGetter(ContinentConfig::getIslandChance)
        ).apply(instance, instance.stable(ContinentConfig::new))
    );

    private final int continentChance;
    private final boolean originContinent;
    private final int islandChance;

    public ContinentConfig(int continentChance, boolean originContinent, int islandChance) {
        this.continentChance = continentChance;
        this.originContinent = originContinent;
        this.islandChance = islandChance;
    }

    public int getContinentChance() {
        return this.continentChance;
    }

    public boolean shouldGenerateOriginContinent() {
        return this.originContinent;
    }

    public int getIslandChance() {
        return this.islandChance;
    }
}
