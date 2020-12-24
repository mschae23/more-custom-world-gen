package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CustomLayeredBiomeSourceConfig {
    public static final Codec<CustomLayeredBiomeSourceConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BiomeCategory.CODEC.listOf().fieldOf("biome_categories").forGetter(CustomLayeredBiomeSourceConfig::getBiomeCategories),
            ContinentConfig.CODEC.fieldOf("continents").forGetter(CustomLayeredBiomeSourceConfig::getContinentConfig),
            ClimateConfig.CODEC.fieldOf("climates").forGetter(CustomLayeredBiomeSourceConfig::getClimateConfig),
            BiomeLayoutConfig.CODEC.fieldOf("biome_layout").forGetter(CustomLayeredBiomeSourceConfig::getBiomeLayout),
            BiomeSizeConfig.CODEC.fieldOf("biome_size").forGetter(CustomLayeredBiomeSourceConfig::getBiomeSizeConfig),
            Codec.STRING.fieldOf("ocean_category").forGetter(CustomLayeredBiomeSourceConfig::getOceanCategory),
            RiverConfig.CODEC.fieldOf("rivers").forGetter(CustomLayeredBiomeSourceConfig::getRivers),
            OceanBiomesConfig.CODEC.fieldOf("ocean_biomes").forGetter(CustomLayeredBiomeSourceConfig::getOceanBiomes)
        ).apply(instance, instance.stable(CustomLayeredBiomeSourceConfig::new)));

    private final List<BiomeCategory> biomeCategories;
    private final ContinentConfig continents;
    private final ClimateConfig climates;
    private final BiomeLayoutConfig biomeLayout;
    private final BiomeSizeConfig biomeSize;
    private final String oceanCategory;
    private final RiverConfig rivers;
    private final OceanBiomesConfig oceanBiomes;

    public CustomLayeredBiomeSourceConfig(List<BiomeCategory> biomeCategories, ContinentConfig continents, ClimateConfig climates, BiomeLayoutConfig biomeLayout, BiomeSizeConfig biomeSize, String oceanCategory, RiverConfig rivers, OceanBiomesConfig oceanBiomes) {
        this.biomeCategories = biomeCategories;
        this.continents = continents;
        this.climates = climates;
        this.biomeLayout = biomeLayout;
        this.biomeSize = biomeSize;
        this.oceanCategory = oceanCategory;
        this.rivers = rivers;
        this.oceanBiomes = oceanBiomes;
    }

    public List<BiomeCategory> getBiomeCategories() {
        return this.biomeCategories;
    }

    public ContinentConfig getContinentConfig() {
        return this.continents;
    }

    public ClimateConfig getClimateConfig() {
        return this.climates;
    }

    public BiomeLayoutConfig getBiomeLayout() {
        return this.biomeLayout;
    }

    public BiomeSizeConfig getBiomeSizeConfig() {
        return this.biomeSize;
    }
    
    public String getOceanCategory() {
        return this.oceanCategory;
    }

    public RiverConfig getRivers() {
        return this.rivers;
    }

    public OceanBiomesConfig getOceanBiomes() {
        return this.oceanBiomes;
    }

    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        List<Supplier<Biome>> biomes = this.getBiomeLayout().getBiomes(biomeRegistry);
        biomes.addAll(this.rivers.getBiomes(biomeRegistry));
        biomes.addAll(this.oceanBiomes.getBiomes(biomeRegistry));
        
        return biomes.stream();
    }
}
