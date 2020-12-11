package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.DiagonalCrossSamplingLayer;

public enum AddRareIslandLayer implements DiagonalCrossSamplingLayer<ClimateCategory> {
    INSTANCE;

    @Override
    public ClimateCategory sample(LayerRandomnessSource context, ClimateCategory sw, ClimateCategory se, ClimateCategory ne, ClimateCategory nw, ClimateCategory center) {
        return ClimateCategory.isShallowOcean(center) && ClimateCategory.isShallowOcean(nw) && ClimateCategory.isShallowOcean(sw) && ClimateCategory.isShallowOcean(ne) && ClimateCategory.isShallowOcean(se) && context.nextInt(100) == 0 ? ClimateCategory.RARE_ISLAND : center;
    }
}
