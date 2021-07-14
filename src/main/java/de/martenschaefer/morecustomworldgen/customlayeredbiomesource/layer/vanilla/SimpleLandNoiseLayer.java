package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampleContext;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.ConvertingLayer;
import de.martenschaefer.morecustomworldgen.util.IdentityCoordinateTransformer;

public enum SimpleLandNoiseLayer implements ConvertingLayer<Integer, ClimateCategory>, IdentityCoordinateTransformer {
    INSTANCE;

    @Override
    public Integer sample(LayerSampleContext<Integer, ClimateCategory, ClimateCategory, ?, ?, ?> context, LayerSampler<ClimateCategory> parent, int x, int z) {
        return this.sample(context, parent.sample(this.transformX(x), this.transformZ(z)));
    }

    public int sample(LayerSampleContext<Integer, ClimateCategory, ClimateCategory, ?, ?, ?> context, ClimateCategory climate) {
        return ClimateCategory.isShallowOcean(climate) ? -1 : context.nextInt(299999) + 2;
    }
}
