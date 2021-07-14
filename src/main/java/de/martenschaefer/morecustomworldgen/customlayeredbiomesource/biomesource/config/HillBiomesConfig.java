package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.Nullable;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class HillBiomesConfig {
    public static final Codec<HillBiomesConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.STRING.listOf().fieldOf("ignored_categories").forGetter(HillBiomesConfig::getIgnoredCategories),
            SpecialHillBiomeEntry.CODEC.listOf().fieldOf("special_hill_biomes").forGetter(HillBiomesConfig::getSpecialHillBiomes),
            HillBiomeEntry.CODEC.listOf().fieldOf("hill_biomes").forGetter(HillBiomesConfig::getHillBiomes),
            ComplexHillBiomeEntry.CODEC.listOf().fieldOf("complex_hill_biomes").forGetter(HillBiomesConfig::getComplexHillBiomes)
        ).apply(instance, instance.stable(HillBiomesConfig::new))
    );

    private final List<String> ignoredCategories;
    private final List<SpecialHillBiomeEntry> specialHillBiomes;
    private final List<HillBiomeEntry> hillBiomes;
    private final List<ComplexHillBiomeEntry> complexHillBiomes;

    public HillBiomesConfig(List<String> ignoredCategories, List<SpecialHillBiomeEntry> specialHillBiomes, List<HillBiomeEntry> hillBiomes, List<ComplexHillBiomeEntry> complexHillBiomes) {
        this.ignoredCategories = ignoredCategories;
        this.specialHillBiomes = specialHillBiomes;
        this.hillBiomes = hillBiomes;
        this.complexHillBiomes = complexHillBiomes;
    }

    public List<String> getIgnoredCategories() {
        return this.ignoredCategories;
    }

    public List<SpecialHillBiomeEntry> getSpecialHillBiomes() {
        return this.specialHillBiomes;
    }

    public List<HillBiomeEntry> getHillBiomes() {
        return this.hillBiomes;
    }

    public List<ComplexHillBiomeEntry> getComplexHillBiomes() {
        return this.complexHillBiomes;
    }

    public RegistryKey<Biome> getSpecialHillBiome(RegistryKey<Biome> biome) {
        return this.getSpecialHillBiomes().stream()
            .filter(entry -> biome.getValue().equals(entry.getBiome().getValue()))
            .map(SpecialHillBiomeEntry::getSpecialHillBiome)
            .findAny()
            .orElse(biome);
    }

    public RegistryKey<Biome> getSpecialHillBiome(RegistryKey<Biome> biome, RegistryKey<Biome> defaultBiome) {
        return this.getSpecialHillBiomes().stream()
            .filter(entry -> biome.getValue().equals(entry.getBiome().getValue()))
            .map(SpecialHillBiomeEntry::getSpecialHillBiome)
            .findAny()
            .orElse(defaultBiome);
    }

    public RegistryKey<Biome> getHillBiome(LayerRandomnessSource context, List<BiomeCategory> categories, RegistryKey<Biome> biome) {
        return this.getHillBiomes().stream()
            .filter(entry ->
                entry.getBiome() != null ? biome.getValue().equals(entry.getBiome().getValue()) :
                    entry.getCategory() != null && entry.getCategory().equals(BiomeCategory.getCategory(categories, biome)))
            .map(HillBiomeEntry::getHillBiome)
            .findAny()
            .orElseGet(() -> getComplexHillBiome(context, biome));
    }

    public RegistryKey<Biome> getComplexHillBiome(LayerRandomnessSource context, RegistryKey<Biome> biome) {
        List<RegistryKey<Biome>> hillBiomes = this.getComplexHillBiomes().stream()
            .filter(entry -> contains(entry.getBiomes(), biome))
            .flatMap(entry -> entry.getHillBiomes().stream())
            .collect(Collectors.toList());

        if (hillBiomes.size() == 0) return biome;
        if (hillBiomes.size() == 1) return hillBiomes.get(0);

        return hillBiomes.get(context.nextInt(hillBiomes.size()));
    }

    public List<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        List<Supplier<Biome>> specialHillBiomes = this.getSpecialHillBiomes().stream()
            .map(SpecialHillBiomeEntry::getSpecialHillBiome)
            .<Supplier<Biome>>map(hillBiome -> () -> biomeRegistry.getOrThrow(hillBiome))
            .collect(Collectors.toList());

        List<Supplier<Biome>> hillBiomes = this.getHillBiomes().stream()
            .map(HillBiomeEntry::getHillBiome)
            .<Supplier<Biome>>map(hillBiome -> () -> biomeRegistry.getOrThrow(hillBiome))
            .collect(Collectors.toList());

        List<Supplier<Biome>> complexHillBiomes = this.getComplexHillBiomes().stream()
            .flatMap(entry -> entry.getHillBiomes().stream())
            .<Supplier<Biome>>map(hillBiome -> () -> biomeRegistry.getOrThrow(hillBiome))
            .collect(Collectors.toList());

        hillBiomes.addAll(specialHillBiomes);
        hillBiomes.addAll(complexHillBiomes);

        return hillBiomes;
    }

    private static boolean contains(List<RegistryKey<Biome>> biomes, RegistryKey<Biome> biome) {
        for (RegistryKey<Biome> element : biomes) {
            if (biome.getValue().equals(element.getValue()))
                return true;
        }

        return false;
    }

    public static class SpecialHillBiomeEntry {
        public static final Codec<SpecialHillBiomeEntry> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(SpecialHillBiomeEntry::getBiome),
                RegistryKeys.BIOME_CODEC.fieldOf("special_hill_biome").forGetter(SpecialHillBiomeEntry::getSpecialHillBiome)
            ).apply(instance, instance.stable(SpecialHillBiomeEntry::new))
        );

        private final RegistryKey<Biome> biome;
        private final RegistryKey<Biome> specialHillBiome;

        public SpecialHillBiomeEntry(RegistryKey<Biome> biome, RegistryKey<Biome> specialHillBiome) {
            this.biome = biome;
            this.specialHillBiome = specialHillBiome;
        }

        public RegistryKey<Biome> getBiome() {
            return this.biome;
        }

        public RegistryKey<Biome> getSpecialHillBiome() {
            return this.specialHillBiome;
        }
    }

    public static class HillBiomeEntry {
        public static final MapCodec<HillBiomeEntry> BIOME_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(HillBiomeEntry::getBiome),
                RegistryKeys.BIOME_CODEC.fieldOf("hill_biome").forGetter(HillBiomeEntry::getHillBiome)
            ).apply(instance, HillBiomeEntry::new)
        );

        public static final MapCodec<HillBiomeEntry> CATEGORY_CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                Codec.STRING.fieldOf("category").forGetter(HillBiomeEntry::getCategory),
                RegistryKeys.BIOME_CODEC.fieldOf("hill_biome").forGetter(HillBiomeEntry::getHillBiome)
            ).apply(instance, HillBiomeEntry::new)
        );

        public static final Codec<HillBiomeEntry> CODEC = Codec.mapEither(BIOME_CODEC, CATEGORY_CODEC).xmap(
            either -> either.map(Function.identity(), Function.identity()),
            entry -> Optional.ofNullable(entry.biome).<Either<HillBiomeEntry, HillBiomeEntry>>map(biome -> Either.left(entry)).orElseGet(() -> Either.right(entry))).codec();

        private final RegistryKey<Biome> biome;
        private final String category;
        private final RegistryKey<Biome> hillBiome;

        public HillBiomeEntry(RegistryKey<Biome> biome, RegistryKey<Biome> hillBiome) {
            this.biome = biome;
            this.category = null;
            this.hillBiome = hillBiome;
        }

        public HillBiomeEntry(String category, RegistryKey<Biome> hillBiome) {
            this.biome = null;
            this.category = category;
            this.hillBiome = hillBiome;
        }

        @Nullable
        public RegistryKey<Biome> getBiome() {
            return this.biome;
        }

        @Nullable
        public String getCategory() {
            return this.category;
        }

        public RegistryKey<Biome> getHillBiome() {
            return this.hillBiome;
        }
    }

    public static class ComplexHillBiomeEntry {
        public static final Codec<ComplexHillBiomeEntry> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                RegistryKeys.BIOME_CODEC.listOf().fieldOf("biomes").forGetter(ComplexHillBiomeEntry::getBiomes),
                Codec.INT.fieldOf("chance").forGetter(ComplexHillBiomeEntry::getChance),
                RegistryKeys.BIOME_CODEC.listOf().fieldOf("hill_biomes").forGetter(ComplexHillBiomeEntry::getHillBiomes)
            ).apply(instance, instance.stable(ComplexHillBiomeEntry::new))
        );

        private final List<RegistryKey<Biome>> biomes;
        private final int chance;
        private final List<RegistryKey<Biome>> hillBiomes;

        public ComplexHillBiomeEntry(List<RegistryKey<Biome>> biomes, int chance, List<RegistryKey<Biome>> hillBiomes) {
            this.biomes = biomes;
            this.chance = chance;
            this.hillBiomes = hillBiomes;
        }

        public List<RegistryKey<Biome>> getBiomes() {
            return this.biomes;
        }

        public int getChance() {
            return this.chance;
        }

        public List<RegistryKey<Biome>> getHillBiomes() {
            return this.hillBiomes;
        }
    }
}
