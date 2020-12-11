package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla.ClimateCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.CrossSamplingLayer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.IdentitySamplingLayer;

public final class AddClimateLayers {
    public enum AddSpecialBiomesLayer implements IdentitySamplingLayer<ClimateCategory> {
        INSTANCE;

        @Override
        public ClimateCategory sample(LayerRandomnessSource context, ClimateCategory value) {
            if (!ClimateCategory.isShallowOcean(value) && context.nextInt(13) == 0) {
                switch (value) {
                    case DRY:
                        return ClimateCategory.SPECIAL_DRY;
                    case TEMPERATE:
                        return ClimateCategory.SPECIAL_TEMPERATE;
                    case COOL:
                        return ClimateCategory.SPECIAL_COOL;
                    case SNOWY:
                        return ClimateCategory.SPECIAL_SNOWY;
                }
            }

            return value;
        }
    }

    public enum AddCoolBiomesLayer implements CrossSamplingLayer<ClimateCategory> {
        INSTANCE;

        @Override
        public ClimateCategory sample(LayerRandomnessSource context, ClimateCategory n, ClimateCategory e, ClimateCategory s, ClimateCategory w, ClimateCategory center) {
            return center != ClimateCategory.SNOWY
                || n != ClimateCategory.DRY
                && e != ClimateCategory.DRY
                && w != ClimateCategory.DRY
                && s != ClimateCategory.DRY
                && n != ClimateCategory.TEMPERATE
                && e != ClimateCategory.TEMPERATE
                && w != ClimateCategory.TEMPERATE
                && s != ClimateCategory.TEMPERATE ? center : ClimateCategory.COOL;
        }
    }

    public enum AddTemperateBiomesLayer implements CrossSamplingLayer<ClimateCategory> {
        INSTANCE;

        @Override
        public ClimateCategory sample(LayerRandomnessSource context, ClimateCategory n, ClimateCategory e, ClimateCategory s, ClimateCategory w, ClimateCategory center) {
            return center != ClimateCategory.DRY
                || n != ClimateCategory.COOL
                && e != ClimateCategory.COOL
                && w != ClimateCategory.COOL
                && s != ClimateCategory.COOL
                && n != ClimateCategory.SNOWY
                && e != ClimateCategory.SNOWY
                && w != ClimateCategory.SNOWY
                && s != ClimateCategory.SNOWY ? center : ClimateCategory.TEMPERATE;
        }
    }
}
