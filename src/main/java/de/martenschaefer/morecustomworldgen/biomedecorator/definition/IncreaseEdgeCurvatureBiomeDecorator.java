package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.DiagonalCrossSamplingBiomeDecorator;

public class IncreaseEdgeCurvatureBiomeDecorator implements DiagonalCrossSamplingBiomeDecorator {
    public static final IncreaseEdgeCurvatureBiomeDecorator INSTANCE = new IncreaseEdgeCurvatureBiomeDecorator();

    public static final Codec<IncreaseEdgeCurvatureBiomeDecorator> CODEC = Codec.unit(() -> INSTANCE);

    // TODO make this a lot more configurable

    private IncreaseEdgeCurvatureBiomeDecorator() {
    }

    @Override
    public Codec<IncreaseEdgeCurvatureBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeContext sample(LayerRandomnessSource random, BiomeContext sw, BiomeContext se, BiomeContext ne, BiomeContext nw, BiomeContext center) {
        if (BiomeKeys.FOREST.getValue().equals(center.biome().getValue()))
            return center;

        boolean isCenterOcean = isOcean(center);
        boolean isOceanNW = isOcean(nw);
        boolean isOceanNE = isOcean(ne);
        boolean isOceanSW = isOcean(sw);
        boolean isOceanSE = isOcean(se);

        if (isCenterOcean && (!isOceanNW || !isOceanNE || !isOceanSW || !isOceanSE)) {
            int n = 1;
            BiomeContext context = center.withBiome(BiomeKeys.PLAINS);

            if (!isOceanNW && random.nextInt(n++) == 0) {
                context = center.withBiome(nw.biome());
            }

            if (!isOceanNE && random.nextInt(n++) == 0) {
                context = center.withBiome(ne.biome());
            }

            if (!isOceanSW && random.nextInt(n++) == 0) {
                context = center.withBiome(sw.biome());
            }

            if (!isOceanSE && random.nextInt(n) == 0) {
                context = center.withBiome(se.biome());
            }

            if (random.nextInt(3) == 0) {
                return context;
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
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return Stream.empty();
    }

    private static boolean isOcean(BiomeContext context) { // TODO config for this
        return BiomeKeys.OCEAN.getValue().equals(context.biome().getValue())
            || BiomeKeys.WARM_OCEAN.getValue().equals(context.biome().getValue())
            || BiomeKeys.LUKEWARM_OCEAN.getValue().equals(context.biome().getValue())
            || BiomeKeys.COLD_OCEAN.getValue().equals(context.biome().getValue())
            || BiomeKeys.FROZEN_OCEAN.getValue().equals(context.biome().getValue());
    }
}
