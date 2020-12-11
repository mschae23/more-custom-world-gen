package de.martenschaefer.morecustomworldgen;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.fabricmc.api.ModInitializer;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.CustomLayeredBiomeSource;

public class MoreCustomWorldGenMod implements ModInitializer {
    public static final String MODID = "layeredbiomesource";
    
    @Override
    public void onInitialize() {
        Registry.register(Registry.BIOME_SOURCE, new Identifier(MODID, "custom_layered"), CustomLayeredBiomeSource.CODEC);
    }
}
