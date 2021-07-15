package de.martenschaefer.morecustomworldgen.terrainlayered;

import java.util.List;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import de.martenschaefer.morecustomworldgen.terrainlayered.layer.NoiseBasedBiomeNestedTerrainLayer;
import de.martenschaefer.morecustomworldgen.terrainlayered.layer.NoiseBasedBiomeTerrainLayer;
import de.martenschaefer.morecustomworldgen.terrainlayered.util.NoiseType;

public final class TerrainLayeredPresets {
    public static final MultiNoiseBiomeSource.NoiseParameters DEFAULT_CONTINENTALNESS_NOISE = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.0D, 2.0D, 2.0D, 2.0D, 1.0D, 1.0D, 1.0D, 1.0D);
    public static final MultiNoiseBiomeSource.NoiseParameters DEFAULT_EROSION_NOISE = new MultiNoiseBiomeSource.NoiseParameters(-9, 1.0D, 1.0D, 0.0D, 1.0D, 1.0D);
    public static final MultiNoiseBiomeSource.NoiseParameters DEFAULT_WEIRDNESS_NOISE = new MultiNoiseBiomeSource.NoiseParameters(-7, 1.0D, 2.0D, 1.0D, 0.0D, 0.0D, 0.0D);

    public static final List<TerrainLayerEntry> DEFAULT_LAND_LAYERS = ImmutableList.of(
        new TerrainLayerEntry(
            3000L,
            new NoiseBasedBiomeTerrainLayer(
                NoiseType.EROSION,
                0.6f, 1f,
                BiomeKeys.SWAMP
            )
        ),
        new TerrainLayerEntry(
            3001L,
            new NoiseBasedBiomeTerrainLayer(
                NoiseType.EROSION,
                0.05f, 1f,
                BiomeKeys.PLAINS
            )
        ),
        new TerrainLayerEntry(
            3002L,
            new NoiseBasedBiomeTerrainLayer(
                NoiseType.EROSION,
                -0.225f, 1f,
                BiomeKeys.GROVE
            )
        ),
        new TerrainLayerEntry(
            3003L,
            new NoiseBasedBiomeTerrainLayer(
                NoiseType.EROSION,
                -0.375f, 1f,
                BiomeKeys.SNOWY_SLOPES
            )
        ),
        new TerrainLayerEntry(
            3004L,
            new NoiseBasedBiomeTerrainLayer(
                NoiseType.EROSION,
                -1f, 1f,
                BiomeKeys.LOFTY_PEAKS
            )
        )
    );

    public static final List<TerrainLayerEntry> DEFAULT_LAYERS = ImmutableList.of(
        new TerrainLayerEntry(
            1000L,
            new NoiseBasedBiomeTerrainLayer(
                NoiseType.CONTINENTALNESS,
                -1f, -0.5f,
                BiomeKeys.DEEP_OCEAN
            )
        ),
        new TerrainLayerEntry(
            1001L,
            new NoiseBasedBiomeTerrainLayer(
                NoiseType.CONTINENTALNESS,
                -1f, -0.17f,
                BiomeKeys.OCEAN
            )
        ),
        new TerrainLayerEntry(
            1002L,
            new NoiseBasedBiomeNestedTerrainLayer(
                NoiseType.CONTINENTALNESS,
                -1f, 1f,
                ImmutableList.of(
                    new TerrainLayerEntry(
                        2000L,
                        new NoiseBasedBiomeNestedTerrainLayer(
                            NoiseType.WEIRDNESS,
                            0.05f, 1f,
                            DEFAULT_LAND_LAYERS
                        )
                    ),
                    new TerrainLayerEntry(
                        2001L,
                        new NoiseBasedBiomeTerrainLayer(
                            NoiseType.WEIRDNESS,
                            -0.05f, 1f,
                            BiomeKeys.RIVER
                        )
                    ),
                    new TerrainLayerEntry(
                        2002L,
                        new NoiseBasedBiomeNestedTerrainLayer(
                            NoiseType.WEIRDNESS,
                            -1f, 1f,
                            DEFAULT_LAND_LAYERS
                        )
                    )
                )
            )
        )
    );

    private TerrainLayeredPresets() {
    }

    public static TerrainLayeredBiomeSource createDefault(long seed, Registry<Biome> biomeRegistry) {
        return new TerrainLayeredBiomeSource(seed, DEFAULT_CONTINENTALNESS_NOISE, DEFAULT_EROSION_NOISE, DEFAULT_WEIRDNESS_NOISE, DEFAULT_LAYERS, biomeRegistry);
    }
}
