package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.VanillaDecoratorRandomnessSource;

public class DecoratedBiomeSource extends BiomeSource {
    public static final Codec<DecoratedBiomeSource> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.LONG.fieldOf("seed").forGetter(DecoratedBiomeSource::getSeed),
            Codec.LONG.fieldOf("salt").forGetter(DecoratedBiomeSource::getSalt),
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(DecoratedBiomeSource::getBiomeSource),
            BiomeDecorator.CODEC.fieldOf("decorator").forGetter(DecoratedBiomeSource::getDecorator),
            RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(DecoratedBiomeSource::getBiomeRegistry)
        ).apply(instance, instance.stable(DecoratedBiomeSource::new))
    );

    private final long seed;
    private final long salt;
    private final DecoratorRandomnessSource random;
    private final BiomeSource biomeSource;
    private final BiomeDecorator decorator;
    private final Registry<Biome> biomeRegistry;

    public DecoratedBiomeSource(long seed, long salt, BiomeSource biomeSource, BiomeDecorator decorator, Registry<Biome> biomeRegistry) {
        super(Stream.concat(biomeSource.getBiomes().stream(), decorator.getBiomes(biomeRegistry).stream())
            .collect(Collectors.toList()));
        this.seed = seed;
        this.salt = salt;
        this.random = new VanillaDecoratorRandomnessSource(seed, salt);
        this.biomeSource = biomeSource;
        this.decorator = decorator;
        this.biomeRegistry = biomeRegistry;
    }

    public long getSeed() {
        return this.seed;
    }

    public long getSalt() {
        return this.salt;
    }

    public BiomeSource getBiomeSource() {
        return this.biomeSource;
    }

    public BiomeDecorator getDecorator() {
        return this.decorator;
    }

    public Registry<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new DecoratedBiomeSource(seed, salt, this.biomeSource, this.decorator, this.biomeRegistry);
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        return this.biomeRegistry.get(this.decorator.getBiome(this.random,
            (x, y, z) -> RegistryKey.of(Registry.BIOME_KEY, this.biomeRegistry.getId(this.biomeSource.getBiomeForNoiseGen(x, y, z))),
            biomeX, biomeY, biomeZ));
    }
}
