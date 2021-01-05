package de.martenschaefer.morecustomworldgen.decorator;

import net.minecraft.world.gen.decorator.DecoratorConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class OffsetDecoratorConfig implements DecoratorConfig {
    public static final Codec<OffsetDecoratorConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("offset").forGetter(OffsetDecoratorConfig::getOffset),
            Codec.INT.fieldOf("factor").orElse(1).forGetter(OffsetDecoratorConfig::getFactor)
        ).apply(instance, instance.stable(OffsetDecoratorConfig::new))
    );

    private final int offset;
    private final int factor;

    public OffsetDecoratorConfig(int offset, int factor) {
        this.offset = offset;
        this.factor = factor;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getFactor() {
        return this.factor;
    }
}
