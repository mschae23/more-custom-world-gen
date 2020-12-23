package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.DiagonalCrossSamplingLayer;

public class AddRareIslandLayer implements DiagonalCrossSamplingLayer<ClimateCategory> {
    private final int chance;

    public AddRareIslandLayer(int chance) {
        this.chance = chance;
    }

    @Override
    public ClimateCategory sample(LayerRandomnessSource context, ClimateCategory sw, ClimateCategory se, ClimateCategory ne, ClimateCategory nw, ClimateCategory center) {
        return ClimateCategory.isShallowOcean(center) && ClimateCategory.isShallowOcean(nw) && ClimateCategory.isShallowOcean(sw) && ClimateCategory.isShallowOcean(ne) && ClimateCategory.isShallowOcean(se) && context.nextInt(this.chance) == 0 ? ClimateCategory.RARE_ISLAND : center;
    }
}
