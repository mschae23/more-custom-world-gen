package de.martenschaefer.morecustomworldgen.feature;

import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.FeatureConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class FillLayersFeatureConfig implements FeatureConfig {
    public static final Codec<FillLayersFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RuleTest.TYPE_CODEC.fieldOf("target").forGetter(FillLayersFeatureConfig::getTarget),
            YOffset.OFFSET_CODEC.fieldOf("bottom_inclusive").forGetter(FillLayersFeatureConfig::getMinY),
            YOffset.OFFSET_CODEC.fieldOf("top_inclusive").forGetter(FillLayersFeatureConfig::getMaxY),
            BlockState.CODEC.fieldOf("state").forGetter(FillLayersFeatureConfig::getState)
        ).apply(instance, FillLayersFeatureConfig::new)
    );

    private final RuleTest target;
    private final YOffset minY;
    private final YOffset maxY;
    private final BlockState state;

    public FillLayersFeatureConfig(RuleTest target, YOffset minY, YOffset maxY, BlockState state) {
        this.target = target;
        this.minY = minY;
        this.maxY = maxY;
        this.state = state;
    }

    public RuleTest getTarget() {
        return this.target;
    }

    public YOffset getMinY() {
        return this.minY;
    }

    public YOffset getMaxY() {
        return this.maxY;
    }

    public BlockState getState() {
        return this.state;
    }
}
