package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.ContinentCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.DiagonalCrossSamplingLayer;

public enum IncreaseEdgeCurvatureContinentLayer implements DiagonalCrossSamplingLayer<ContinentCategory> {
    INSTANCE;

    @Override
    public ContinentCategory sample(LayerRandomnessSource context, ContinentCategory sw, ContinentCategory se, ContinentCategory ne, ContinentCategory nw, ContinentCategory center) {
        if (!(center == ContinentCategory.OCEAN) || (nw == ContinentCategory.OCEAN) && (ne == ContinentCategory.OCEAN) && (sw == ContinentCategory.OCEAN) && (se == ContinentCategory.OCEAN)) {
            if (!(center == ContinentCategory.OCEAN) && ((nw == ContinentCategory.OCEAN) || (sw == ContinentCategory.OCEAN) || (ne == ContinentCategory.OCEAN) || (se == ContinentCategory.OCEAN)) && context.nextInt(5) == 0) {
                if (nw == ContinentCategory.OCEAN) {
                    return nw;
                }

                if (sw == ContinentCategory.OCEAN) {
                    return sw;
                }

                if (ne == ContinentCategory.OCEAN) {
                    return ne;
                }

                return se;
            }

            return center;
        } else {
            int i = 1;
            ContinentCategory j = ContinentCategory.LAND;
            if (!(nw == ContinentCategory.OCEAN) && context.nextInt(i++) == 0) {
                j = nw;
            }

            if (!(ne == ContinentCategory.OCEAN) && context.nextInt(i++) == 0) {
                j = ne;
            }

            if (!(sw == ContinentCategory.OCEAN) && context.nextInt(i++) == 0) {
                j = sw;
            }

            if (!(se == ContinentCategory.OCEAN) && context.nextInt(i++) == 0) {
                j = se;
            }

            if (context.nextInt(3) == 0) {
                return j;
            } else {
                return center;
            }
        }
    }
}
