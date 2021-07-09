package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import java.util.List;
import java.util.function.LongFunction;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.ContinentCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.OceanCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeLayoutConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.ClimateConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.ContinentConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.OceanBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.RiverConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.BiomeLayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerFactory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.LayerHelper;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.ScaleLayer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.SmoothLayer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.ParentedLayer;

public final class VanillaLayers {
    // Continent layer
    public static <T extends LayerSampler<ContinentCategory>, C extends LayerSampleContext<ContinentCategory, ContinentCategory, ContinentCategory, T, T, T>> LayerFactory<ContinentCategory, T> buildContinentLayerFactory(ContinentConfig config, LongFunction<C> contextProvider) {
        ScaleLayer<ContinentCategory> normalScaleLayer = ScaleLayer.normal();
        ScaleLayer<ContinentCategory> fuzzyScaleLayer = ScaleLayer.fuzzy();

        LayerFactory<ContinentCategory, T> continents = new ContinentLayer(config.getContinentChance(), config.shouldGenerateOriginContinent()).create(contextProvider.apply(1L));
        continents = fuzzyScaleLayer.create(contextProvider.apply(2000L), continents);
        continents = IncreaseEdgeCurvatureContinentLayer.INSTANCE.create(contextProvider.apply(1L), continents);
        continents = normalScaleLayer.create(contextProvider.apply(2001L), continents);
        continents = IncreaseEdgeCurvatureContinentLayer.INSTANCE.create(contextProvider.apply(2L), continents);
        continents = IncreaseEdgeCurvatureContinentLayer.INSTANCE.create(contextProvider.apply(50L), continents);
        continents = IncreaseEdgeCurvatureContinentLayer.INSTANCE.create(contextProvider.apply(70L), continents);
        continents = new AddIslandLayer(config.getIslandChance()).create(contextProvider.apply(2L), continents);

        return continents;
    }

    public static BiomeLayerSampler<ContinentCategory> buildContinentLayer(long seed, ContinentConfig config) {
        return LayerHelper.<ContinentCategory, ContinentCategory, ContinentCategory>createLayerSampler(seed,
            contextProvider -> buildContinentLayerFactory(config, contextProvider));
    }

    // Climate layer
    public static <T extends LayerSampler<ClimateCategory>, T2 extends LayerSampler<ContinentCategory>, C extends LayerSampleContext<ClimateCategory, ClimateCategory, ClimateCategory, T, T, T>, C2 extends LayerSampleContext<ClimateCategory, ContinentCategory, ContinentCategory, T, T2, T2>> LayerFactory<ClimateCategory, T> buildClimateLayerFactory(LayerFactory<ContinentCategory, T2> continentLayer, LongFunction<C2> convertingContextProvider, ClimateConfig config, LongFunction<C> contextProvider) {
        ScaleLayer<ClimateCategory> normalScaleLayer = ScaleLayer.normal();

        LayerFactory<ClimateCategory, T> climates = new AddBaseClimatesLayer(config).create(convertingContextProvider.apply(2L), continentLayer);
        climates = IncreaseEdgeCurvatureClimateLayer.INSTANCE.create(contextProvider.apply(3L), climates);
        climates = AddClimateLayers.AddTemperateBiomesLayer.INSTANCE.create(contextProvider.apply(2L), climates);
        climates = AddClimateLayers.AddCoolBiomesLayer.INSTANCE.create(contextProvider.apply(2L), climates);
        climates = AddClimateLayers.AddSpecialBiomesLayer.INSTANCE.create(contextProvider.apply(3L), climates);
        climates = stack(2002L, normalScaleLayer, climates, config.getClimateSize(), contextProvider);
        climates = IncreaseEdgeCurvatureClimateLayer.INSTANCE.create(contextProvider.apply(4L), climates);
        climates = new AddRareIslandLayer(config.getRareIslandChance()).create(contextProvider.apply(5L), climates);
        climates = AddDeepOceanLayer.INSTANCE.create(contextProvider.apply(4L), climates);

        climates = stack(1000L, normalScaleLayer, climates, 0, contextProvider);

        return climates;
    }

