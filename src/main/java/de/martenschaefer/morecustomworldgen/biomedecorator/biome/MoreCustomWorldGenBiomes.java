package de.martenschaefer.morecustomworldgen.biomedecorator.biome;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeCreator;
import de.martenschaefer.morecustomworldgen.MoreCustomWorldGenMod;

public final class MoreCustomWorldGenBiomes {
    private MoreCustomWorldGenBiomes() {
    }

    public static final RegistryKey<Biome> DRY = register("dry", DefaultBiomeCreator.createDesert(0.125F, 0.05F, true, true, true));
    public static final RegistryKey<Biome> TEMPERATE = register("temperate", DefaultBiomeCreator.createPlains(false));
    public static final RegistryKey<Biome> COOL = register("cool", DefaultBiomeCreator.createTaiga(0.2F, 0.2F, false, false, true, false));
    public static final RegistryKey<Biome> SNOWY = register("snowy", DefaultBiomeCreator.createSnowyTundra(0.125F, 0.05F, false, false));

    public static final RegistryKey<Biome> SPECIAL_DRY = register("special_dry", DefaultBiomeCreator.createNormalBadlands(0.1F, 0.2F, false));
    public static final RegistryKey<Biome> SPECIAL_TEMPERATE = register("special_temperate", DefaultBiomeCreator.createJungle());
    public static final RegistryKey<Biome> SPECIAL_COOL = register("special_cool", DefaultBiomeCreator.createSwamp(-0.2F, 0.1F, false));
    public static final RegistryKey<Biome> SPECIAL_SNOWY = register("special_snowy", DefaultBiomeCreator.createSnowyTundra(0.425F, 0.45000002F, true, false));

    public static RegistryKey<Biome> register(String name, Biome biome) {
        Identifier id = MoreCustomWorldGenMod.id(name);
        Registry.register(BuiltinRegistries.BIOME, id, biome);

        return RegistryKey.of(Registry.BIOME_KEY, id);
    }

    public static void init() {
    }
}
