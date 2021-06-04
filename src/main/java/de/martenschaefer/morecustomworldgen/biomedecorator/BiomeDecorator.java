package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.List;
import java.util.function.Function;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenRegistries;
import com.mojang.serialization.Codec;

public abstract class BiomeDecorator {
    public static final Codec<BiomeDecorator> CODEC = MoreCustomWorldGenRegistries.BIOME_DECORATOR.dispatchStable(BiomeDecorator::getCodec, Function.identity());

    protected abstract Codec<? extends BiomeDecorator> getCodec();

    public abstract RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, BiomeAccess.Storage parent, int x, int y, int z);

    public abstract List<Biome> getBiomes(Registry<Biome> biomeRegistry);
}
