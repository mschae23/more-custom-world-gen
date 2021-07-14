package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.List;
import java.util.function.Function;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenRegistries;

public abstract class MergingBiomeDecorator {
    public static final Codec<MergingBiomeDecorator> CODEC = MoreCustomWorldGenRegistries.MERGING_BIOME_DECORATOR
        .dispatchStable(MergingBiomeDecorator::getCodec, Function.identity());

    protected abstract Codec<? extends MergingBiomeDecorator> getCodec();

    public abstract RegistryKey<Biome> getBiome(LayerRandomnessSource random, BiomeSampler parent, BiomeSampler parent2, int x, int y, int z);

    public abstract List<Biome> getBiomes(Registry<Biome> biomeRegistry);
}
