package de.martenschaefer.morecustomworldgen.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ChanceEntry<T> {
    private final Chance chance;
    private final T value;

    public ChanceEntry(Chance chance, T value) {
        this.chance = chance;
        this.value = value;
    }

    public Chance getChance() {
        return this.chance;
    }

    public T getValue() {
        return this.value;
    }

    public static <T> Codec<ChanceEntry<T>> createCodec(Codec<T> valueCodec) {
        return createCodec(valueCodec, "value");
    }

    public static <T> Codec<ChanceEntry<T>> createCodec(Codec<T> valueCodec, String valueFieldName) {
        return RecordCodecBuilder.create(instance ->
            instance.group(
                Chance.CODEC.fieldOf("chance").forGetter(ChanceEntry::getChance),
                valueCodec.fieldOf(valueFieldName).forGetter(ChanceEntry::getValue)
            ).apply(instance, instance.stable(ChanceEntry::new))
        );
    }
}
