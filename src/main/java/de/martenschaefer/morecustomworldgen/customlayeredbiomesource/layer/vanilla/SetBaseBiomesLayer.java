package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import java.util.List;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BaseBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeWeightEntry;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.OceanBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.ConvertingLayer;

public class SetBaseBiomesLayer implements ConvertingLayer<RegistryKey<Biome>, ClimateCategory>, IdentityCoordinateTransformer {
    private final BaseBiomesConfig config;
    private final OceanBiomesConfig oceanBiomes;

    public SetBaseBiomesLayer(BaseBiomesConfig config, OceanBiomesConfig oceanBiomes) {
        this.config = config;
        this.oceanBiomes = oceanBiomes;

        if (this.config.getDryBiomes().size() == 0) throw new IllegalArgumentException("No dry biomes");
        if (this.config.getTemperateBiomes().size() == 0) throw new IllegalArgumentException("No temperate biomes");
        if (this.config.getCoolBiomes().size() == 0) throw new IllegalArgumentException("No cool biomes");
        if (this.config.getSnowyBiomes().size() == 0) throw new IllegalArgumentException("No snowy biomes");
        if (this.config.getRareIslandBiomes().size() == 0) throw new IllegalArgumentException("No rare island biomes");
    }

    @Override
    public RegistryKey<Biome> sample(LayerSampleContext<RegistryKey<Biome>, ClimateCategory, ClimateCategory, ?, ?, ?> context, LayerSampler<ClimateCategory> parent, int x, int z) {
        ClimateCategory climate = parent.sample(this.transformX(x), this.transformZ(z));

        switch (climate) {
            case SPECIAL_DRY:
                if (this.config.getSpecialDryBiomes().size() > 0) {
                    return pick(context, this.config.getSpecialDryBiomes());
                }
            case DRY:
                return pick(context, this.config.getDryBiomes());
            case SPECIAL_TEMPERATE:
                if (this.config.getSpecialTemperateBiomes().size() > 0) {
                    return pick(context, this.config.getSpecialTemperateBiomes());
                }
            case TEMPERATE:
                return pick(context, this.config.getTemperateBiomes());
            case SPECIAL_COOL:
                if (this.config.getSpecialCoolBiomes().size() > 0) {
                    return pick(context, this.config.getSpecialCoolBiomes());
                }
            case COOL:
                return pick(context, this.config.getCoolBiomes());
            case SPECIAL_SNOWY:
                if (this.config.getSpecialSnowyBiomes().size() > 0) {
                    return pick(context, this.config.getSpecialSnowyBiomes());
                }
            case SNOWY:
                return pick(context, this.config.getSnowyBiomes());
            case RARE_ISLAND:
                return pick(context, this.config.getRareIslandBiomes());

            case OCEAN:
                return this.oceanBiomes.getOcean();
            case DEEP_OCEAN:
                return this.oceanBiomes.getDeepOcean();

            default:
                return this.config.getDefaultLandBiome();
        }
    }

    private static RegistryKey<Biome> pick(LayerSampleContext<RegistryKey<Biome>, ClimateCategory, ClimateCategory, ?, ?, ?> context, List<BiomeWeightEntry> biomes) {
        return WeightedPicker.getAt(biomes, context.nextInt(WeightedPicker.getWeightSum(biomes))).getBiome();
    }
}
