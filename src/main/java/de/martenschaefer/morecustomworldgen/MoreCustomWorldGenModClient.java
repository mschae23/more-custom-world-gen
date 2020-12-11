package de.martenschaefer.morecustomworldgen;

import net.fabricmc.api.ClientModInitializer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.LayeredWorldType;

public class MoreCustomWorldGenModClient implements ClientModInitializer {
    public static LayeredWorldType GENERATOR_TYPE;
    
    @Override
    public void onInitializeClient() {
        GENERATOR_TYPE = new LayeredWorldType();
    }
}
