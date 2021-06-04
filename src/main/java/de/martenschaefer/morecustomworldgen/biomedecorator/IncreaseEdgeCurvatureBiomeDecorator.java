package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.List;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.DiagonalCrossSamplingBiomeDecorator;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

public class IncreaseEdgeCurvatureBiomeDecorator extends DiagonalCrossSamplingBiomeDecorator {
    public static final Codec<IncreaseEdgeCurvatureBiomeDecorator> CODEC = Codec.unit(IncreaseEdgeCurvatureBiomeDecorator::new);

    // TODO make this a lot more configurable

    @Override
    protected Codec<? extends BiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, RegistryKey<Biome> sw, RegistryKey<Biome> se, RegistryKey<Biome> ne, RegistryKey<Biome> nw, RegistryKey<Biome> center) {
        if (!(!isOcean(center) || isOcean(nw) && isOcean(ne) && isOcean(sw) && isOcean(se))) {
            int n = 1;
            RegistryKey<Biome> o = BiomeKeys.PLAINS;

            if (!isOcean(nw) && random.nextInt(n++) == 0) {
                o = nw;
            }

            if (!isOcean(ne) && random.nextInt(n++) == 0) {
                o = ne;
            }

            if (!isOcean(sw) && random.nextInt(n++) == 0) {
                o = sw;
            }

            if (!isOcean(se) && random.nextInt(n) == 0) {
                o = se;
            }

            if (random.nextInt(3) == 0) {
                return o;
            }

            return BiomeKeys.FOREST.getValue().equals(o.getValue()) ? BiomeKeys.FOREST : center;
        }

        if (!isOcean(center) && (isOcean(nw) || isOcean(sw) || isOcean(ne) || isOcean(se)) && random.nextInt(5) == 0) {
            if (BiomeKeys.FOREST.getValue().equals(center.getValue()))
                return center;

            if (isOcean(nw))
                return nw;
            if (isOcean(sw))
                return sw;
            if (isOcean(ne))
                return ne;
            if (isOcean(se))
                return se;
        }

        return center;
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return ImmutableList.of();
    }

    private static boolean isOcean(RegistryKey<Biome> id) { // TODO config for this
        return BiomeKeys.OCEAN.getValue().equals(id.getValue())
            || BiomeKeys.WARM_OCEAN.getValue().equals(id.getValue())
            || BiomeKeys.LUKEWARM_OCEAN.getValue().equals(id.getValue())
            || BiomeKeys.COLD_OCEAN.getValue().equals(id.getValue())
            || BiomeKeys.FROZEN_OCEAN.getValue().equals(id.getValue());
    }
}
