package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import java.util.List;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.RiverConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.MergingLayer;

public class AddRiversLayer implements MergingLayer<RegistryKey<Biome>, Integer>, IdentityCoordinateTransformer {
    private final List<BiomeCategory> categories;
    private final String oceanCategory;
    private final RiverConfig config;

    public AddRiversLayer(List<BiomeCategory> categories, String oceanCategory, RiverConfig config) {
        this.categories = categories;
        this.oceanCategory = oceanCategory;
        this.config = config;
    }

    @Override
    public RegistryKey<Biome> sample(LayerRandomnessSource context, LayerSampler<RegistryKey<Biome>> sampler1, LayerSampler<Integer> sampler2, int x, int z) {
        RegistryKey<Biome> i = sampler1.sample(this.transformX(x), this.transformZ(z));
        int j = sampler2.sample(this.transformX(x), this.transformZ(z));
        if (this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, i))) {
            return i;
        } else if (j == 1) {
            for (RiverConfig.Override override : this.config.getOverrides()) {
                if (!contains(override.getBiomes(), i)) continue;

                return override.getRiver();
            }

            return config.getRiver();
        } else {
            return i;
        }
    }

    private static boolean contains(List<RegistryKey<Biome>> biomes, RegistryKey<Biome> biome) {
        for (RegistryKey<Biome> element : biomes) {
            if (element.getValue().equals(biome.getValue()))
                return true;
        }

        return false;
    }
}
