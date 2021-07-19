package de.martenschaefer.morecustomworldgen.util;

import java.util.Random;
import java.util.function.Function;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;

public record Chance(int numerator, int denominator) {
    public static final Chance ALWAYS = simple(1);
    public static final Chance NEVER = fraction(0, 1);

    public static final Codec<Chance> INT_CODEC = Codec.INT.xmap(i -> new Chance(1, i), Chance::denominator);

    public static final Codec<Chance> FRACTION_CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("numerator").forGetter(Chance::numerator),
            Codec.INT.fieldOf("denominator").forGetter(Chance::denominator)
        ).apply(instance, instance.stable(Chance::new))
    );

    public static final Codec<Chance> CODEC = Codec.either(INT_CODEC, FRACTION_CODEC).xmap(
        either -> either.map(Function.identity(), Function.identity()),
        chance -> chance.numerator() == 1 ? Either.left(chance) : Either.right(chance)
    );

    public boolean get(Random random) {
        return random.nextInt(this.denominator) < this.numerator;
    }

    public boolean get(LayerRandomnessSource random) {
        return random.nextInt(this.denominator) < this.numerator;
    }

    public static Chance simple(int denominator) {
        return new Chance(1, denominator);
    }

    public static Chance fraction(int numerator, int denominator) {
        return new Chance(numerator, denominator);
    }

    public static Chance always() {
        return ALWAYS;
    }

    public static Chance never() {
        return NEVER;
    }
}
