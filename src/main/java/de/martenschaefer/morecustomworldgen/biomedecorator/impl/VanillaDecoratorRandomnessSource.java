package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.source.SeedMixer;
import net.minecraft.world.gen.SimpleRandom;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;

public class VanillaDecoratorRandomnessSource implements DecoratorRandomnessSource {
    private final long worldSeed;
    private long localSeed;
    private final PerlinNoiseSampler noiseSampler;

    public VanillaDecoratorRandomnessSource(long seed, long salt) {
        this.worldSeed = addSalt(seed, salt);
        this.noiseSampler = new PerlinNoiseSampler(new SimpleRandom(seed));
    }

    @Override
    public void initSeed(long x, long y, long z) {
        long l = this.worldSeed;
        l = SeedMixer.mixSeed(l, x);
        l = SeedMixer.mixSeed(l, y);
        if (z != 0L) l = SeedMixer.mixSeed(l, z);
        l = SeedMixer.mixSeed(l, x);
        l = SeedMixer.mixSeed(l, y);
        if (z != 0L) l = SeedMixer.mixSeed(l, z);

        this.localSeed = l;
    }

    @Override
    public int nextInt(int bound) {
        int i = (int) Math.floorMod(this.localSeed >> 24, bound);
        this.localSeed = SeedMixer.mixSeed(this.localSeed, this.worldSeed);
        return i;
    }

    @Override
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
