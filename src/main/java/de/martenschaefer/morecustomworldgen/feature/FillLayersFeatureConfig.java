package de.martenschaefer.morecustomworldgen.feature;

import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.FeatureConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class FillLayersFeatureConfig implements FeatureConfig {
    public static final Codec<FillLayersFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            RuleTest.TYPE_CODEC.fieldOf("target").forGetter(FillLayersFeatureConfig::getTarget),
            Codec.intRange(DimensionType.MIN_HEIGHT, DimensionType.MAX_COLUMN_HEIGHT).fieldOf("start_height").forGetter(FillLayersFeatureConfig::getStartHeight),
            Codec.intRange(DimensionType.MIN_HEIGHT, DimensionType.MAX_COLUMN_HEIGHT).fieldOf("height").forGetter(FillLayersFeatureConfig::getHeight),
            BlockState.CODEC.fieldOf("state").forGetter(FillLayersFeatureConfig::getState)
        ).apply(instance, FillLayersFeatureConfig::new)
    );

    private final RuleTest target;
    private final int startHeight;
    private final int height;
    private final BlockState state;

    public FillLayersFeatureConfig(RuleTest target, int startHeight, int height, BlockState state) {
        if(startHeight + height > DimensionType.MAX_COLUMN_HEIGHT) throw new IllegalArgumentException("Illegal height: " + (startHeight + height));

        this.target = target;
        this.startHeight = startHeight;
        this.height = height;
        this.state = state;
    }

    public RuleTest getTarget() {
        return this.target;
    }

    public int getStartHeight() {
        return this.startHeight;
    }

    public int getHeight() {
        return this.height;
    }

    public BlockState getState() {
        return this.state;
    }
}
