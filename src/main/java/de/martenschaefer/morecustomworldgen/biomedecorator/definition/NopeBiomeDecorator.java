package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public class NopeBiomeDecorator implements ParentedBiomeDecorator {
    public static final NopeBiomeDecorator INSTANCE = new NopeBiomeDecorator();

    public static final Codec<NopeBiomeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    private NopeBiomeDecorator() {
    }

    @Override
    public Codec<NopeBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return Stream.empty();
    }

    @Override
    public BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        return parent.sample(x, y, z);
    }
}
