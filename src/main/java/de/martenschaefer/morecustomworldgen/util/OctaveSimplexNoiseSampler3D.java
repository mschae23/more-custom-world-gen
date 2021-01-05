package de.martenschaefer.morecustomworldgen.util;

import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.world.gen.ChunkRandom;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;

public class OctaveSimplexNoiseSampler3D {
    private final SimplexNoiseSampler[] octaveSamplers;
    private final double persistence;
    private final double lacunarity;

    public OctaveSimplexNoiseSampler3D(ChunkRandom random, IntStream octaves) {
        this(random, octaves.boxed().collect(ImmutableList.toImmutableList()));
    }

    public OctaveSimplexNoiseSampler3D(ChunkRandom random, List<Integer> octaves) {
        this(random, new IntRBTreeSet(octaves));
    }

    private OctaveSimplexNoiseSampler3D(ChunkRandom random, IntSortedSet octaves) {
        if (octaves.isEmpty()) {
            throw new IllegalArgumentException("Need some octaves!");
        } else {
            int i = -octaves.firstInt();
            int j = octaves.lastInt();
            int k = i + j + 1;
            if (k < 1) {
                throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
            } else {
                SimplexNoiseSampler simplexNoiseSampler = new SimplexNoiseSampler(random);
                int l = j;
                this.octaveSamplers = new SimplexNoiseSampler[k];
                if (j >= 0 && j < k && octaves.contains(0)) {
                    this.octaveSamplers[j] = simplexNoiseSampler;
                }

                for (int m = j + 1; m < k; ++m) {
                    if (m >= 0 && octaves.contains(l - m)) {
                        this.octaveSamplers[m] = new SimplexNoiseSampler(random);
                    } else {
                        random.consume(262);
                    }
                }

                if (j > 0) {
                    long n = (long) (simplexNoiseSampler.sample(simplexNoiseSampler.originX, simplexNoiseSampler.originY, simplexNoiseSampler.originZ) * 9.223372036854776E18D);
                    ChunkRandom chunkRandom = new ChunkRandom(n);

                    for (int o = l - 1; o >= 0; --o) {
                        if (o < k && octaves.contains(l - o)) {
                            this.octaveSamplers[o] = new SimplexNoiseSampler(chunkRandom);
                        } else {
                            chunkRandom.consume(262);
                        }
                    }
                }

                this.lacunarity = Math.pow(2.0D, j);
                this.persistence = 1.0D / (Math.pow(2.0D, k) - 1.0D);
            }
        }
    }

    public double sample(double x, double y, double z, boolean useOrigin) {
        double d = 0.0D;
        double e = this.lacunarity;
        double f = this.persistence;
        SimplexNoiseSampler[] var12 = this.octaveSamplers;
        int var13 = var12.length;

        for (SimplexNoiseSampler simplexNoiseSampler : var12) {
            if (simplexNoiseSampler != null) {
                d += simplexNoiseSampler.sample(x * e + (useOrigin ? simplexNoiseSampler.originX : 0.0D), y * e + (useOrigin ? simplexNoiseSampler.originY : 0.0D), z * e + (useOrigin ? simplexNoiseSampler.originZ : 0.0D)) * f;
            }

            e /= 2.0D;
            f *= 2.0D;
        }

        return d;
    }

    public double sample(double x, double y, double z, double yScale, double yMax) {
        return this.sample(x, y, z, true) * 0.55D;
    }
}
