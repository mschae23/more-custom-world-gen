package de.martenschaefer.morecustomworldgen;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.fabricmc.api.ModInitializer;
import de.martenschaefer.morecustomworldgen.biomedecorator.ArrayDecoratedBiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorators;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratedBiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.biome.MoreCustomWorldGenBiomes;
import de.martenschaefer.morecustomworldgen.command.SaveBiomeMapCommand;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.CustomLayeredBiomeSource;

public class MoreCustomWorldGenMod implements ModInitializer {
    public static final String MODID = "morecustomworldgen";

    @Override
    public void onInitialize() {
        Registry.register(Registry.BIOME_SOURCE, id("custom_layered"), CustomLayeredBiomeSource.CODEC);
        Registry.register(Registry.BIOME_SOURCE, id("decorated"), DecoratedBiomeSource.CODEC);
        Registry.register(Registry.BIOME_SOURCE, id("array_decorated"), ArrayDecoratedBiomeSource.CODEC);

        MoreCustomWorldGenDecorators.init();
        MoreCustomWorldGenFeatures.init();
        MoreCustomWorldGenBiomes.init();
        BiomeDecorators.register();

        SaveBiomeMapCommand.init();
    }

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    public static boolean equals(RegistryKey<Biome> i, RegistryKey<Biome> j) {
        return i.getValue().equals(j.getValue());
    }
}
