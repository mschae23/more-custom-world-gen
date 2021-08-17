package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeDecoratorEntry;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.FixedBiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.FromSourceBiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public class ArrayDecoratedBiomeSource extends BiomeSource {
    public static final Codec<ArrayDecoratedBiomeSource> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.LONG.fieldOf("salt").forGetter(ArrayDecoratedBiomeSource::getSeed),
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
        super(Stream.concat(decorators.stream().map(BiomeDecoratorEntry::decorator).flatMap(decorator -> decorator.getBiomes(biomeRegistry)),
            biomeSource.map(BiomeSource::getBiomes).stream().flatMap(Collection::stream).map(b -> () -> b)));
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
            .orElseGet(() -> new FixedBiomeSampler(new BiomeContext(BiomeKeys.THE_VOID, 0, 0, 0, 0, 0, 0, 0)));

        for (BiomeDecoratorEntry entry : this.decorators) {
            parent = entry.decorator().createSampler(this.seed, entry.salt(), parent, this.biomeRegistry);
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
        BiomeContext context = this.sampler.sample(biomeX, 0, biomeZ);
        RegistryKey<Biome> key = context.biome();

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

    @Override
    public BiomeSource.TerrainParameters getTerrainParameters(int x, int z) {
        BiomeContext context = this.sampler.sample(x, 0, z);
        return new BiomeSource.TerrainParameters(context.offset(), context.factor(), false);
    }

    @Override
    public void addDebugInfo(List<String> info, BlockPos pos) {
        BiomeContext context = this.sampler.sample(pos.getX(), 0, pos.getZ());
        // BiomeContext context3D = this.sampler.sample(pos.getX(), pos.getY(), pos.getZ());

        info.add("Parameters T: " + context.temperature() + " H: " + context.humidity() + " C: " + context.continentalness()
            + " E: " + context.erosion() + " W: " + context.weirdness() + " O: " + context.offset() + " F: " + context.factor());
        // info.add("Parameters (3D) T: " + context3D.temperature() + " H: " + context3D.humidity() + " C: " + context3D.continentalness()
        //     + " E: " + context3D.erosion() + " W: " + context3D.weirdness() + " O: " + context3D.offset() + " F: " + context3D.factor());
    }
}
