package de.martenschaefer.morecustomworldgen.decorator;

import java.util.Random;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;

public class OffsetVerticallyDecorator extends Decorator<OffsetDecoratorConfig> {
    public OffsetVerticallyDecorator(Codec<OffsetDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, OffsetDecoratorConfig config, BlockPos pos) {
        return Stream.of(pos.withY((pos.getY() + config.getOffset()) * config.getFactor()));
    }
}
