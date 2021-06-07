package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;

public record BiomeDecoratorEntry(long salt, BiomeDecorator decorator) {
    // public static final Codec<BiomeDecoratorEntry> CODEC = Codec.LONG.dispatch("salt", BiomeDecoratorEntry::getSalt, BiomeDecoratorEntry::getCodec);

    public static final Codec<BiomeDecoratorEntry> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.LONG.fieldOf("salt").forGetter(BiomeDecoratorEntry::salt),
            BiomeDecorator.CODEC.fieldOf("decorator").forGetter(BiomeDecoratorEntry::decorator)
        ).apply(instance, instance.stable(BiomeDecoratorEntry::new))
    );

    /* private static Codec<BiomeDecoratorEntry> getCodec(long salt) {
        return BiomeDecorator.CODEC.xmap(decorator -> new BiomeDecoratorEntry(salt, decorator), BiomeDecoratorEntry::decorator);
    } */
}
