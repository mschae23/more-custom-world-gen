package de.martenschaefer.morecustomworldgen;

import net.fabricmc.api.ClientModInitializer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.LargeRiversWorldType;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.LayeredWorldType;

public class MoreCustomWorldGenModClient implements ClientModInitializer {
    public static LayeredWorldType GENERATOR_TYPE;
    public static LargeRiversWorldType LARGE_RIVERS;

    
    @Override
    public void onInitializeClient() {
        GENERATOR_TYPE = new LayeredWorldType();
        LARGE_RIVERS = new LargeRiversWorldType();
    }
}
