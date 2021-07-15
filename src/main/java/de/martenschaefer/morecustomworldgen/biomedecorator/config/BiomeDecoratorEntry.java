package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;

public record BiomeDecoratorEntry(long salt, ParentedBiomeDecorator decorator) {
    public static final Codec<BiomeDecoratorEntry> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.LONG.fieldOf("salt").forGetter(BiomeDecoratorEntry::salt),
            ParentedBiomeDecorator.CODEC.fieldOf("decorator").forGetter(BiomeDecoratorEntry::decorator)
        ).apply(instance, instance.stable(BiomeDecoratorEntry::new))
    );
}
