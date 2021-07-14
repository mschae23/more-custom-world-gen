package de.martenschaefer.morecustomworldgen.terrainlayered;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenMod;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenRegistries;
import de.martenschaefer.morecustomworldgen.terrainlayered.layer.NoiseBasedBiomeNestedTerrainLayer;
import de.martenschaefer.morecustomworldgen.terrainlayered.layer.NoiseBasedBiomeTerrainLayer;

public final class TerrainLayers {
    private TerrainLayers() {
    }

    private static void register(String name, Codec<? extends TerrainLayer> codec) {
        Registry.register(MoreCustomWorldGenRegistries.TERRAIN_LAYER, MoreCustomWorldGenMod.id(name), codec);
    }

    public static void register() {
        register("noise_based_biome", NoiseBasedBiomeTerrainLayer.CODEC);
        register("noise_based_biome_nested", NoiseBasedBiomeNestedTerrainLayer.CODEC);
    }
}
