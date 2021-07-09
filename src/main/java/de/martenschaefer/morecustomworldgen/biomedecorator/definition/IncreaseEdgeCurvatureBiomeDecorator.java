package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.List;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.DiagonalCrossSamplingBiomeDecorator;

public class IncreaseEdgeCurvatureBiomeDecorator extends DiagonalCrossSamplingBiomeDecorator {
    public static final Codec<IncreaseEdgeCurvatureBiomeDecorator> CODEC = Codec.unit(IncreaseEdgeCurvatureBiomeDecorator::new);

    // TODO make this a lot more configurable

    @Override
    protected Codec<IncreaseEdgeCurvatureBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, RegistryKey<Biome> sw, RegistryKey<Biome> se, RegistryKey<Biome> ne, RegistryKey<Biome> nw, RegistryKey<Biome> center) {
        if (BiomeKeys.FOREST.getValue().equals(center.getValue()))
            return BiomeKeys.FOREST;

        boolean isCenterOcean = isOcean(center);
        boolean isOceanNW = isOcean(nw);
        boolean isOceanNE = isOcean(ne);
        boolean isOceanSW = isOcean(sw);
        boolean isOceanSE = isOcean(se);

        if (isCenterOcean && (!isOceanNW || !isOceanNE || !isOceanSW || !isOceanSE)) {
            int n = 1;
            RegistryKey<Biome> o = BiomeKeys.PLAINS;

            if (!isOceanNW && random.nextInt(n++) == 0) {
                o = nw;
            }

            if (!isOceanNE && random.nextInt(n++) == 0) {
                o = ne;
            }

            if (!isOceanSW && random.nextInt(n++) == 0) {
                o = sw;
            }

            if (!isOceanSE && random.nextInt(n) == 0) {
                o = se;
            }

            if (random.nextInt(3) == 0) {
                return o;
            }

            return center;
        }

        if (!isCenterOcean && (isOceanNW || isOceanNE || isOceanSW || isOceanSE) && random.nextInt(5) == 0) {
            if (isOceanNW)
                return nw;
            if (isOceanNE)
                return ne;
            if (isOceanSW)
                return sw;

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
