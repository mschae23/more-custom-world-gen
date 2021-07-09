package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeSetEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.CrossSamplingBiomeDecorator;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class BorderingReplaceBiomeDecorator extends CrossSamplingBiomeDecorator {
    public static final Codec<BorderingReplaceBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.BOOL.fieldOf("and").orElse(Boolean.FALSE).forGetter(BorderingReplaceBiomeDecorator::isAnd),
            Codec.BOOL.fieldOf("center_and").orElse(Boolean.TRUE).forGetter(BorderingReplaceBiomeDecorator::isCenterAnd),
            Codec.BOOL.fieldOf("negative").orElse(Boolean.FALSE).forGetter(BorderingReplaceBiomeDecorator::isNegative),
            BiomeSetEntry.CODEC.fieldOf("center_biomes").forGetter(BorderingReplaceBiomeDecorator::getCenterBiomeSet),
            BiomeSetEntry.CODEC.fieldOf("bordering_biomes").forGetter(BorderingReplaceBiomeDecorator::getBorderingBiomeSet),
            Chance.CODEC.fieldOf("chance").forGetter(BorderingReplaceBiomeDecorator::getChance),
            RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(BorderingReplaceBiomeDecorator::getBiome)
        ).apply(instance, instance.stable(BorderingReplaceBiomeDecorator::new))
    );

    private final boolean and;
    private final boolean centerAnd;
    private final boolean negative;
    private final Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet;
    private final Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSet;
    private final Chance chance;
    private final RegistryKey<Biome> biome;

    public BorderingReplaceBiomeDecorator(boolean and, boolean centerAnd, boolean negative, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this.and = and;
        this.centerAnd = centerAnd;
        this.negative = negative;
        this.centerBiomeSet = centerBiomeSet;
        this.borderingBiomeSet = borderingBiomeSet;
        this.chance = chance;
        this.biome = biome;
    }

    public BorderingReplaceBiomeDecorator(boolean and, boolean centerAnd, boolean negative, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(and, centerAnd, negative, borderingBiomeSet, borderingBiomeSet, chance, biome);
    }

    public BorderingReplaceBiomeDecorator(boolean and, boolean centerAnd, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(and, centerAnd, false, centerBiomeSet, borderingBiomeSet, chance, biome);
    }

    public BorderingReplaceBiomeDecorator(boolean and, boolean centerAnd, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(and, centerAnd, false, borderingBiomeSet, borderingBiomeSet, chance, biome);
    }

    public BorderingReplaceBiomeDecorator(boolean and, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(and, true, centerBiomeSet, borderingBiomeSet, chance, biome);
    }

    public BorderingReplaceBiomeDecorator(boolean and, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(and, true, borderingBiomeSet, borderingBiomeSet, chance, biome);
    }

    public BorderingReplaceBiomeDecorator(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(false, centerBiomeSet, borderingBiomeSet, chance, biome);
    }

    public BorderingReplaceBiomeDecorator(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> borderingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(borderingBiomeSet, borderingBiomeSet, chance, biome);
    }

    public boolean isAnd() {
        return this.and;
    }

    public boolean isCenterAnd() {
        return centerAnd;
    }

    public boolean isNegative() {
        return this.negative;
    }

    public Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> getCenterBiomeSet() {
        return centerBiomeSet;
    }

    public Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> getBorderingBiomeSet() {
        return this.borderingBiomeSet;
    }

    public Chance getChance() {
        return this.chance;
    }

    public RegistryKey<Biome> getBiome() {
        return this.biome;
    }

    @Override
    protected Codec<BorderingReplaceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return ImmutableList.of(Objects.requireNonNull(biomeRegistry.get(this.biome)));
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, RegistryKey<Biome> n, RegistryKey<Biome> e, RegistryKey<Biome> s, RegistryKey<Biome> w, RegistryKey<Biome> center) {
        return this.sample(random, n, e, s, w, center).orElse(center);
    }

    public Optional<RegistryKey<Biome>> sample(DecoratorRandomnessSource random, RegistryKey<Biome> n, RegistryKey<Biome> e, RegistryKey<Biome> s, RegistryKey<Biome> w, RegistryKey<Biome> center) {
        boolean compareN = BiomeSetEntry.contains(this.borderingBiomeSet, n) == !this.negative;
        boolean compareE = BiomeSetEntry.contains(this.borderingBiomeSet, e) == !this.negative;
        boolean compareS = BiomeSetEntry.contains(this.borderingBiomeSet, s) == !this.negative;
        boolean compareW = BiomeSetEntry.contains(this.borderingBiomeSet, w) == !this.negative;
        boolean compareCenter = BiomeSetEntry.contains(this.centerBiomeSet, center);
        boolean compare = this.and ? compareN && compareE && compareS && compareW :
            compareN || compareE || compareS || compareW;

        compare = this.centerAnd ? compare && compareCenter : compare || compareCenter;
        compare = compare && this.chance.get(random);

        return compare ? Optional.of(this.biome) : Optional.empty();
    }
}
