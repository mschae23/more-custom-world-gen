package de.martenschaefer.morecustomworldgen.decorator;

import java.util.Random;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import com.mojang.serialization.Codec;

public class RangeMaskDecorator extends Decorator<RangeMaskDecoratorConfig> {
    private static final Logger LOGGER = LogManager.getLogger();

    public RangeMaskDecorator(Codec<RangeMaskDecoratorConfig> codec) {
        super(codec);
    }

    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, RangeMaskDecoratorConfig config, BlockPos pos) {
        int minY = config.getBottom().getY(context);
        int maxY = config.getTop().getY(context);
        
        if (minY >= maxY) {
            LOGGER.warn("Empty range: {} [{}-{}]", this, minY, maxY);
            return Stream.empty();
        } else
            return Stream.of(pos);
    }
}
