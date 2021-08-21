package de.martenschaefer.morecustomworldgen.test.multinoise;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

public final class NetherBiomeParameters {
    private NetherBiomeParameters() {
    }

    public static void writeNetherBiomeParameters(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
    }
}
