package de.martenschaefer.morecustomworldgen.biomedecorator;

import net.minecraft.util.math.noise.PerlinNoiseSampler;

public interface DecoratorRandomnessSource {
    void initSeed(long x, long y);

    int nextInt(int bound);

    PerlinNoiseSampler getNoiseSampler();

    default <T> T choose(T a, T b) {
        return this.nextInt(2) == 0 ? a : b;
    }

    default <T> T choose(T a, T b, T c, T d) {
        int i = this.nextInt(4);
        if (i == 0) {
            return a;
        } else if (i == 1) {
            return b;
        } else {
            return i == 2 ? c : d;
        }
    }
}
