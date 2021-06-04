package de.martenschaefer.morecustomworldgen;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.fabricmc.api.ModInitializer;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorators;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratedBiomeSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.CustomLayeredBiomeSource;

public class MoreCustomWorldGenMod implements ModInitializer {
    public static final String MODID = "morecustomworldgen";
    
    @Override
    public void onInitialize() {
        Registry.register(Registry.BIOME_SOURCE, id("custom_layered"), CustomLayeredBiomeSource.CODEC);
        Registry.register(Registry.BIOME_SOURCE, id("decorated"), DecoratedBiomeSource.CODEC);

        MoreCustomWorldGenDecorators.init();
        MoreCustomWorldGenFeatures.init();
        BiomeDecorators.register();
    }

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }
}
