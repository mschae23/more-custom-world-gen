package de.martenschaefer.morecustomworldgen.decorator;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.decorator.CountNoiseBiasedDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorContext;
import de.martenschaefer.morecustomworldgen.util.OctaveSimplexNoiseSampler3D;

public class CountNoiseBiased3DDecorator extends Decorator<CountNoiseBiasedDecoratorConfig> {
    public static final OctaveSimplexNoiseSampler3D NOISE_SAMPLER = new OctaveSimplexNoiseSampler3D(new ChunkRandom(2345L), ImmutableList.of(0));

    public CountNoiseBiased3DDecorator(Codec<CountNoiseBiasedDecoratorConfig> codec) {
        super(codec);
    }

    public Stream<BlockPos> getPositions(DecoratorContext context, Random random, CountNoiseBiasedDecoratorConfig config, BlockPos pos) {
        double d = NOISE_SAMPLER.sample((double) pos.getX() / config.noiseFactor, (double) pos.getY() / config.noiseFactor, (double) pos.getZ() / config.noiseFactor, false);
        int i = (int) Math.ceil((d + config.noiseOffset) * (double) config.noiseToCountRatio);
        return IntStream.range(0, i).mapToObj((ix) -> pos);
    }
}
