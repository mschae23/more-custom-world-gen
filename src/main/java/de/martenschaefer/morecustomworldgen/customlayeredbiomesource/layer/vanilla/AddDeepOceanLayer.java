package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.CrossSamplingLayer;

public enum AddDeepOceanLayer implements CrossSamplingLayer<ClimateCategory> {
    INSTANCE;

    @Override
    public ClimateCategory sample(LayerRandomnessSource context, ClimateCategory n, ClimateCategory e, ClimateCategory s, ClimateCategory w, ClimateCategory center) {
        if (ClimateCategory.isShallowOcean(center)) {
            int i = 0;
            if (ClimateCategory.isShallowOcean(n)) {
                ++i;
            }

            if (ClimateCategory.isShallowOcean(e)) {
                ++i;
            }

            if (ClimateCategory.isShallowOcean(w)) {
                ++i;
            }

            if (ClimateCategory.isShallowOcean(s)) {
                ++i;
            }

            if (i > 3) {
                if (center == ClimateCategory.OCEAN) {
                    return ClimateCategory.DEEP_OCEAN;
                }

                return ClimateCategory.DEEP_OCEAN;
            }
        }

        return center;
    }
}
