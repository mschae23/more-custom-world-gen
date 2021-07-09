package de.martenschaefer.morecustomworldgen.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.decorator.DecoratorConfig;

public class SpreadVerticallyDecoratorConfig implements DecoratorConfig {
    public static final Codec<SpreadVerticallyDecoratorConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("factor").orElse(1).forGetter(SpreadVerticallyDecoratorConfig::getFactor)
        ).apply(instance, instance.stable(SpreadVerticallyDecoratorConfig::new))
    );

    private final int factor;

    public SpreadVerticallyDecoratorConfig(int factor) {
        this.factor = factor;
    }

    public int getFactor() {
        return this.factor;
    }
}
