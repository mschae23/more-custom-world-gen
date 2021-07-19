package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.impl.FromSourceBiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

@Deprecated
public record FromBiomeSourceBiomeDecorator(BiomeSource biomeSource,
                                            Registry<Biome> biomeRegistry) implements ParentedBiomeDecorator {
    public static final Codec<FromBiomeSourceBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(FromBiomeSourceBiomeDecorator::getBiomeSource),
            RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(FromBiomeSourceBiomeDecorator::getBiomeRegistry)
        ).apply(instance, instance.stable(FromBiomeSourceBiomeDecorator::new))
    );

    public BiomeSource getBiomeSource() {
        return this.biomeSource;
    }

    public Registry<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    @Override
    public Codec<FromBiomeSourceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        Biome biome = this.biomeSource.getBiomeForNoiseGen(x, y, z);
        double[] offsetAndFactor = this.biomeSource.method_37612(x, z);
        Identifier id = this.biomeRegistry.getId(biome);
        RegistryKey<Biome> key = RegistryKey.of(Registry.BIOME_KEY, id);

        double offset = offsetAndFactor[0];
        double factor = offsetAndFactor[1];

        return new BiomeContext(key, 0, 0, 0, 0, 0, offset, factor);
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return this.biomeSource.getBiomes().stream().map(b -> () -> b);
    }

    @Override
    public BiomeSampler createSampler(long seed, long salt, BiomeSampler parent, Registry<Biome> biomeRegistry) {
        return new FromSourceBiomeSampler(this.biomeSource, biomeRegistry);
    }
}
