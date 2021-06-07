package de.martenschaefer.morecustomworldgen.biomedecorator.replace;

import java.util.List;
import java.util.Objects;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeSetEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.CrossSamplingBiomeDecorator;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class BorderingReplaceBiomeDecorator extends CrossSamplingBiomeDecorator {
    public static final Codec<BorderingReplaceBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.BOOL.fieldOf("and").orElse(Boolean.TRUE).forGetter(BorderingReplaceBiomeDecorator::isAnd),
            Codec.BOOL.fieldOf("center_and").orElse(Boolean.TRUE).forGetter(BorderingReplaceBiomeDecorator::isCenterAnd),
            Codec.BOOL.fieldOf("negative").orElse(Boolean.FALSE).forGetter(BorderingReplaceBiomeDecorator::isNegative),
            BiomeSetEntry.CODEC.fieldOf("center_biomes").forGetter(BorderingReplaceBiomeDecorator::getCenterBiomeSet),
            BiomeSetEntry.CODEC.fieldOf("comparing_biomes").forGetter(BorderingReplaceBiomeDecorator::getComparingBiomeSet),
            Chance.CODEC.fieldOf("chance").forGetter(BorderingReplaceBiomeDecorator::getChance),
            RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(BorderingReplaceBiomeDecorator::getBiome)
        ).apply(instance, instance.stable(BorderingReplaceBiomeDecorator::new))
    );

    private final boolean and;
    private final boolean centerAnd;
    private final boolean negative;
    private final Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet;
    private final Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> comparingBiomeSet;
    private final Chance chance;
    private final RegistryKey<Biome> biome;

    public BorderingReplaceBiomeDecorator(boolean and, boolean centerAnd, boolean negative, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> comparingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this.and = and;
        this.centerAnd = centerAnd;
        this.negative = negative;
        this.centerBiomeSet = centerBiomeSet;
        this.comparingBiomeSet = comparingBiomeSet;
        this.chance = chance;
        this.biome = biome;
    }

    public BorderingReplaceBiomeDecorator(boolean and, boolean centerAnd, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> comparingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(and, centerAnd, false, centerBiomeSet, comparingBiomeSet, chance, biome);
    }

    public BorderingReplaceBiomeDecorator(boolean and, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> comparingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(and, true, centerBiomeSet, comparingBiomeSet, chance, biome);
    }

    public BorderingReplaceBiomeDecorator(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> centerBiomeSet, Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> comparingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(true, centerBiomeSet, comparingBiomeSet, chance, biome);
    }

    public BorderingReplaceBiomeDecorator(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> comparingBiomeSet, Chance chance, RegistryKey<Biome> biome) {
        this(comparingBiomeSet, comparingBiomeSet, chance, biome);
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

    public Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> getComparingBiomeSet() {
        return this.comparingBiomeSet;
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
        boolean compareN = BiomeSetEntry.contains(this.comparingBiomeSet, n) && !this.negative;
        boolean compareE = BiomeSetEntry.contains(this.comparingBiomeSet, e) && !this.negative;
        boolean compareS = BiomeSetEntry.contains(this.comparingBiomeSet, s) && !this.negative;
        boolean compareW = BiomeSetEntry.contains(this.comparingBiomeSet, w) && !this.negative;
        boolean compareCenter = BiomeSetEntry.contains(this.centerBiomeSet, center) && !this.negative;
        boolean compare = this.and ? compareN && compareE && compareS && compareW :
            compareN || compareE || compareS || compareW;

        compare = this.centerAnd ? compare && compareCenter : compare || compareCenter;
        compare = compare && this.chance.get(random);

        return compare ? this.biome : center;
    }
}
