package de.martenschaefer.morecustomworldgen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import com.mojang.serialization.Codec;

public class FillLayersFeature extends Feature<FillLayersFeatureConfig> {
    public FillLayersFeature(Codec<FillLayersFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(StructureWorldAccess world, ChunkGenerator generator, Random random, BlockPos pos, FillLayersFeatureConfig config) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int i = 0; i < config.getHeight(); i++) {
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    int x = pos.getX() + j;
                    int z = pos.getZ() + k;
                    int y = config.getStartHeight() + i;
                    mutable.set(x, y, z);

                    if (config.getTarget().test(world.getBlockState(mutable), random)) {
                        world.setBlockState(mutable, config.getState(), 2);
                    }
                }
            }
        }

        return true;
    }
}
