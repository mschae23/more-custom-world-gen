package de.martenschaefer.morecustomworldgen.terrainlayered;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenRegistries;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.VanillaLayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.terrainlayered.impl.CachingLayerTerrainBiomeSampler;

public interface TerrainLayer {
    Codec<TerrainLayer> CODEC = MoreCustomWorldGenRegistries.TERRAIN_LAYER.dispatchStable(TerrainLayer::getCodec, Function.identity());

    Codec<? extends TerrainLayer> getCodec();

    Optional<RegistryKey<Biome>> getBiome(LayerRandomnessSource random, TerrainBiomeSampler parent, int x, int y, int z, TerrainSamplerContext context);

    void init(LayerRandomnessSource random);

    Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry);

    default TerrainBiomeSampler createSampler(long seed, long salt, TerrainBiomeSampler parent) {
        LayerRandomnessSource random = new VanillaLayerRandomnessSource(seed, salt);
        this.init(random);

        return new CachingLayerTerrainBiomeSampler(random, parent, this);
    }
}
