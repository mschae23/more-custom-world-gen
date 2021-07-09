package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.List;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;

public class NopeBiomeDecorator extends BiomeDecorator {
    public static final Codec<NopeBiomeDecorator> CODEC = Codec.unit(NopeBiomeDecorator::new);

    @Override
    protected Codec<? extends BiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return ImmutableList.of();
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        return parent.sample(x, y, z);
    }
}
