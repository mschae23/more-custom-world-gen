package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeDecoratorEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.CachingDecoratorBiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.FixedBiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.FromSourceBiomeSampler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArrayDecoratedBiomeSource extends BiomeSource {
    public static final Codec<ArrayDecoratedBiomeSource> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.LONG.fieldOf("seed").forGetter(ArrayDecoratedBiomeSource::getSeed),
            BiomeSource.CODEC.optionalFieldOf("biome_source").forGetter(ArrayDecoratedBiomeSource::getBiomeSource),
            BiomeDecoratorEntry.CODEC.listOf().fieldOf("decorators").forGetter(ArrayDecoratedBiomeSource::getDecorators),
            RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(ArrayDecoratedBiomeSource::getBiomeRegistry)
        ).apply(instance, instance.stable(ArrayDecoratedBiomeSource::new))
    );

    private static final Logger LOGGER = LogManager.getLogger();

    private final long seed;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<BiomeSource> biomeSource;
    private final List<BiomeDecoratorEntry> decorators;
    private final Registry<Biome> biomeRegistry;
    private final BiomeSampler sampler;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public ArrayDecoratedBiomeSource(long seed, Optional<BiomeSource> biomeSource, List<BiomeDecoratorEntry> decorators, Registry<Biome> biomeRegistry) {
        super(Stream.concat(decorators.stream().map(BiomeDecoratorEntry::decorator).flatMap(decorator -> decorator.getBiomes(biomeRegistry).stream()),
            biomeSource.map(BiomeSource::getBiomes).stream().flatMap(Collection::stream))
            .collect(Collectors.toList()));
        this.seed = seed;
        this.biomeSource = biomeSource;
        this.decorators = decorators;
        this.biomeRegistry = biomeRegistry;
        this.sampler = this.createSampler();
    }

    public ArrayDecoratedBiomeSource(long seed, BiomeSource biomeSource, List<BiomeDecoratorEntry> decorators, Registry<Biome> biomeRegistry) {
        this(seed, Optional.of(biomeSource), decorators, biomeRegistry);
    }

    public ArrayDecoratedBiomeSource(long seed, List<BiomeDecoratorEntry> decorators, Registry<Biome> biomeRegistry) {
        this(seed, Optional.empty(), decorators, biomeRegistry);
    }

    public long getSeed() {
        return this.seed;
    }

    public Optional<BiomeSource> getBiomeSource() {
        return this.biomeSource;
    }

    public List<BiomeDecoratorEntry> getDecorators() {
        return this.decorators;
    }

    public Registry<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    private BiomeSampler createSampler() {
        BiomeSampler parent = this.biomeSource.<BiomeSampler>map(source -> new FromSourceBiomeSampler(source, this.biomeRegistry))
            .orElseGet(() -> new FixedBiomeSampler(BiomeKeys.THE_VOID));

        for (BiomeDecoratorEntry entry : this.decorators) {
            DecoratorRandomnessSource random = new VanillaDecoratorRandomnessSource(this.seed, entry.salt());
            parent = new CachingDecoratorBiomeSampler(random, parent, entry.decorator());
        }

        return parent;
    }

    @Override
    protected Codec<ArrayDecoratedBiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new ArrayDecoratedBiomeSource(seed, this.biomeSource, this.decorators, this.biomeRegistry);
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        RegistryKey<Biome> key = this.sampler.sample(biomeX, 0, biomeZ);

        if (key == null) {
            throw new IllegalStateException("No biome emitted by biome decorators");
        } else {
            Biome biome = this.biomeRegistry.get(key);
            if (biome == null) {
                if (SharedConstants.isDevelopment) {
                    throw Util.throwOrPause(new IllegalStateException("Unknown biome id: " + key.getValue()));
                } else {
                    LOGGER.warn("Unknown biome id: " + key.getValue());
                    return this.biomeRegistry.get(BiomeKeys.OCEAN);
                }
            } else {
                return biome;
            }
        }
    }
}
