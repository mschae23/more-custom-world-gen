package de.martenschaefer.morecustomworldgen.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.DecoratorConfig;

public class RangeMaskDecoratorConfig implements DecoratorConfig {
    public static final Codec<RangeMaskDecoratorConfig> CODEC = RecordCodecBuilder.create(instance -> 
        instance.group(
            YOffset.OFFSET_CODEC.fieldOf("bottom_inclusive").forGetter(RangeMaskDecoratorConfig::getBottom),
            YOffset.OFFSET_CODEC.fieldOf("top_inclusive").forGetter(RangeMaskDecoratorConfig::getTop)
        ).apply(instance, instance.stable(RangeMaskDecoratorConfig::new))
    );
    
    private final YOffset bottom;
    private final YOffset top;

    public RangeMaskDecoratorConfig(YOffset bottom, YOffset top) {
        this.bottom = bottom;
        this.top = top;
    }

    public YOffset getBottom() {
        return this.bottom;
    }

    public YOffset getTop() {
        return this.top;
    }
}
