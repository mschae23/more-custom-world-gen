package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.ScaleType;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.util.CoordinateTransformer;

public record ScaleBiomeDecorator(ScaleType type) implements ParentedBiomeDecorator, CoordinateTransformer {
    public static final ScaleBiomeDecorator SIMPLE = new ScaleBiomeDecorator(ScaleType.SIMPLE);
    public static final ScaleBiomeDecorator NORMAL = new ScaleBiomeDecorator(ScaleType.NORMAL);
    public static final ScaleBiomeDecorator FUZZY = new ScaleBiomeDecorator(ScaleType.FUZZY);

    public static final Codec<ScaleBiomeDecorator> CODEC = ScaleType.CODEC.fieldOf("scale_type")
        .xmap(ScaleBiomeDecorator::new, ScaleBiomeDecorator::type).codec();

    @Override
    public Codec<ScaleBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public int transformX(int x) {
        return x >> 1;
    }

    public int transformY(int y) {
        return y;
    }

    @Override
    public int transformZ(int z) {
        return z >> 1;
    }

    @Override
    public BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        BiomeContext center = parent.sample(this.transformX(x), this.transformY(y), this.transformZ(z));

        if (this.type == ScaleType.SIMPLE)
            return center;

        random.initSeed(x >> 1 << 1, y >> 1 << 1, z >> 1 << 1);
        int j = x & 1;
        int k = z & 1;
        if (j == 0 && k == 0) {
            return center;
        } else {
            BiomeContext l = parent.sample(this.transformX(x), this.transformY(y), this.transformZ(z + 1));

            if (j == 0) { // k == 1
                return random.choose(center, l);
            } else {
                BiomeContext n = parent.sample(this.transformX(x + 1), this.transformY(y), this.transformZ(z));

                if (k == 0) { // j == 1
                    return random.choose(center, n);
                } else {
                    BiomeContext p = parent.sample(this.transformX(x + 1), this.transformY(y), this.transformZ(z + 1));
                    return this.sample(random, center, n, l, p);
                }
            }
        }
    }

    protected BiomeContext sample(LayerRandomnessSource random, BiomeContext i, BiomeContext j, BiomeContext k, BiomeContext l) {
        if (this.type == ScaleType.FUZZY)
            return random.choose(i, j, k, l);

        if (Objects.equals(j, k) && Objects.equals(k, l)) {
            return j;
        } else if (Objects.equals(i, j) && Objects.equals(i, k)) {
            return i;
        } else if (Objects.equals(i, j) && Objects.equals(i, l)) {
            return i;
        } else if (Objects.equals(i, k) && Objects.equals(i, l)) {
            return i;
        } else if (Objects.equals(i, j) && !Objects.equals(k, l)) {
            return i;
        } else if (Objects.equals(i, k) && !Objects.equals(j, l)) {
            return i;
        } else if (Objects.equals(i, l) && !Objects.equals(j, k)) {
            return i;
        } else if (Objects.equals(j, k) && !Objects.equals(i, l)) {
            return j;
        } else if (Objects.equals(j, l) && !Objects.equals(i, k)) {
            return j;
        } else {
            return Objects.equals(k, l) && !Objects.equals(i, j) ? k : random.choose(i, j, k, l);
        }
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return Stream.empty();
    }

    public static ScaleBiomeDecorator normal() {
        return NORMAL;
    }

    public static ScaleBiomeDecorator fuzzy() {
        return FUZZY;
    }

    public static ScaleBiomeDecorator simple() {
        return SIMPLE;
    }
}
