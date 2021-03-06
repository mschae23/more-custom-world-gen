package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.List;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.CoordinateTransformer;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.ScaleType;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class ScaleBiomeDecorator extends BiomeDecorator implements CoordinateTransformer {
    public static final ScaleBiomeDecorator SIMPLE = new ScaleBiomeDecorator(ScaleType.SIMPLE);
    public static final ScaleBiomeDecorator NORMAL = new ScaleBiomeDecorator(ScaleType.NORMAL);
    public static final ScaleBiomeDecorator FUZZY = new ScaleBiomeDecorator(ScaleType.FUZZY);

    public static final Codec<ScaleBiomeDecorator> CODEC = ScaleType.CODEC.fieldOf("scale_type")
        .xmap(ScaleBiomeDecorator::new, ScaleBiomeDecorator::getType).codec();

    private final ScaleType type;

    public ScaleBiomeDecorator(ScaleType type) {
        this.type = type;
    }

    public ScaleType getType() {
        return this.type;
    }

    @Override
    protected Codec<ScaleBiomeDecorator> getCodec() {
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
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        RegistryKey<Biome> biome = parent.sample(this.transformX(x), this.transformY(y), this.transformZ(z));

        if (this.type == ScaleType.SIMPLE)
            return biome;

        random.initSeed(x >> 1 << 1, y >> 1 << 1,  z >> 1 << 1);
        int j = x & 1;
        int k = z & 1;
        if (j == 0 && k == 0) {
            return biome;
        } else {
            RegistryKey<Biome> l = parent.sample(this.transformX(x), this.transformY(y), this.transformZ(z + 1));

            if (j == 0) { // k == 1
                return random.choose(biome, l);
            } else {
                RegistryKey<Biome> n = parent.sample(this.transformX(x + 1), this.transformY(y), this.transformZ(z));

                if (k == 0) { // j == 1
                    return random.choose(biome, n);
                } else {
                    RegistryKey<Biome> p = parent.sample(this.transformX(x + 1), this.transformY(y), this.transformZ(z + 1));
                    return this.sample(random, biome, n, l, p);
                }
            }
        }
    }

    protected <T> RegistryKey<T> sample(DecoratorRandomnessSource random, RegistryKey<T> i, RegistryKey<T> j, RegistryKey<T> k, RegistryKey<T> l) {
        if (this.type == ScaleType.FUZZY)
            return random.choose(i, j, k, l);

        if (RegistryKeys.equals(j, k) && RegistryKeys.equals(k, l)) {
            return j;
        } else if (RegistryKeys.equals(i, j) && RegistryKeys.equals(i, k)) {
            return i;
        } else if (RegistryKeys.equals(i, j) && RegistryKeys.equals(i, l)) {
            return i;
        } else if (RegistryKeys.equals(i, k) && RegistryKeys.equals(i, l)) {
            return i;
        } else if (RegistryKeys.equals(i, j) && !RegistryKeys.equals(k, l)) {
            return i;
        } else if (RegistryKeys.equals(i, k) && !RegistryKeys.equals(j, l)) {
            return i;
        } else if (RegistryKeys.equals(i, l) && !RegistryKeys.equals(j, k)) {
            return i;
        } else if (RegistryKeys.equals(j, k) && !RegistryKeys.equals(i, l)) {
            return j;
        } else if (RegistryKeys.equals(j, l) && !RegistryKeys.equals(i, k)) {
            return j;
        } else {
            return RegistryKeys.equals(k, l) && !RegistryKeys.equals(i, j) ? k : random.choose(i, j, k, l);
        }
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return ImmutableList.of();
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
