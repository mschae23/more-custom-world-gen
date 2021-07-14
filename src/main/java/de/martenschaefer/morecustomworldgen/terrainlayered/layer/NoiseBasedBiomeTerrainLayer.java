package de.martenschaefer.morecustomworldgen.terrainlayered.layer;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainBiomeSampler;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainLayer;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainSamplerContext;
import de.martenschaefer.morecustomworldgen.terrainlayered.util.NoiseType;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public record NoiseBasedBiomeTerrainLayer(NoiseType noiseType, float min, float max, RegistryKey<Biome> biome) implements TerrainLayer {
    public static final Codec<NoiseBasedBiomeTerrainLayer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        NoiseType.CODEC.fieldOf("noise_type").forGetter(NoiseBasedBiomeTerrainLayer::noiseType),
        Codec.FLOAT.fieldOf("noise_min").forGetter(NoiseBasedBiomeTerrainLayer::min),
        Codec.FLOAT.fieldOf("noise_max").forGetter(NoiseBasedBiomeTerrainLayer::max),
        RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(NoiseBasedBiomeTerrainLayer::biome)
    ).apply(instance, instance.stable(NoiseBasedBiomeTerrainLayer::new)));

    @Override
    public Codec<NoiseBasedBiomeTerrainLayer> getCodec() {
        return CODEC;
    }

    @Override
    public void init(LayerRandomnessSource random) {
    }

    @Override
    public Optional<RegistryKey<Biome>> getBiome(LayerRandomnessSource random, TerrainBiomeSampler parent, int x, int y, int z, TerrainSamplerContext context) {
        Optional<RegistryKey<Biome>> previous = parent.sample(x, y, z, context);

        if (previous.isPresent())
            return previous;

        float noise = getNoise(this.noiseType, context);

        if (noise >= this.min && noise <= this.max) {
            return Optional.of(this.biome);
        }

        return Optional.empty();
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return Stream.of(() -> biomeRegistry.get(this.biome));
    }

    public static float getNoise(NoiseType type, TerrainSamplerContext context) {
        return switch(type) {
            case CONTINENTALNESS -> context.continentalnessNoise();
            case EROSION -> context.erosionNoise();
            case WEIRDNESS -> context.weirdnessNoise();
        };
    }
}
