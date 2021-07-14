package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import java.util.List;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.ShoreBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.CrossSamplingLayer;

public class AddShoresLayer implements CrossSamplingLayer<RegistryKey<Biome>> {
    private final List<BiomeCategory> categories;
    private final String oceanCategory;
    private final ShoreBiomesConfig config;

    public AddShoresLayer(List<BiomeCategory> categories, String oceanCategory, ShoreBiomesConfig config) {
        this.categories = categories;
        this.oceanCategory = oceanCategory;
        this.config = config;
    }

    @Override
    public RegistryKey<Biome> sample(LayerRandomnessSource context, RegistryKey<Biome> n, RegistryKey<Biome> e, RegistryKey<Biome> s, RegistryKey<Biome> w, RegistryKey<Biome> center) {
        for (String ignoredCategory : config.getIgnoredCategories()) {
            if (ignoredCategory.equals(BiomeCategory.getCategory(this.categories, center))) {
                return center;
            }
        }

        for (ShoreBiomesConfig.Override override : this.config.getOverrides()) {
            boolean b = false;
            for (RegistryKey<Biome> biome : override.getBiomes()) {
                if (biome.getValue().equals(center.getValue())) {
                    b = true;
                    break;
                }
            }

            if (!b) continue;

            // And bordering biomes

            if (!override.getAndBorderingBiomes().isEmpty()) {
                if (override.isNegative() == contains(override.getAndBorderingBiomes(), n)) {
                    b = false;
                } else if (override.isNegative() == contains(override.getAndBorderingBiomes(), e)) {
                    b = false;
                } else if (override.isNegative() == contains(override.getAndBorderingBiomes(), s)) {
                    b = false;
                } else if (override.isNegative() == contains(override.getAndBorderingBiomes(), w)) {
                    b = false;
                }
            }

            if (!b) {
                if (override.isContinue()) continue;
                else return center;
            }

            // Or bordering biomes

            b = override.getOrBorderingBiomes().isEmpty() && !override.isCheckIfBorderingOcean();

            if (override.isNegative() != contains(override.getOrBorderingBiomes(), n)
                || (override.isCheckIfBorderingOcean() && this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, n)))) {
                b = true;
            } else if (override.isNegative() != contains(override.getOrBorderingBiomes(), e)
                || (override.isCheckIfBorderingOcean() && this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, e)))) {
                b = true;
            } else if (override.isNegative() != contains(override.getOrBorderingBiomes(), s)
                || (override.isCheckIfBorderingOcean() && this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, s)))) {
                b = true;
            } else if (override.isNegative() != contains(override.getOrBorderingBiomes(), w)
                || (override.isCheckIfBorderingOcean() && this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, w)))) {
                b = true;
            }

            if (!b) {
                if (override.isContinue()) continue;
                else return center;
            }

            return override.getShoreBiome();
        }

        if (!this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, center))
            && (this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, n))
            || this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, e))
            || this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, s))
            || this.oceanCategory.equals(BiomeCategory.getCategory(this.categories, w)))) {
            return this.config.getDefaultBeach();
        }

        return center;
    }

    private static boolean contains(List<RegistryKey<Biome>> biomes, RegistryKey<Biome> biome) {
        for (RegistryKey<Biome> element : biomes) {
            if (element.getValue().equals(biome.getValue()))
                return true;
        }

        return false;
    }
}
