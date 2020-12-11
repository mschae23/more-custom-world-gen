package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.NorthWestCoordinateTransformer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.ContinentCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.ConvertingLayer;

public enum AddColdClimatesLayer implements ConvertingLayer<ClimateCategory, ContinentCategory>, NorthWestCoordinateTransformer {
    INSTANCE;

    public ClimateCategory sample(LayerRandomnessSource context, ContinentCategory se) {
        if (se == ContinentCategory.OCEAN) {
            return ClimateCategory.UNPROCESSED_OCEAN;
        } else {
            int i = context.nextInt(6);
            if (i == 0) {
                return ClimateCategory.SNOWY;
            } else {
                return i == 1 ? ClimateCategory.COOL : ClimateCategory.DRY;
            }
        }
    }

    public ClimateCategory sample(LayerSampleContext<ClimateCategory, ContinentCategory, ContinentCategory, ?, ?, ?> context, LayerSampler<ContinentCategory> parent, int x, int z) {
        ContinentCategory i = parent.sample(this.transformX(x + 1), this.transformZ(z + 1));
        return this.sample(context, i);
    }
}
