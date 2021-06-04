package de.martenschaefer.morecustomworldgen;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.FabricRegistryBuilderUtil;
import com.mojang.serialization.Codec;

public final class MoreCustomWorldGenRegistries {
    public static final RegistryKey<Registry<Codec<BiomeDecorator>>> BIOME_DECORATOR_KEY = RegistryKey.ofRegistry(new Identifier(MoreCustomWorldGenMod.MODID, "worldgen/biome_decorator"));
    public static final Registry<Codec<? extends BiomeDecorator>> BIOME_DECORATOR = FabricRegistryBuilderUtil.<Codec<? extends BiomeDecorator>>createSimple(BIOME_DECORATOR_KEY.getValue())
        .buildAndRegister();

    private MoreCustomWorldGenRegistries() {
    }

    public void init() {
    }
}
