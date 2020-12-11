package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class EdgeBiomesConfig {
    public static final Codec<EdgeBiomesConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.STRING.listOf().fieldOf("ignored_categories").forGetter(config -> config.ignoredCategories),
            CategoryEdgeBiome.CODEC.listOf().fieldOf("category_edge_biomes").forGetter(config -> config.categoryEdgeBiomes),
            EdgeBiome.CODEC.listOf().fieldOf("edge_biomes").forGetter(config -> config.edgeBiomes)
        ).apply(instance, instance.stable(EdgeBiomesConfig::new))
    );

    private final List<String> ignoredCategories;
    private final List<CategoryEdgeBiome> categoryEdgeBiomes;
    private final List<EdgeBiome> edgeBiomes;

    public EdgeBiomesConfig(List<String> ignoredCategories, List<CategoryEdgeBiome> categoryEdgeBiomes, List<EdgeBiome> edgeBiomes) {
        this.ignoredCategories = ignoredCategories;
        this.categoryEdgeBiomes = categoryEdgeBiomes;
        this.edgeBiomes = edgeBiomes;
    }

    public List<String> getIgnoredCategories() {
        return this.ignoredCategories;
    }

    public List<CategoryEdgeBiome> getCategoryEdgeBiomes() {
        return this.categoryEdgeBiomes;
    }

    public List<EdgeBiome> getEdgeBiomes() {
        return this.edgeBiomes;
    }

    public List<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        List<Supplier<Biome>> categoryEdgeBiomes = this.getCategoryEdgeBiomes().stream()
            .map(CategoryEdgeBiome::getEdgeBiome)
            .<Supplier<Biome>>map(edgeBiome -> () -> biomeRegistry.get(edgeBiome))
            .collect(Collectors.toList());

        List<Supplier<Biome>> edgeBiomes = this.getEdgeBiomes().stream()
            .map(EdgeBiome::getEdgeBiome)
            .<Supplier<Biome>>map(edgeBiome -> () -> biomeRegistry.get(edgeBiome))
            .collect(Collectors.toList());

        edgeBiomes.addAll(categoryEdgeBiomes);
        return edgeBiomes;
    }

    public static class CategoryEdgeBiome {
        public static final Codec<CategoryEdgeBiome> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(CategoryEdgeBiome::getBiome),
                RegistryKeys.BIOME_CODEC.fieldOf("edge_biome").forGetter(CategoryEdgeBiome::getEdgeBiome)
            ).apply(instance, instance.stable(CategoryEdgeBiome::new))
        );

        private final RegistryKey<Biome> biome;
        private final RegistryKey<Biome> edgeBiome;

        public CategoryEdgeBiome(RegistryKey<Biome> biome, RegistryKey<Biome> edgeBiome) {
            this.biome = biome;
            this.edgeBiome = edgeBiome;
        }

        public RegistryKey<Biome> getBiome() {
            return this.biome;
        }

        public RegistryKey<Biome> getEdgeBiome() {
            return this.edgeBiome;
        }
    }

    public static class EdgeBiome {
        public static final Codec<EdgeBiome> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(EdgeBiome::getBiome),
                RegistryKeys.BIOME_CODEC.listOf().fieldOf("bordering_biomes").forGetter(EdgeBiome::getBorderingBiomes),
                RegistryKeys.BIOME_CODEC.fieldOf("edge_biome").forGetter(EdgeBiome::getEdgeBiome)
            ).apply(instance, instance.stable(EdgeBiome::new))
        );

        private final RegistryKey<Biome> biome;
        private final List<RegistryKey<Biome>> borderingBiomes;
        private final RegistryKey<Biome> edgeBiome;

        public EdgeBiome(RegistryKey<Biome> biome, List<RegistryKey<Biome>> borderingBiomes, RegistryKey<Biome> edgeBiome) {
            this.biome = biome;
            this.borderingBiomes = borderingBiomes;
            this.edgeBiome = edgeBiome;
        }

        public RegistryKey<Biome> getBiome() {
            return this.biome;
        }

        public List<RegistryKey<Biome>> getBorderingBiomes() {
            return this.borderingBiomes;
        }

        public RegistryKey<Biome> getEdgeBiome() {
            return this.edgeBiome;
        }
    }
}
