package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.ContinentCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.ClimateConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.ConvertingLayer;
import de.martenschaefer.morecustomworldgen.util.NorthWestCoordinateTransformer;

public class AddBaseClimatesLayer implements ConvertingLayer<ClimateCategory, ContinentCategory>, NorthWestCoordinateTransformer {
    private final ClimateConfig config;

    public AddBaseClimatesLayer(ClimateConfig config) {
        this.config = config;
    }

    public ClimateCategory sample(LayerRandomnessSource context, ContinentCategory se) {
        if (se == ContinentCategory.OCEAN) {
            return ClimateCategory.OCEAN;
        } else {
            int i = context.nextInt(this.config.getWeightSum());
            if (i < this.config.getSnowyClimateWeight()) {
                return ClimateCategory.SNOWY;
            } else {
                return i < this.config.getSnowyClimateWeight() + this.config.getCoolClimateWeight() ?
                    ClimateCategory.COOL : ClimateCategory.DRY;
            }
        }
    }

    public ClimateCategory sample(LayerSampleContext<ClimateCategory, ContinentCategory, ContinentCategory, ?, ?, ?> context, LayerSampler<ContinentCategory> parent, int x, int z) {
        ContinentCategory i = parent.sample(this.transformX(x + 1), this.transformZ(z + 1));
        return this.sample(context, i);
    }
}