    public static BiomeLayerSampler<ClimateCategory> buildClimateLayer(long seed, BiomeLayerSampler<ContinentCategory> continentLayer, ClimateConfig config) {
        return LayerHelper.<ClimateCategory, ClimateCategory, ClimateCategory>createLayerSampler(seed,
            contextProvider -> buildClimateLayerFactory(continentLayer::getSampler, LayerHelper.createContextProvider(seed), config, contextProvider));
    }

    // Noise layer
    public static <T extends LayerSampler<Integer>, T2 extends LayerSampler<ClimateCategory>, C2 extends LayerSampleContext<Integer, ClimateCategory, ClimateCategory, T, T2, T2>> LayerFactory<Integer, T> buildNoiseLayerFactory(LayerFactory<ClimateCategory, T2> climateLayer, LongFunction<C2> convertingContextProvider) {
        return SimpleLandNoiseLayer.INSTANCE.create(convertingContextProvider.apply(100L), climateLayer);
    }

    public static BiomeLayerSampler<Integer> buildNoiseLayer(long seed, BiomeLayerSampler<ClimateCategory> climateLayer) {
        return LayerHelper.<Integer, Integer, Integer>createLayerSampler(seed,
            contextProvider -> buildNoiseLayerFactory(climateLayer::getSampler, LayerHelper.createContextProvider(seed)));
    }

    // Hill layer
    public static <T extends LayerSampler<Integer>, C extends LayerSampleContext<Integer, Integer, Integer, T, T, T>> LayerFactory<Integer, T> buildHillLayerFactory(LayerFactory<Integer, T> noiseLayer, LongFunction<C> contextProvider) {
        ScaleLayer<Integer> normalScaleLayer = ScaleLayer.normal();

        return stack(1000L, normalScaleLayer, noiseLayer, 2, contextProvider);
    }

    public static BiomeLayerSampler<Integer> buildHillLayer(long seed, BiomeLayerSampler<Integer> noiseLayer) {
        return LayerHelper.<Integer, Integer, Integer>createLayerSampler(seed,
            contextProvider -> buildHillLayerFactory(noiseLayer::getSampler, contextProvider));
    }

    // Biome layout layer
    public static <T extends LayerSampler<RegistryKey<Biome>>, T2 extends LayerSampler<ClimateCategory>, T3 extends LayerSampler<Integer>, C extends LayerSampleContext<RegistryKey<Biome>, RegistryKey<Biome>, RegistryKey<Biome>, T, T, T>, C2 extends LayerSampleContext<RegistryKey<Biome>, ClimateCategory, ClimateCategory, T, T2, T2>, C3 extends LayerSampleContext<RegistryKey<Biome>, RegistryKey<Biome>, Integer, T, T, T3>> LayerFactory<RegistryKey<Biome>, T> buildBiomeLayoutLayerFactory(LayerFactory<ClimateCategory, T2> climateLayer, LayerFactory<Integer, T3> hillLayer, List<BiomeCategory> categories, OceanBiomesConfig oceanBiomes, BiomeLayoutConfig config, int biomeScale, String oceanCategory, LongFunction<C2> convertingContextProvider, LongFunction<C3> mergingNoiseContextProvider, LongFunction<C> contextProvider) {
        ScaleLayer<RegistryKey<Biome>> normalScaleLayer = ScaleLayer.normal();

        LayerFactory<RegistryKey<Biome>, T> biomes = new SetBaseBiomesLayer(config.getBaseBiomes(), oceanBiomes).create(convertingContextProvider.apply(200L), climateLayer);
        biomes = new AddInnerBiomesLayer(config.getLargeInnerBiomes()).create(contextProvider.apply(1001L), biomes);
        biomes = stack(1000L, normalScaleLayer, biomes, 2, contextProvider);
        biomes = new AddEdgeBiomesLayer(categories, config.getBiomeEdges()).create(contextProvider.apply(1000L), biomes);
        biomes = new AddHillsLayer(categories, config.getHillBiomes()).create(mergingNoiseContextProvider.apply(1000L), biomes, hillLayer);
        biomes = new AddInnerBiomesLayer(config.getSpotInnerBiomes()).create(contextProvider.apply(1001L), biomes);

        for (int i = 0; i < biomeScale; i++) {
            biomes = normalScaleLayer.create(contextProvider.apply((1000 + i)), biomes);
            if (i == 0) {
                biomes = new IncreaseEdgeCurvatureBiomeLayer(config.getShallowOceanBiomes(), config.getForestBiome(), config.getPlainsBiome()).create(contextProvider.apply(3L), biomes);
            }

            if (i == 1 || biomeScale == 1) {
                biomes = new AddShoresLayer(categories, oceanCategory, config.getShoreBiomes()).create(contextProvider.apply(1000L), biomes);
            }
        }

        biomes = new SmoothLayer<RegistryKey<Biome>>().create(contextProvider.apply(1000L), biomes);

        return biomes;
    }

