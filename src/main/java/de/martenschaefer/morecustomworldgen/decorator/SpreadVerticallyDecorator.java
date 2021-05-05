package de.martenschaefer.morecustomworldgen.decorator;

import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import com.mojang.serialization.Codec;

public class SpreadVerticallyDecorator extends Decorator<SpreadVerticallyDecoratorConfig> {
    public SpreadVerticallyDecorator(Codec<SpreadVerticallyDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, SpreadVerticallyDecoratorConfig config, BlockPos pos) {
        return pos.getY() == context.getBottomY() ? Stream.of() : Stream.of(new BlockPos(pos.getX(), context.getBottomY() + random.nextInt((pos.getY() - context.getBottomY()) * config.getFactor()), pos.getZ()));
    }
}
