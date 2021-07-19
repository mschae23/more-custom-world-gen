package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.util.Chance;

public record BiomeContextPredicate(Optional<BiomePredicate> biome, List<ParameterPredicate> parameters,
                                    boolean parametersAnd, Chance chance) {
    public static final Codec<BiomeContextPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BiomePredicate.CODEC.optionalFieldOf("biome").forGetter(BiomeContextPredicate::biome),
        ParameterPredicate.CODEC.listOf().fieldOf("parameters").forGetter(BiomeContextPredicate::parameters),
        Codec.BOOL.optionalFieldOf("parameters_and", Boolean.TRUE).forGetter(BiomeContextPredicate::parametersAnd),
        Chance.CODEC.optionalFieldOf("chance", Chance.always()).forGetter(BiomeContextPredicate::chance)
    ).apply(instance, instance.stable(BiomeContextPredicate::new)));

    public BiomeContextPredicate(BiomePredicate biome, List<ParameterPredicate> parameters, boolean parametersAnd, Chance chance) {
        this(Optional.of(biome), parameters, parametersAnd, chance);
    }

    public BiomeContextPredicate(BiomePredicate biome, List<ParameterPredicate> parameters, boolean parametersAnd) {
        this(biome, parameters, parametersAnd, Chance.always());
    }

    public BiomeContextPredicate(BiomePredicate biome, List<ParameterPredicate> parameters, Chance chance) {
        this(biome, parameters, true, chance);
    }

    public BiomeContextPredicate(BiomePredicate biome, List<ParameterPredicate> parameters) {
        this(biome, parameters, Chance.always());
    }

    public BiomeContextPredicate(List<ParameterPredicate> parameters, boolean parametersAnd, Chance chance) {
        this(Optional.empty(), parameters, parametersAnd, chance);
    }

    public BiomeContextPredicate(List<ParameterPredicate> parameters, boolean parametersAnd) {
        this(parameters, parametersAnd, Chance.always());
    }

    public BiomeContextPredicate(List<ParameterPredicate> parameters, Chance chance) {
        this(parameters, true, chance);
    }

    public BiomeContextPredicate(List<ParameterPredicate> parameters) {
        this(parameters, Chance.always());
    }

    public boolean isEmpty() {
        return this.biome.isEmpty() && this.parameters.isEmpty() || this.chance.numerator() == 0;
    }

    public boolean test(BiomeContext context, Random random) {
        boolean testBiome = this.biome.map(predicate -> predicate.test(context.biome(), random)).orElse(Boolean.TRUE);

        Stream<Boolean> testParametersStream = this.parameters.stream().map(predicate -> predicate.test(context));
        boolean testParameters = this.parametersAnd ? testParametersStream.allMatch(b -> b) :
            testParametersStream.anyMatch(b -> b);
        testParameters = testParameters || this.parameters.isEmpty();

        return testBiome && testParameters && this.chance.get(random);
    }

    public boolean test(BiomeContext context, LayerRandomnessSource random) {
        boolean testBiome = this.biome.map(predicate -> predicate.test(context.biome(), random)).orElse(Boolean.TRUE);

        Stream<Boolean> testParametersStream = this.parameters.stream().map(predicate -> predicate.test(context));
        boolean testParameters = this.parametersAnd ? testParametersStream.allMatch(b -> b) :
            testParametersStream.anyMatch(b -> b);
        testParameters = testParameters || this.parameters.isEmpty();

        return testBiome && testParameters && this.chance.get(random);
    }
}
