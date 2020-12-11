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
            ContinentConfig.CODEC.fieldOf("continents").forGetter(CustomLayeredBiomeSourceConfig::getContinents),
            BiomeLayoutConfig.CODEC.fieldOf("biome_layout").forGetter(CustomLayeredBiomeSourceConfig::getBiomeLayout),
            Codec.STRING.fieldOf("ocean_category").forGetter(CustomLayeredBiomeSourceConfig::getOceanCategory),
            RiverConfig.CODEC.fieldOf("rivers").forGetter(CustomLayeredBiomeSourceConfig::getRivers),
            OceanBiomesConfig.CODEC.fieldOf("ocean_biomes").forGetter(CustomLayeredBiomeSourceConfig::getOceanBiomes)
        ).apply(instance, instance.stable(CustomLayeredBiomeSourceConfig::new)));

    private final List<BiomeCategory> biomeCategories;
    private final ContinentConfig continents;
    private final BiomeLayoutConfig biomeLayout;
    private final String oceanCategory;
    private final RiverConfig rivers;
    private final OceanBiomesConfig oceanBiomes;

    public CustomLayeredBiomeSourceConfig(List<BiomeCategory> biomeCategories, ContinentConfig continents, BiomeLayoutConfig biomeLayout, String oceanCategory, RiverConfig rivers, OceanBiomesConfig oceanBiomes) {
        this.biomeCategories = biomeCategories;
        this.continents = continents;
        this.biomeLayout = biomeLayout;
        this.oceanCategory = oceanCategory;
        this.rivers = rivers;
        this.oceanBiomes = oceanBiomes;
    }

    public List<BiomeCategory> getBiomeCategories() {
        return this.biomeCategories;
    }

    public ContinentConfig getContinents() {
        return this.continents;
    }

    public BiomeLayoutConfig getBiomeLayout() {
        return this.biomeLayout;
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
