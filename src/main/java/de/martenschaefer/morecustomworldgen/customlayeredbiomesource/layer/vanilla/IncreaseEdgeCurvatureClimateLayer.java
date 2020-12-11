package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.DiagonalCrossSamplingLayer;

public enum IncreaseEdgeCurvatureClimateLayer implements DiagonalCrossSamplingLayer<ClimateCategory> {
    INSTANCE;

    @Override
    public ClimateCategory sample(LayerRandomnessSource context, ClimateCategory sw, ClimateCategory se, ClimateCategory ne, ClimateCategory nw, ClimateCategory center) {
        if (!ClimateCategory.isShallowOcean(center) || ClimateCategory.isShallowOcean(nw) && ClimateCategory.isShallowOcean(ne) && ClimateCategory.isShallowOcean(sw) && ClimateCategory.isShallowOcean(se)) {
            if (!ClimateCategory.isShallowOcean(center) && (ClimateCategory.isShallowOcean(nw) || ClimateCategory.isShallowOcean(sw) || ClimateCategory.isShallowOcean(ne) || ClimateCategory.isShallowOcean(se)) && context.nextInt(5) == 0) {
                if (ClimateCategory.isShallowOcean(nw)) {
                    return center == ClimateCategory.TEMPERATE ? ClimateCategory.TEMPERATE : nw;
                }

                if (ClimateCategory.isShallowOcean(sw)) {
                    return center == ClimateCategory.TEMPERATE ? ClimateCategory.TEMPERATE : sw;
                }

                if (ClimateCategory.isShallowOcean(ne)) {
                    return center == ClimateCategory.TEMPERATE ? ClimateCategory.TEMPERATE : ne;
                }

                if (ClimateCategory.isShallowOcean(se)) {
                    return center == ClimateCategory.TEMPERATE ? ClimateCategory.TEMPERATE : se;
                }
            }

            return center;
        } else {
            int i = 1;
            ClimateCategory j = ClimateCategory.DRY;
            if (!ClimateCategory.isShallowOcean(nw) && context.nextInt(i++) == 0) {
                j = nw;
            }

            if (!ClimateCategory.isShallowOcean(ne) && context.nextInt(i++) == 0) {
                j = ne;
            }

            if (!ClimateCategory.isShallowOcean(sw) && context.nextInt(i++) == 0) {
                j = sw;
            }

            if (!ClimateCategory.isShallowOcean(se) && context.nextInt(i++) == 0) {
                j = se;
            }

            if (context.nextInt(3) == 0) {
                return j;
            } else {
                return j == ClimateCategory.TEMPERATE ? ClimateCategory.TEMPERATE : center;
            }
        }
    }
}
