package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import java.util.List;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.NorthWestCoordinateTransformer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.HillBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util.LayerSampler;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.MergingLayer;

public class AddHillsLayer implements MergingLayer<RegistryKey<Biome>, Integer>, NorthWestCoordinateTransformer {
    private final List<BiomeCategory> categories;
    private final HillBiomesConfig config;

    public AddHillsLayer(List<BiomeCategory> categories, HillBiomesConfig config) {
        this.categories = categories;
        this.config = config;
    }

    @Override
    public RegistryKey<Biome> sample(LayerRandomnessSource context, LayerSampler<RegistryKey<Biome>> sampler1, LayerSampler<Integer> sampler2, int x, int z) {
        RegistryKey<Biome> i = sampler1.sample(this.transformX(x + 1), this.transformZ(z + 1));
        int noise = sampler2.sample(this.transformX(x + 1), this.transformZ(z + 1));

        int k = (noise - 2) % 29;

        for(String ignoredCategory : config.getIgnoredCategories()) {
            if(ignoredCategory.equals(BiomeCategory.getCategory(this.categories, i))) {
                return i;
            }
        }
        
        if (noise >= 2 && k == 1) {
            return this.config.getSpecialHillBiome(i);
        } else {
            if (context.nextInt(3) == 0 || k == 0) {
                RegistryKey<Biome> l = config.getHillBiome(context, this.categories, i);

                if (k == 0 && !l.getValue().equals(i.getValue())) {
                    l = this.config.getSpecialHillBiome(l, i);
                }

                if (!l.getValue().equals(i.getValue())) {
                    int m = 0;
                    if (isSimilar(sampler1, this.transformX(x + 1), this.transformZ(z), i)) {
                        m++;
                    }

                    if (isSimilar(sampler1, this.transformX(x + 2), this.transformZ(z + 1), i)) {
                        m++;
                    }

                    if (isSimilar(sampler1, this.transformX(x), this.transformZ(z + 1), i)) {
                        m++;
                    }

                    if (isSimilar(sampler1, this.transformX(x + 1), this.transformZ(z + 2), i)) {
                        m++;
                    }

                    if (m >= 3) {
                        return l;
                    }
                }
            }

            return i;
        }
    }

    private boolean isSimilar(LayerSampler<RegistryKey<Biome>> sampler, int x, int z, RegistryKey<Biome> i) {
        return BiomeCategory.getCategory(this.categories, sampler.sample(x, z)).equals(BiomeCategory.getCategory(this.categories, i));
    }
}
