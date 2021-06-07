package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.config.BiomeDecoratorEntry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ArrayDecoratedBiomeSource extends BiomeSource {
    public static final Codec<ArrayDecoratedBiomeSource> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.LONG.fieldOf("seed").forGetter(ArrayDecoratedBiomeSource::getSeed),
            BiomeSource.CODEC.optionalFieldOf("biome_source").forGetter(ArrayDecoratedBiomeSource::getBiomeSource),
            BiomeDecoratorEntry.CODEC.listOf().fieldOf("decorators").forGetter(ArrayDecoratedBiomeSource::getDecorators),
            RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(ArrayDecoratedBiomeSource::getBiomeRegistry)
        ).apply(instance, instance.stable(ArrayDecoratedBiomeSource::new))
    );

    private final long seed;
    private final Optional<BiomeSource> biomeSource;
    private final List<BiomeDecoratorEntry> decorators;
    private final Registry<Biome> biomeRegistry;

    public ArrayDecoratedBiomeSource(long seed, Optional<BiomeSource> biomeSource, List<BiomeDecoratorEntry> decorators, Registry<Biome> biomeRegistry) {
        super(Stream.concat(decorators.stream().map(BiomeDecoratorEntry::decorator).flatMap(decorator -> decorator.getBiomes(biomeRegistry).stream()),
            biomeSource.map(BiomeSource::getBiomes).map(List::stream).orElseGet(Stream::empty))
            .collect(Collectors.toList()));
        this.seed = seed;
        this.biomeSource = biomeSource;
        this.decorators = decorators;
        this.biomeRegistry = biomeRegistry;
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

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new ArrayDecoratedBiomeSource(seed, this.biomeSource, this.decorators, this.biomeRegistry);
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        BiomeSampler parent = this.biomeSource.map(source -> (BiomeSampler) (x, y, z) ->
            RegistryKey.of(Registry.BIOME_KEY, this.biomeRegistry.getId(source.getBiomeForNoiseGen(x, y, z)))
        ).orElseGet(() -> (x, y, z) -> BiomeKeys.THE_VOID);

        for (BiomeDecoratorEntry entry : this.decorators) {
            DecoratorRandomnessSource random = new VanillaDecoratorRandomnessSource(this.seed, entry.salt());

            BiomeSampler tempParent = parent;
            parent = (x, y, z) -> entry.decorator().getBiome(random, tempParent, x, y, z);
        }

        return this.biomeRegistry.get(parent.sample(biomeX, biomeY, biomeZ));
    }
}
