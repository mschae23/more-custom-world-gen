package de.martenschaefer.morecustomworldgen.util;

import java.util.Random;
import java.util.function.Function;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Chance {
    public static final Codec<Chance> INT_CODEC = Codec.INT.xmap(i -> new Chance(1, i), Chance::getDenominator);

    public static final Codec<Chance> FRACTION_CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("numerator").forGetter(Chance::getNumerator),
            Codec.INT.fieldOf("denominator").forGetter(Chance::getDenominator)
        ).apply(instance, instance.stable(Chance::new))
    );

    public static final Codec<Chance> CODEC = Codec.either(INT_CODEC, FRACTION_CODEC).xmap(
        either -> either.map(Function.identity(), Function.identity()),
        chance -> chance.getNumerator() == 1 ? Either.left(chance) : Either.right(chance)
    );

    private final int numerator;
    private final int denominator;

    public Chance(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public boolean get(Random random) {
        return random.nextInt(this.denominator) < this.numerator;
    }

    public boolean get(DecoratorRandomnessSource random) {
        return random.nextInt(this.denominator) < this.numerator;
    }

    public static Chance simple(int denominator) {
        return new Chance(1, denominator);
    }

    public static Chance fraction(int numerator, int denominator) {
        return new Chance(numerator, denominator);
    }

    public static Chance always() {
        return simple(1);
    }

    public static Chance never() {
        return fraction(0, 1);
    }
}
