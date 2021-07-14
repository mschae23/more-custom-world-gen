package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.List;
import java.util.function.Function;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenRegistries;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.CachingDecoratorBiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.VanillaLayerRandomnessSource;

public abstract class BiomeDecorator {
    public static final Codec<BiomeDecorator> CODEC = MoreCustomWorldGenRegistries.BIOME_DECORATOR.dispatchStable(BiomeDecorator::getCodec, Function.identity());

    protected abstract Codec<? extends BiomeDecorator> getCodec();

    public abstract RegistryKey<Biome> getBiome(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z);

    public abstract List<Biome> getBiomes(Registry<Biome> biomeRegistry);

    public BiomeSampler createSampler(long seed, long salt, BiomeSampler parent, Registry<Biome> biomeRegistry) {
        LayerRandomnessSource random = new VanillaLayerRandomnessSource(seed, salt);
        return new CachingDecoratorBiomeSampler(random, parent, this);
    }
}
