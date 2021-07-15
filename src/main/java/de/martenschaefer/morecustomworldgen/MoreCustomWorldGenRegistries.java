package de.martenschaefer.morecustomworldgen;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.MergingBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainLayer;
import de.martenschaefer.morecustomworldgen.util.FabricRegistryBuilderUtil;

public final class MoreCustomWorldGenRegistries {
    public static final RegistryKey<Registry<Codec<ParentedBiomeDecorator>>> PARENTED_BIOME_DECORATOR_KEY = RegistryKey.ofRegistry(new Identifier(MoreCustomWorldGenMod.MODID, "worldgen/parented_biome_decorator"));
    public static final Registry<Codec<? extends ParentedBiomeDecorator>> PARENTED_BIOME_DECORATOR = FabricRegistryBuilderUtil.<Codec<? extends ParentedBiomeDecorator>>createSimple(PARENTED_BIOME_DECORATOR_KEY.getValue())
        .buildAndRegister();

    public static final RegistryKey<Registry<Codec<BiomeDecorator>>> MERGING_BIOME_DECORATOR_KEY = RegistryKey.ofRegistry(
        new Identifier(MoreCustomWorldGenMod.MODID, "worldgen/merging_biome_decorator"));
    public static final Registry<Codec<? extends MergingBiomeDecorator>> MERGING_BIOME_DECORATOR =
        FabricRegistryBuilderUtil.<Codec<? extends MergingBiomeDecorator>>createSimple(MERGING_BIOME_DECORATOR_KEY.getValue())
            .buildAndRegister();

    public static final RegistryKey<Registry<Codec<TerrainLayer>>> TERRAIN_LAYER_KEY = RegistryKey.ofRegistry(new Identifier(MoreCustomWorldGenMod.MODID, "worldgen/terrain_layer"));
    public static final Registry<Codec<? extends TerrainLayer>> TERRAIN_LAYER = FabricRegistryBuilderUtil.<Codec<? extends TerrainLayer>>createSimple(TERRAIN_LAYER_KEY.getValue())
        .buildAndRegister();

    private MoreCustomWorldGenRegistries() {
    }

    public void init() {
    }
}
