package de.martenschaefer.morecustomworldgen.biomedecorator.replace;

import java.util.List;
import java.util.Objects;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.CrossSamplingBiomeDecorator;
import de.martenschaefer.morecustomworldgen.util.Chance;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CrossSamplingReplaceBiomeDecorator extends CrossSamplingBiomeDecorator {
    public static final Codec<CrossSamplingReplaceBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.BOOL.fieldOf("and").forGetter(CrossSamplingReplaceBiomeDecorator::isAnd),
            Codec.BOOL.fieldOf("negative").forGetter(CrossSamplingReplaceBiomeDecorator::isNegative),
            RegistryKeys.BIOME_CODEC.fieldOf("comparing_biome").forGetter(CrossSamplingReplaceBiomeDecorator::getComparingBiome),
            Chance.CODEC.fieldOf("chance").forGetter(CrossSamplingReplaceBiomeDecorator::getChance),
            RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(CrossSamplingReplaceBiomeDecorator::getBiome)
        ).apply(instance, instance.stable(CrossSamplingReplaceBiomeDecorator::new))
    );

    private final boolean and;
    private final boolean negative;
    private final RegistryKey<Biome> comparingBiome;
    private final Chance chance;
    private final RegistryKey<Biome> biome;

    public CrossSamplingReplaceBiomeDecorator(boolean and, boolean negative, RegistryKey<Biome> comparingBiome, Chance chance, RegistryKey<Biome> biome) {
        this.and = and;
        this.negative = negative;
        this.comparingBiome = comparingBiome;
        this.chance = chance;
        this.biome = biome;
    }

    public boolean isAnd() {
        return this.and;
    }

    public boolean isNegative() {
        return this.negative;
    }

    public RegistryKey<Biome> getComparingBiome() {
        return this.comparingBiome;
    }

    public Chance getChance() {
        return this.chance;
    }

    public RegistryKey<Biome> getBiome() {
        return this.biome;
    }

    @Override
    protected Codec<? extends BiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return ImmutableList.of(Objects.requireNonNull(biomeRegistry.get(this.biome)));
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, RegistryKey<Biome> n, RegistryKey<Biome> e, RegistryKey<Biome> s, RegistryKey<Biome> w, RegistryKey<Biome> center) {
        boolean compareN = equals(this.comparingBiome, n) && !this.negative;
        boolean compareE = equals(this.comparingBiome, e) && !this.negative;
        boolean compareS = equals(this.comparingBiome, s) && !this.negative;
        boolean compareW = equals(this.comparingBiome, w) && !this.negative;
        boolean compareCenter = equals(this.comparingBiome, center) && !this.negative;
        boolean compare = this.and ? compareN && compareE && compareS && compareW && compareCenter :
            compareN || compareE || compareS || compareW || compareCenter;

        compare = compare && this.chance.get(random);

        return compare ? this.biome : center;
    }

    private static boolean equals(RegistryKey<Biome> key1, RegistryKey<Biome> key2) {
        return key1.getValue().equals(key2.getValue());
    }
}
