package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.function.Function;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenRegistries;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.CachingMergingDecoratorBiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.VanillaLayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public interface MergingBiomeDecorator {
    Codec<MergingBiomeDecorator> CODEC = MoreCustomWorldGenRegistries.MERGING_BIOME_DECORATOR
        .dispatchStable(MergingBiomeDecorator::getCodec, Function.identity());

    Codec<? extends MergingBiomeDecorator> getCodec();

    BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, BiomeSampler parent2, int x, int y, int z);

    default BiomeSampler createSampler(long seed, long salt, BiomeSampler parent, BiomeSampler parent2, Registry<Biome> biomeRegistry) {
        LayerRandomnessSource random = new VanillaLayerRandomnessSource(seed, salt);
        return new CachingMergingDecoratorBiomeSampler(random, parent, parent2, this);
    }
}
