package de.martenschaefer.morecustomworldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class FillLayersFeature extends Feature<FillLayersFeatureConfig> {
    public FillLayersFeature(Codec<FillLayersFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<FillLayersFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        FillLayersFeatureConfig config = context.getConfig();
        BlockPos pos = context.getOrigin();

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        HeightContext heightContext = new HeightContext(context.getGenerator(), context.getWorld());
        int minY = config.getMinY().getY(heightContext);
        int maxY = config.getMaxY().getY(heightContext);

        for (int i = minY; i <= maxY; i++) {
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    int x = pos.getX() + j;
                    int z = pos.getZ() + k;
                    mutable.set(x, i, z);

                    if (config.getTarget().test(world.getBlockState(mutable), context.getRandom())) {
                        world.setBlockState(mutable, config.getState(), 2);
                    }
                }
            }
        }

        return true;
    }
}
