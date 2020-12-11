package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.OceanCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.InitLayer;

public enum OceanTemperatureLayer implements InitLayer<OceanCategory> {
    INSTANCE;

    @Override
    public OceanCategory sample(LayerRandomnessSource context, int x, int z) {
        PerlinNoiseSampler noiseSampler = context.getNoiseSampler();
        double d = noiseSampler.sample((double) x / 8.0D, (double) z / 8.0D, 0.0D, 0.0D, 0.0D);
        if (d > 0.4D) {
            return OceanCategory.WARM;
        } else if (d > 0.2D) {
            return OceanCategory.LUKEWARM;
        } else if (d < -0.4D) {
            return OceanCategory.FROZEN;
        } else {
            return d < -0.2D ? OceanCategory.COLD : OceanCategory.NORMAL;
        }
    }
}
