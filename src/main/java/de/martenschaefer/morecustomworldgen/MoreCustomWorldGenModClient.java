package de.martenschaefer.morecustomworldgen;

import net.fabricmc.api.ClientModInitializer;
import de.martenschaefer.morecustomworldgen.biomedecorator.VanillaBiomeDecoratorsWorldType;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.ContinentsWorldType;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.CustomLayeredWorldType;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.LargeRiversWorldType;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainLayeredWorldType;

public class MoreCustomWorldGenModClient implements ClientModInitializer {
    public static CustomLayeredWorldType CUSTOM_LAYERED_WORLD_TYPE;
    public static LargeRiversWorldType LARGE_RIVERS_WORLD_TYPE;
    public static ContinentsWorldType CONTINENTS_WORLD_TYPE;
    public static VanillaBiomeDecoratorsWorldType VANILLA_BIOME_DECORATORS_WORLD_TYPE;
    public static TerrainLayeredWorldType TERRAIN_LAYERED_WORLD_TYPE;

    @Override
    public void onInitializeClient() {
        // CUSTOM_LAYERED_WORLD_TYPE = new CustomLayeredWorldType();
        // LARGE_RIVERS_WORLD_TYPE = new LargeRiversWorldType();
        // CONTINENTS_WORLD_TYPE = new ContinentsWorldType();
        // VANILLA_BIOME_DECORATORS_WORLD_TYPE = new VanillaBiomeDecoratorsWorldType();
        TERRAIN_LAYERED_WORLD_TYPE = new TerrainLayeredWorldType();
    }
}
