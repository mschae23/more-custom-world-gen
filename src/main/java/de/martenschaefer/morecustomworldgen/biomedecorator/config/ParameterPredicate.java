package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public record ParameterPredicate(ParameterType type, double min, double max) {
    public static final Codec<ParameterPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ParameterType.CODEC.fieldOf("parameter_type").forGetter(ParameterPredicate::type),
        Codec.DOUBLE.fieldOf("min").forGetter(ParameterPredicate::min),
        Codec.DOUBLE.fieldOf("max").forGetter(ParameterPredicate::max)
    ).apply(instance, instance.stable(ParameterPredicate::new)));

    public boolean test(BiomeContext context) {
        double parameter = context.getParameter(this.type);
        return parameter >= this.min && parameter <= this.max;
    }
}
