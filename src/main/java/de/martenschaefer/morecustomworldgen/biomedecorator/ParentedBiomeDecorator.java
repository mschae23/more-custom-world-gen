package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.function.Function;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenRegistries;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.CachingDecoratorBiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.VanillaLayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public interface ParentedBiomeDecorator extends BiomeDecorator {
    Codec<ParentedBiomeDecorator> CODEC = MoreCustomWorldGenRegistries.PARENTED_BIOME_DECORATOR.dispatchStable(ParentedBiomeDecorator::getCodec, Function.identity());

    BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z);

    Codec<? extends ParentedBiomeDecorator> getCodec();

    @Override
    default BiomeSampler createSampler(long seed, long salt, BiomeSampler parent, Registry<Biome> biomeRegistry) {
        LayerRandomnessSource random = new VanillaLayerRandomnessSource(seed, salt);
        return new CachingDecoratorBiomeSampler(random, parent, this);
    }
}
