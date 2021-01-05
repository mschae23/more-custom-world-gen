package de.martenschaefer.morecustomworldgen.decorator;

import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.decorator.SimpleDecorator;
import com.mojang.serialization.Codec;

public class OffsetVerticallyDecorator extends SimpleDecorator<OffsetDecoratorConfig> {
    public OffsetVerticallyDecorator(Codec<OffsetDecoratorConfig> codec) {
        super(codec);
    }

    @Override
    protected Stream<BlockPos> getPositions(Random random, OffsetDecoratorConfig config, BlockPos pos) {
        return Stream.of(pos.withY((pos.getY() + config.getOffset()) * config.getFactor()));
    }
}
