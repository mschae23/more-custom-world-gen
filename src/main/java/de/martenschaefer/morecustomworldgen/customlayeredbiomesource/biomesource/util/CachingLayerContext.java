package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util;

import java.util.Random;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.source.SeedMixer;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;

public class CachingLayerContext<T, T2, T3> implements LayerSampleContext<T, T2, T3, CachingLayerSampler<T>, CachingLayerSampler<T2>, CachingLayerSampler<T3>> {
    private final Long2ObjectLinkedOpenHashMap<T> cache;
    private final int cacheCapacity;
    private final PerlinNoiseSampler noiseSampler;
    private final long worldSeed;
    private long localSeed;

    public CachingLayerContext(int cacheCapacity, long seed, long salt) {
        this.worldSeed = addSalt(seed, salt);
        this.noiseSampler = new PerlinNoiseSampler(new Random(seed));
        this.cache = new Long2ObjectLinkedOpenHashMap<>(16, 0.25F);
        this.cache.defaultReturnValue(null);
        this.cacheCapacity = cacheCapacity;
    }

    @Override
    public CachingLayerSampler<T> createSampler(LayerOperator<T> layerOperator) {
        return new CachingLayerSampler<>(this.cache, this.cacheCapacity, layerOperator);
    }

    @Override
    public CachingLayerSampler<T> createSampler(LayerOperator<T> layerOperator, CachingLayerSampler<T2> parent) {
        return new CachingLayerSampler<>(this.cache, Math.min(1024, parent.getCapacity() * 4), layerOperator);
    }

    @Override
    public CachingLayerSampler<T> createSampler(LayerOperator<T> operator, CachingLayerSampler<T2> parent, CachingLayerSampler<T3> sampler2) {
        return new CachingLayerSampler<>(this.cache, Math.min(1024, Math.max(parent.getCapacity(), sampler2.getCapacity()) * 4), operator);
    }

    public void initSeed(long x, long y) {
        long l = this.worldSeed;
        l = SeedMixer.mixSeed(l, x);
        l = SeedMixer.mixSeed(l, y);
        l = SeedMixer.mixSeed(l, x);
        l = SeedMixer.mixSeed(l, y);
        this.localSeed = l;
    }

    public int nextInt(int bound) {
        int i = (int) Math.floorMod(this.localSeed >> 24, bound);
        this.localSeed = SeedMixer.mixSeed(this.localSeed, this.worldSeed);
        return i;
    }

    public PerlinNoiseSampler getNoiseSampler() {
        return this.noiseSampler;
    }

    private static long addSalt(long seed, long salt) {
        long l = SeedMixer.mixSeed(salt, salt);
        l = SeedMixer.mixSeed(l, salt);
        l = SeedMixer.mixSeed(l, salt);
        long m = SeedMixer.mixSeed(seed, l);
        m = SeedMixer.mixSeed(m, l);
        m = SeedMixer.mixSeed(m, l);
        return m;
    }
}