    public static BiomeLayerSampler<RegistryKey<Biome>> buildBiomeLayoutLayer(long seed, BiomeLayerSampler<ClimateCategory> climateLayer, BiomeLayerSampler<Integer> hillLayer, List<BiomeCategory> categories, OceanBiomesConfig oceanBiomes, BiomeLayoutConfig config, int biomeScale, String oceanCategory) {
        return LayerHelper.<RegistryKey<Biome>, RegistryKey<Biome>, RegistryKey<Biome>>createLayerSampler(seed,
            contextProvider -> buildBiomeLayoutLayerFactory(climateLayer::getSampler, hillLayer::getSampler, categories, oceanBiomes, config, biomeScale, oceanCategory, LayerHelper.createContextProvider(seed), LayerHelper.createContextProvider(seed), contextProvider));
    }

    // Biome layer
    public static <T extends LayerSampler<RegistryKey<Biome>>, T2 extends LayerSampler<Integer>, T3 extends LayerSampler<OceanCategory>, C extends LayerSampleContext<RegistryKey<Biome>, RegistryKey<Biome>, RegistryKey<Biome>, T, T, T>, C2 extends LayerSampleContext<RegistryKey<Biome>, RegistryKey<Biome>, Integer, T, T, T2>, C3 extends LayerSampleContext<RegistryKey<Biome>, RegistryKey<Biome>, OceanCategory, T, T, T3>> LayerFactory<RegistryKey<Biome>, T> buildBiomeLayerFactory(LayerFactory<RegistryKey<Biome>, T> biomeLayoutLayer, LayerFactory<Integer, T2> riverLayer, LayerFactory<OceanCategory, T3> oceanLayer, List<BiomeCategory> categories, RiverConfig riverConfig, int biomeAndRiverScale, String oceanCategory, OceanBiomesConfig oceanBiomes, LongFunction<C2> riverMergingContextProvider, LongFunction<C3> oceanMergingContextProvider, LongFunction<C> contextProvider) {
        ScaleLayer<RegistryKey<Biome>> normalScaleLayer = ScaleLayer.normal();

        LayerFactory<RegistryKey<Biome>, T> biomes = biomeLayoutLayer;

        if (riverConfig.shouldGenerateRivers())
            biomes = new AddRiversLayer(categories, oceanCategory, riverConfig).create(riverMergingContextProvider.apply(100L), biomes, riverLayer);
        biomes = stack(1000L, normalScaleLayer, biomes, biomeAndRiverScale, contextProvider);

        if (oceanBiomes.shouldApplyOceanTemperatures())
            biomes = new ApplyOceanTemperatureLayer(categories, oceanCategory, oceanBiomes).create(oceanMergingContextProvider.apply(100L), biomes, oceanLayer);

        return biomes;
    }

