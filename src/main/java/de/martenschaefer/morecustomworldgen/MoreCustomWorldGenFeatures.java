package de.martenschaefer.morecustomworldgen;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DiskFeature;
import net.minecraft.world.gen.feature.DiskFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import de.martenschaefer.morecustomworldgen.feature.FillLayersFeature;
import de.martenschaefer.morecustomworldgen.feature.FillLayersFeatureConfig;

@SuppressWarnings("unused")
public class MoreCustomWorldGenFeatures {
    public static final Feature<DiskFeatureConfig> DISK = register("disk", new DiskFeature(DiskFeatureConfig.CODEC));
    public static final Feature<FillLayersFeatureConfig> FILL_LAYERS = register("fill_layers", new FillLayersFeature(FillLayersFeatureConfig.CODEC));

    private static <FC extends FeatureConfig, F extends Feature<FC>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, new Identifier(MoreCustomWorldGenMod.MODID, name), feature);
    }

    public static void init() {
    }
}
