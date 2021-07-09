package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import java.util.List;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.DiagonalCrossSamplingLayer;

public class IncreaseEdgeCurvatureBiomeLayer implements DiagonalCrossSamplingLayer<RegistryKey<Biome>> {
    private final List<RegistryKey<Biome>> shallowOceanBiomes;
    private final RegistryKey<Biome> forestBiome;
    private final RegistryKey<Biome> plainsBiome;

    public IncreaseEdgeCurvatureBiomeLayer(List<RegistryKey<Biome>> shallowOceanBiomes, RegistryKey<Biome> forestBiome, RegistryKey<Biome> plainsBiome) {
        this.shallowOceanBiomes = shallowOceanBiomes;
        this.forestBiome = forestBiome;
        this.plainsBiome = plainsBiome;
    }

    @Override
    public RegistryKey<Biome> sample(LayerRandomnessSource context, RegistryKey<Biome> sw, RegistryKey<Biome> se, RegistryKey<Biome> ne, RegistryKey<Biome> nw, RegistryKey<Biome> center) {
        if (!isShallowOcean(center) || isShallowOcean(nw) && isShallowOcean(ne) && isShallowOcean(sw) && isShallowOcean(se)) {
            if (!isShallowOcean(center) && (isShallowOcean(nw) || isShallowOcean(sw) || isShallowOcean(ne) || isShallowOcean(se)) && context.nextInt(5) == 0) {
                if (isShallowOcean(nw)) {
                    return this.forestBiome.getValue().equals(center.getValue()) ? center : nw;
                }

                if (isShallowOcean(sw)) {
                    return this.forestBiome.getValue().equals(center.getValue()) ? center : sw;
                }

                if (isShallowOcean(ne)) {
                    return this.forestBiome.getValue().equals(center.getValue()) ? center : ne;
                }

                if (isShallowOcean(se)) {
                    return this.forestBiome.getValue().equals(center.getValue()) ? center : se;
                }
            }

            return center;
        } else {
            int i = 1;
            RegistryKey<Biome> j = this.plainsBiome;
            if (!isShallowOcean(nw) && context.nextInt(i++) == 0) {
                j = nw;
            }

            if (!isShallowOcean(ne) && context.nextInt(i++) == 0) {
                j = ne;
            }

            if (!isShallowOcean(sw) && context.nextInt(i++) == 0) {
                j = sw;
            }

            if (!isShallowOcean(se) && context.nextInt(i++) == 0) {
                j = se;
            }

            if (context.nextInt(3) == 0) {
                return j;
            } else {
                return this.forestBiome.getValue().equals(j.getValue()) ? j : center;
            }
        }
    }

    private boolean isShallowOcean(RegistryKey<Biome> biome) {
        for (RegistryKey<Biome> shallowOceanBiome : this.shallowOceanBiomes) {
            if (shallowOceanBiome.getValue().equals(biome.getValue()))
                return true;
        }

        return false;
    }
}
