package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeContextPredicate;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.util.Chance;

public record BorderingReplaceBiomeEntry(boolean centerAnd, boolean negativeCenter, boolean negative,
                                         BiomeContextPredicate centerBiome,
                                         BiomeContextPredicate borderingBiomesAnd,
                                         BiomeContextPredicate borderingBiomesOr,
                                         Chance chance, BiomeContext biome) {
    public static final Codec<BorderingReplaceBiomeEntry> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.BOOL.fieldOf("center_and").orElse(Boolean.TRUE).forGetter(BorderingReplaceBiomeEntry::centerAnd),
            Codec.BOOL.fieldOf("negative_center").orElse(Boolean.FALSE).forGetter(BorderingReplaceBiomeEntry::negativeCenter),
            Codec.BOOL.fieldOf("negative").orElse(Boolean.FALSE).forGetter(BorderingReplaceBiomeEntry::negative),
            BiomeContextPredicate.CODEC.fieldOf("center_biome").forGetter(BorderingReplaceBiomeEntry::centerBiome),
            BiomeContextPredicate.CODEC.fieldOf("bordering_biomes_and").forGetter(BorderingReplaceBiomeEntry::borderingBiomesAnd),
            BiomeContextPredicate.CODEC.fieldOf("bordering_biomes_or").forGetter(BorderingReplaceBiomeEntry::borderingBiomesOr),
            Chance.CODEC.fieldOf("chance").forGetter(BorderingReplaceBiomeEntry::chance),
            BiomeContext.CODEC.fieldOf("biome").forGetter(BorderingReplaceBiomeEntry::biome)
        ).apply(instance, instance.stable(BorderingReplaceBiomeEntry::new))
    );

    public BorderingReplaceBiomeEntry(boolean centerAnd, boolean negativeCenter, boolean negative,
                                      BiomeContextPredicate centerBiomeSet,
                                      BiomeContextPredicate borderingBiomeSetOr,
                                      Chance chance, BiomeContext biome) {
        this(centerAnd, negativeCenter, negative, centerBiomeSet, new BiomeContextPredicate(ImmutableList.of()), borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, boolean negative,
                                      BiomeContextPredicate centerBiomeSet,
                                      BiomeContextPredicate borderingBiomeSetAnd,
                                      BiomeContextPredicate borderingBiomeSetOr,
                                      Chance chance, BiomeContext biome) {
        this(centerAnd, false, negative, centerBiomeSet, borderingBiomeSetAnd, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, boolean negative,
                                      BiomeContextPredicate centerBiomeSet,
                                      BiomeContextPredicate borderingBiomeSetOr,
                                      Chance chance, BiomeContext biome) {
        this(centerAnd, negative, centerBiomeSet, new BiomeContextPredicate(ImmutableList.of()), borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(BiomeContextPredicate centerBiomeSet,
                                      BiomeContextPredicate borderingBiomeSetAnd,
                                      BiomeContextPredicate borderingBiomeSetOr,
                                      Chance chance, BiomeContext biome) {
        this(true, false, centerBiomeSet, borderingBiomeSetAnd, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, BiomeContextPredicate centerBiomeSet,
                                      BiomeContextPredicate borderingBiomeSetOr,
                                      Chance chance, BiomeContext biome) {
        this(centerAnd, false, centerBiomeSet, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, boolean negativeCenter, boolean negative,
                                      BiomeContextPredicate borderingBiomeSetOr,
                                      Chance chance, BiomeContext biome) {
        this(centerAnd, negativeCenter, negative, borderingBiomeSetOr, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, boolean negative,
                                      BiomeContextPredicate borderingBiomeSetOr,
                                      Chance chance, BiomeContext biome) {
        this(centerAnd, negative, borderingBiomeSetOr, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(BiomeContextPredicate borderingBiomeSetOr,
                                      Chance chance, BiomeContext biome) {
        this(true, borderingBiomeSetOr, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, BiomeContextPredicate borderingBiomeSetOr,
                                      Chance chance, BiomeContext biome) {
        this(centerAnd, borderingBiomeSetOr, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(BiomeContextPredicate centerBiomeSet,
                                      BiomeContextPredicate borderingBiomeSetOr,
                                      Chance chance, BiomeContext biome) {
        this(true, centerBiomeSet, borderingBiomeSetOr, chance, biome);
    }

    public Optional<BiomeContext> sample(LayerRandomnessSource random, BiomeContext n, BiomeContext e, BiomeContext s, BiomeContext w, BiomeContext center) {
        boolean compareAnd = true;
        boolean compareOr = true;

        if (!this.borderingBiomesAnd.isEmpty()) {
            boolean compareN = this.borderingBiomesAnd.test(n, random) != this.negative;
            boolean compareE = this.borderingBiomesAnd.test(e, random) != this.negative;
            boolean compareS = this.borderingBiomesAnd.test(s, random) != this.negative;
            boolean compareW = this.borderingBiomesAnd.test(w, random) != this.negative;

            compareAnd = compareN && compareE && compareS && compareW;
        }

        if (!this.borderingBiomesOr.isEmpty()) {
            boolean compareN = this.borderingBiomesOr.test(n, random) != this.negative;
            boolean compareE = this.borderingBiomesOr.test(e, random) != this.negative;
            boolean compareS = this.borderingBiomesOr.test(s, random) != this.negative;
            boolean compareW = this.borderingBiomesOr.test(w, random) != this.negative;

            compareOr = compareN || compareE || compareS || compareW;
        }

        boolean compareCenter = this.centerBiome.test(center, random) != this.negativeCenter;

        boolean compare = compareAnd && compareOr;

        compare = this.centerAnd ? compare && compareCenter : compare || compareCenter;
        compare = compare && this.chance.get(random);

        return compare ? Optional.of(this.biome) : Optional.empty();
    }

    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return Stream.of(() -> biomeRegistry.get(this.biome.biome()));
    }
}
