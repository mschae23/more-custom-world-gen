package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.List;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.FromSourceBiomeSampler;

public class FromBiomeSourceBiomeDecorator extends BiomeDecorator {
    public static final Codec<FromBiomeSourceBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(FromBiomeSourceBiomeDecorator::getBiomeSource),
            RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(FromBiomeSourceBiomeDecorator::getBiomeRegistry)
        ).apply(instance, instance.stable(FromBiomeSourceBiomeDecorator::new))
    );

    private final BiomeSource biomeSource;
    private final Registry<Biome> biomeRegistry;

    public FromBiomeSourceBiomeDecorator(BiomeSource biomeSource, Registry<Biome> biomeRegistry) {
        this.biomeSource = biomeSource;
        this.biomeRegistry = biomeRegistry;
    }

    public BiomeSource getBiomeSource() {
        return this.biomeSource;
    }

    public Registry<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    @Override
    protected Codec<FromBiomeSourceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        return RegistryKey.of(Registry.BIOME_KEY, this.biomeRegistry.getId(this.biomeSource.getBiomeForNoiseGen(x, y, z)));
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return this.biomeSource.getBiomes();
    }

    @Override
    public BiomeSampler createSampler(long seed, long salt, BiomeSampler parent, Registry<Biome> biomeRegistry) {
        return new FromSourceBiomeSampler(this.biomeSource, biomeRegistry);
    }
}
