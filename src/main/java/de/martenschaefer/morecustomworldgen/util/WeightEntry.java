package de.martenschaefer.morecustomworldgen.util;

import java.util.Objects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.collection.WeightedPicker;

public final class WeightEntry<T> extends WeightedPicker.Entry {
    private final int weight;
    private final T value;

    public WeightEntry(int weight, T value) {
        super(weight);
        this.weight = weight;
        this.value = value;
    }

    public int weight() {
        return weight;
    }

    public T value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        WeightEntry<?> that = (WeightEntry<?>) obj;
        return this.weight == that.weight &&
            Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, value);
    }

    @Override
    public String toString() {
        return "WeightEntry[" +
            "weight=" + weight + ", " +
            "value=" + value + ']';
    }

    public static <T> Codec<WeightEntry<T>> createCodec(String valueFieldName, Codec<T> valueCodec) {
        return RecordCodecBuilder.create(instance ->
            instance.group(
                Codec.INT.fieldOf("weight").forGetter(WeightEntry::weight),
                valueCodec.fieldOf(valueFieldName).forGetter(WeightEntry::value)
            ).apply(instance, instance.stable(WeightEntry::new))
        );
    }

    public static <T> Codec<WeightEntry<T>> createCodec(Codec<T> valueCodec) {
        return createCodec("value", valueCodec);
    }

    public static <T> WeightEntry<T> of(int weight, T value) {
        return new WeightEntry<>(weight, value);
    }

    public static <T> WeightEntry<T> one(T value) {
        return of(1, value);
    }
}
