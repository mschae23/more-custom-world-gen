package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.CrossSamplingBiomeDecorator;

public record BorderingReplaceBiomeDecorator(BorderingReplaceBiomeEntry entry) implements CrossSamplingBiomeDecorator {
    public static final Codec<BorderingReplaceBiomeDecorator> CODEC = BorderingReplaceBiomeEntry.CODEC.xmap(
        BorderingReplaceBiomeDecorator::new, BorderingReplaceBiomeDecorator::entry);

    @Override
    public Codec<BorderingReplaceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return this.entry.getBiomes(biomeRegistry);
    }

    @Override
    public BiomeContext sample(LayerRandomnessSource random, BiomeContext n, BiomeContext e, BiomeContext s, BiomeContext w, BiomeContext center) {
        return this.entry.sample(random, n, e, s, w, center).orElse(center);
    }
}
