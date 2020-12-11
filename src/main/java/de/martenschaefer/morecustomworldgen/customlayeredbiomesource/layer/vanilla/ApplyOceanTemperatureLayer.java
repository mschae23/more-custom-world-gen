package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import java.util.List;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.OceanCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.OceanBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.MergingLayer;

public class ApplyOceanTemperatureLayer implements MergingLayer<RegistryKey<Biome>, OceanCategory>, IdentityCoordinateTransformer {
    private final List<BiomeCategory> categories;
    private final String oceanCategory;
    private final OceanBiomesConfig config;

    public ApplyOceanTemperatureLayer(List<BiomeCategory> categories, String oceanCategory, OceanBiomesConfig config) {
        this.categories = categories;
        this.oceanCategory = oceanCategory;
        this.config = config;
    }

    @Override
    public RegistryKey<Biome> sample(LayerRandomnessSource context, LayerSampler<RegistryKey<Biome>> parent, LayerSampler<OceanCategory> ocean, int x, int z) {
        RegistryKey<Biome> i = parent.sample(this.transformX(x), this.transformZ(z));
        OceanCategory j = ocean.sample(this.transformX(x), this.transformZ(z));
        if (!this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, i))) {
            return i;
        } else {

            for (int m = -8; m <= 8; m += 4) {
                for (int n = -8; n <= 8; n += 4) {
                    RegistryKey<Biome> o = parent.sample(this.transformX(x + m), this.transformZ(z + n));
                    if (!this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, o))) {
                        if (j == OceanCategory.WARM) {
                            return this.config.getLukewarmOcean();
                        }

                        if (j == OceanCategory.FROZEN) {
                            return this.config.getColdOcean();
                        }
                    }
                }
            }

            if (this.config.getDeepOcean().getValue().equals(i.getValue())) {
                if (j == OceanCategory.LUKEWARM) {
                    return config.getDeepLukewarmOcean();
                }

                if (j == OceanCategory.NORMAL) {
                    return config.getDeepOcean();
                }

                if (j == OceanCategory.COLD) {
                    return config.getDeepColdOcean();
                }

                if (j == OceanCategory.FROZEN) {
                    return config.getDeepFrozenOcean();
                }
            }

            switch (j) {
                case WARM:
                    return config.getWarmOcean();
                case LUKEWARM:
                    return config.getLukewarmOcean();
                case COLD:
                    return config.getColdOcean();
                case FROZEN:
                    return config.getFrozenOcean();
                    
                case NORMAL:
                default:
                    return config.getOcean();
            }
        }
    }
}
