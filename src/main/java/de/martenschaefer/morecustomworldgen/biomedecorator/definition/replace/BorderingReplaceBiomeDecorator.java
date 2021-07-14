package de.martenschaefer.morecustomworldgen.biomedecorator.definition.replace;

import java.util.List;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.CrossSamplingBiomeDecorator;

public class BorderingReplaceBiomeDecorator extends CrossSamplingBiomeDecorator {
    public static final Codec<BorderingReplaceBiomeDecorator> CODEC = BorderingReplaceBiomeEntry.CODEC.xmap(
        BorderingReplaceBiomeDecorator::new, BorderingReplaceBiomeDecorator::getEntry);

    private final BorderingReplaceBiomeEntry entry;

    public BorderingReplaceBiomeDecorator(BorderingReplaceBiomeEntry entry) {
        this.entry = entry;
    }

    public BorderingReplaceBiomeEntry getEntry() {
        return this.entry;
    }

    @Override
    protected Codec<BorderingReplaceBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        return this.entry.getBiomes(biomeRegistry);
    }

    @Override
    public RegistryKey<Biome> getBiome(LayerRandomnessSource random, RegistryKey<Biome> n, RegistryKey<Biome> e, RegistryKey<Biome> s, RegistryKey<Biome> w, RegistryKey<Biome> center) {
        return this.entry.sample(random, n, e, s, w, center).orElse(center);
    }
}
