package de.martenschaefer.morecustomworldgen.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ChanceEntry<T>(Chance chance, T value) {
    public static <T> Codec<ChanceEntry<T>> createCodec(Codec<T> valueCodec) {
        return createCodec(valueCodec, "value");
    }

    public static <T> Codec<ChanceEntry<T>> createCodec(Codec<T> valueCodec, String valueFieldName) {
        return RecordCodecBuilder.create(instance ->
            instance.group(
                Chance.CODEC.fieldOf("chance").forGetter(ChanceEntry::chance),
                valueCodec.fieldOf(valueFieldName).forGetter(ChanceEntry::value)
            ).apply(instance, instance.stable(ChanceEntry::new))
        );
    }
}
