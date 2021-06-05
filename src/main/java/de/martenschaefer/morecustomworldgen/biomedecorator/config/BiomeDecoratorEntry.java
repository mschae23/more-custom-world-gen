package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class BiomeDecoratorEntry {
    // public static final Codec<BiomeDecoratorEntry> CODEC = Codec.LONG.dispatch("salt", BiomeDecoratorEntry::getSalt, BiomeDecoratorEntry::getCodec);

    public static final Codec<BiomeDecoratorEntry> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.LONG.fieldOf("salt").forGetter(BiomeDecoratorEntry::getSalt),
            BiomeDecorator.CODEC.fieldOf("decorator").forGetter(BiomeDecoratorEntry::getDecorator)
        ).apply(instance, instance.stable(BiomeDecoratorEntry::new))
    );

    private final long salt;
    private final BiomeDecorator decorator;

    public BiomeDecoratorEntry(long salt, BiomeDecorator decorator) {
        this.salt = salt;
        this.decorator = decorator;
    }

    public long getSalt() {
        return this.salt;
    }

    public BiomeDecorator getDecorator() {
        return this.decorator;
    }

    private static Codec<BiomeDecoratorEntry> getCodec(long salt) {
        return BiomeDecorator.CODEC.xmap(decorator -> new BiomeDecoratorEntry(salt, decorator), BiomeDecoratorEntry::getDecorator);
    }
}
