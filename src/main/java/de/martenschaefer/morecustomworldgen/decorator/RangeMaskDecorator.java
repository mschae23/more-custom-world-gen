package de.martenschaefer.morecustomworldgen.decorator;

import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.decorator.SimpleDecorator;
import com.mojang.serialization.Codec;

public class RangeMaskDecorator extends SimpleDecorator<RangeDecoratorConfig> {
    public RangeMaskDecorator(Codec<RangeDecoratorConfig> codec) {
        super(codec);
    }

    public Stream<BlockPos> getPositions(Random random, RangeDecoratorConfig config, BlockPos pos) {
        if (pos.getY() >= config.bottomOffset && pos.getY() < (config.maximum - config.topOffset + config.bottomOffset))
            return Stream.of(pos);
        else
            return Stream.empty();
    }
}
