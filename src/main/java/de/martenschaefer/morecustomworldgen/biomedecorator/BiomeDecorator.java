package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public interface BiomeDecorator {
    Codec<? extends BiomeDecorator> getCodec();

    Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry);

    BiomeSampler createSampler(long seed, long salt, BiomeSampler parent, Registry<Biome> biomeRegistry);
}
