package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import java.util.List;
import java.util.Random;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.util.Chance;

public record BiomePredicate(Either<RegistryKey<Biome>, List<RegistryKey<Biome>>> biomes, boolean negative,
                             Chance chance) {
    public static final Codec<BiomePredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BiomeSetEntry.CODEC.fieldOf("biomes").forGetter(BiomePredicate::biomes),
        Codec.BOOL.optionalFieldOf("negative", Boolean.FALSE).forGetter(BiomePredicate::negative),
        Chance.CODEC.optionalFieldOf("chance", Chance.always()).forGetter(BiomePredicate::chance)
    ).apply(instance, instance.stable(BiomePredicate::new)));

    public BiomePredicate(RegistryKey<Biome> biome, boolean negative, Chance chance) {
        this(Either.left(biome), negative, chance);
    }

    public BiomePredicate(RegistryKey<Biome> biome, boolean negative) {
        this(biome, negative, Chance.always());
    }

    public BiomePredicate(RegistryKey<Biome> biome, Chance chance) {
        this(biome, false, chance);
    }

    public BiomePredicate(RegistryKey<Biome> biome) {
        this(biome, false);
    }

    public BiomePredicate(List<RegistryKey<Biome>> biomes, boolean negative, Chance chance) {
        this(Either.right(biomes), negative, chance);
    }

    public BiomePredicate(List<RegistryKey<Biome>> biomes, boolean negative) {
        this(biomes, negative, Chance.always());
    }

    public BiomePredicate(List<RegistryKey<Biome>> biomes, Chance chance) {
        this(biomes, false, chance);
    }

    public BiomePredicate(List<RegistryKey<Biome>> biomes) {
        this(biomes, false);
    }

    public boolean isEmpty() {
        return BiomeSetEntry.isEmpty(this.biomes) && !this.negative || this.chance.numerator() == 0;
    }

    public boolean test(RegistryKey<Biome> biome, Random random) {
        boolean test = BiomeSetEntry.contains(this.biomes, biome);

        return test != this.negative && this.chance.get(random);
    }

    public boolean test(RegistryKey<Biome> biome, LayerRandomnessSource random) {
        boolean test = BiomeSetEntry.contains(this.biomes, biome);

        return test != this.negative && this.chance.get(random);
    }
}
