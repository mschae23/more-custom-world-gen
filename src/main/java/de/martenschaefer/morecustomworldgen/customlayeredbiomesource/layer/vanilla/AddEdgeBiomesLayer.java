package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import java.util.List;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenMod;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.BiomeCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.EdgeBiomesConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.CrossSamplingLayer;

public class AddEdgeBiomesLayer implements CrossSamplingLayer<RegistryKey<Biome>> {
    private final List<BiomeCategory> categories;
    private final EdgeBiomesConfig config;

    public AddEdgeBiomesLayer(List<BiomeCategory> categories, EdgeBiomesConfig config) {
        this.categories = categories;
        this.config = config;
    }

    @Override
    public RegistryKey<Biome> sample(LayerRandomnessSource context, RegistryKey<Biome> n, RegistryKey<Biome> e, RegistryKey<Biome> s, RegistryKey<Biome> w, RegistryKey<Biome> center) {
        MutableObject<RegistryKey<Biome>> is = new MutableObject<>(null);

        for (String ignoredCategory : config.getIgnoredCategories()) {
            if (this.setIfInCategory(is, center, ignoredCategory)) {
                return is.get();
            }
        }

        for (EdgeBiomesConfig.CategoryEdgeBiome categoryEdgeBiome : config.getCategoryEdgeBiomes()) {
            if (this.setCategoryEdgeBiomeForBiome(is, n, e, s, w, center, categoryEdgeBiome.getBiome(), categoryEdgeBiome.getEdgeBiome())) {
                return is.get();
            }
        }

        for (EdgeBiomesConfig.EdgeBiome edgeBiome : config.getEdgeBiomes()) {
            if (MoreCustomWorldGenMod.equals(center, edgeBiome.getBiome())) {
                for (RegistryKey<Biome> borderingBiome : edgeBiome.getBorderingBiomes()) {
                    if (MoreCustomWorldGenMod.equals(n, borderingBiome)
                        || MoreCustomWorldGenMod.equals(e, borderingBiome)
                        || MoreCustomWorldGenMod.equals(w, borderingBiome)
                        || MoreCustomWorldGenMod.equals(s, borderingBiome)) {
                        return edgeBiome.getEdgeBiome();
                    }
                }
            }
        }

        return center;
    }

    private boolean setIfInCategory(MutableObject<RegistryKey<Biome>> is, RegistryKey<Biome> biome, String category) {
        if (!category.equals(BiomeCategory.getCategory(this.categories, biome))) {
            return false;
        } else {
            is.set(biome);
            return true;
        }
    }

    private boolean setCategoryEdgeBiomeForBiome(MutableObject<RegistryKey<Biome>> is, RegistryKey<Biome> n, RegistryKey<Biome> e, RegistryKey<Biome> s, RegistryKey<Biome> w, RegistryKey<Biome> center, RegistryKey<Biome> checkFor, RegistryKey<Biome> edgeBiome) {
        if (center != checkFor) {
            return false;
        } else {
            if (areSimilar(n, checkFor) && areSimilar(e, checkFor) && areSimilar(w, checkFor) && areSimilar(s, checkFor)) {
                is.set(center);
            } else {
                is.set(edgeBiome);
            }

            return true;
        }
    }

    private boolean areSimilar(RegistryKey<Biome> i, RegistryKey<Biome> j) {
        return BiomeCategory.getCategory(this.categories, i).equals(BiomeCategory.getCategory(this.categories, j));
    }

    private static class MutableObject<T> {
        private T value;

        public MutableObject(T value) {
            this.value = value;
        }

        public T get() {
            return this.value;
        }

        public void set(T value) {
            this.value = value;
        }
    }
}