    public static BiomeLayerSampler<RegistryKey<Biome>> buildBiomeLayer(long seed, BiomeLayerSampler<RegistryKey<Biome>> biomeLayoutLayer, BiomeLayerSampler<Integer> riverLayer, BiomeLayerSampler<OceanCategory> oceanLayer, List<BiomeCategory> categories, RiverConfig riverConfig, int biomeAndRiverScale, String oceanCategory, OceanBiomesConfig oceanBiomes) {
        return LayerHelper.<RegistryKey<Biome>, RegistryKey<Biome>, RegistryKey<Biome>>createLayerSampler(seed,
            contextProvider -> buildBiomeLayerFactory(biomeLayoutLayer::getSampler, riverLayer::getSampler, oceanLayer::getSampler, categories, riverConfig, biomeAndRiverScale, oceanCategory, oceanBiomes, LayerHelper.createContextProvider(seed), LayerHelper.createContextProvider(seed), contextProvider));
    }

    // River layer
    public static <T extends LayerSampler<Integer>, C extends LayerSampleContext<Integer, Integer, Integer, T, T, T>> LayerFactory<Integer, T> buildRiverLayerFactory(LayerFactory<Integer, T> noiseLayer, int riverSize, LongFunction<C> contextProvider) {
        ScaleLayer<Integer> normalScaleLayer = ScaleLayer.normal();

        LayerFactory<Integer, T> rivers = stack(1000L, normalScaleLayer, noiseLayer, 2, contextProvider);
        rivers = stack(1000L, normalScaleLayer, rivers, riverSize, contextProvider);
        rivers = NoiseToRiverLayer.INSTANCE.create(contextProvider.apply(1L), rivers);
        rivers = new SmoothLayer<Integer>().create(contextProvider.apply(1000L), rivers);

        return rivers;
    }

    public static BiomeLayerSampler<Integer> buildRiverLayer(long seed, BiomeLayerSampler<Integer> noiseLayer, int riverSize) {
        return LayerHelper.<Integer, Integer, Integer>createLayerSampler(seed,
            contextProvider -> buildRiverLayerFactory(noiseLayer::getSampler, riverSize, contextProvider));
    }

    // Ocean layer
    public static <T extends LayerSampler<OceanCategory>, C extends LayerSampleContext<OceanCategory, OceanCategory, OceanCategory, T, T, T>> LayerFactory<OceanCategory, T> buildOceanLayerFactory(int oceanClimateSize, LongFunction<C> contextProvider) {
        ScaleLayer<OceanCategory> normalScaleLayer = ScaleLayer.normal();

        LayerFactory<OceanCategory, T> ocean = OceanTemperatureLayer.INSTANCE.create(contextProvider.apply(1L));
        ocean = stack(2001L, normalScaleLayer, ocean, oceanClimateSize, contextProvider);

        return ocean;
    }

    public static BiomeLayerSampler<OceanCategory> buildOceanLayer(long seed, int oceanClimateSize) {
        return LayerHelper.<OceanCategory, OceanCategory, OceanCategory>createLayerSampler(seed,
            contextProvider -> buildOceanLayerFactory(oceanClimateSize, contextProvider));
    }

    private static <T, S extends LayerSampler<T>, C extends LayerSampleContext<T, T, T, S, S, S>> LayerFactory<T, S> stack(long salt, ParentedLayer<T> layer, LayerFactory<T, S> parent, int count, LongFunction<C> contextProvider) {
        LayerFactory<T, S> layerFactory = parent;

        for (int i = 0; i < count; i++) {
            layerFactory = layer.create(contextProvider.apply(salt + (long) i), layerFactory);
        }

        return layerFactory;
    }
}
