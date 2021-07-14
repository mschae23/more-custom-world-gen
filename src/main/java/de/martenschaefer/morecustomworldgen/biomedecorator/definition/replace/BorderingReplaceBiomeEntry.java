package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeSetEntry;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public record BorderingReplaceBiomeEntry(boolean centerAnd, boolean negativeCenter, boolean negative,
                                         Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet,
                                         Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetAnd,
                                         Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                         Chance chance, RegistryKey<Biome> biome) {
    public static final Codec<BorderingReplaceBiomeEntry> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.BOOL.fieldOf("center_and").orElse(Boolean.TRUE).forGetter(BorderingReplaceBiomeEntry::centerAnd),
            Codec.BOOL.fieldOf("negative_center").orElse(Boolean.FALSE).forGetter(BorderingReplaceBiomeEntry::negativeCenter),
            Codec.BOOL.fieldOf("negative").orElse(Boolean.FALSE).forGetter(BorderingReplaceBiomeEntry::negative),
            BiomeSetEntry.CODEC.fieldOf("center_biomes").forGetter(BorderingReplaceBiomeEntry::centerBiomeSet),
            BiomeSetEntry.CODEC.fieldOf("bordering_biomes_and").forGetter(BorderingReplaceBiomeEntry::borderingBiomeSetAnd),
            BiomeSetEntry.CODEC.fieldOf("bordering_biomes_or").forGetter(BorderingReplaceBiomeEntry::borderingBiomeSetOr),
            Chance.CODEC.fieldOf("chance").forGetter(BorderingReplaceBiomeEntry::chance),
            RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(BorderingReplaceBiomeEntry::biome)
        ).apply(instance, instance.stable(BorderingReplaceBiomeEntry::new))
    );

    public BorderingReplaceBiomeEntry(boolean centerAnd, boolean negativeCenter, boolean negative,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                      Chance chance, RegistryKey<Biome> biome) {
        this(centerAnd, negativeCenter, negative, centerBiomeSet, Either.right(ImmutableList.of()), borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, boolean negative,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetAnd,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                      Chance chance, RegistryKey<Biome> biome) {
        this(centerAnd, false, negative, centerBiomeSet, borderingBiomeSetAnd, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, boolean negative,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                      Chance chance, RegistryKey<Biome> biome) {
        this(centerAnd, negative, centerBiomeSet, Either.right(ImmutableList.of()), borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetAnd,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                      Chance chance, RegistryKey<Biome> biome) {
        this(true, false, centerBiomeSet, borderingBiomeSetAnd, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                      Chance chance, RegistryKey<Biome> biome) {
        this(centerAnd, false, centerBiomeSet, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, boolean negativeCenter, boolean negative,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                      Chance chance, RegistryKey<Biome> biome) {
        this(centerAnd, negativeCenter, negative, borderingBiomeSetOr, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, boolean negative,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                      Chance chance, RegistryKey<Biome> biome) {
        this(centerAnd, negative, borderingBiomeSetOr, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                      Chance chance, RegistryKey<Biome> biome) {
        this(true, borderingBiomeSetOr, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(boolean centerAnd, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                      Chance chance, RegistryKey<Biome> biome) {
        this(centerAnd, borderingBiomeSetOr, borderingBiomeSetOr, chance, biome);
    }

    public BorderingReplaceBiomeEntry(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet,
                                      Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSetOr,
                                      Chance chance, RegistryKey<Biome> biome) {
        this(true, centerBiomeSet, borderingBiomeSetOr, chance, biome);
    }

    public Optional<RegistryKey<Biome>> sample(LayerRandomnessSource random, RegistryKey<Biome> n, RegistryKey<Biome> e, RegistryKey<Biome> s, RegistryKey<Biome> w, RegistryKey<Biome> center) {
        boolean compareAnd = true;
        boolean compareOr = true;

        if (!BiomeSetEntry.isEmpty(this.borderingBiomeSetAnd)) {
            boolean compareN = BiomeSetEntry.contains(this.borderingBiomeSetAnd, n) != this.negative;
            boolean compareE = BiomeSetEntry.contains(this.borderingBiomeSetAnd, e) != this.negative;
            boolean compareS = BiomeSetEntry.contains(this.borderingBiomeSetAnd, s) != this.negative;
            boolean compareW = BiomeSetEntry.contains(this.borderingBiomeSetAnd, w) != this.negative;

            compareAnd = compareN && compareE && compareS && compareW;
        }

        if (!BiomeSetEntry.isEmpty(this.borderingBiomeSetOr)) {
            boolean compareN = BiomeSetEntry.contains(this.borderingBiomeSetOr, n) != this.negative;
            boolean compareE = BiomeSetEntry.contains(this.borderingBiomeSetOr, e) != this.negative;
            boolean compareS = BiomeSetEntry.contains(this.borderingBiomeSetOr, s) != this.negative;
            boolean compareW = BiomeSetEntry.contains(this.borderingBiomeSetOr, w) != this.negative;

            compareOr = compareN || compareE || compareS || compareW;
        }

        boolean compareCenter = BiomeSetEntry.contains(this.centerBiomeSet, center) != this.negativeCenter;

        boolean compare = compareAnd && compareOr;

        compare = this.centerAnd ? compare && compareCenter : compare || compareCenter;
        compare = compare && this.chance.get(random);

        return compare ? Optional.of(this.biome) : Optional.empty();
    }

    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return ImmutableList.of(Objects.requireNonNull(biomeRegistry.get(this.biome)));
    }
}
