package de.martenschaefer.morecustomworldgen.terrainlayered.layer;

import java.util.List;
import java.util.Objects;
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
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainLayerEntry;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainLayeredBiomeSource;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainSamplerContext;
import de.martenschaefer.morecustomworldgen.terrainlayered.util.NoiseType;

public final class NoiseBasedBiomeNestedTerrainLayer implements TerrainLayer {
    public static final Codec<NoiseBasedBiomeNestedTerrainLayer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        NoiseType.CODEC.fieldOf("noise_type").forGetter(NoiseBasedBiomeNestedTerrainLayer::noiseType),
        Codec.FLOAT.fieldOf("noise_min").forGetter(NoiseBasedBiomeNestedTerrainLayer::min),
        Codec.FLOAT.fieldOf("noise_max").forGetter(NoiseBasedBiomeNestedTerrainLayer::max),
        TerrainLayerEntry.CODEC.listOf().fieldOf("layers").forGetter(NoiseBasedBiomeNestedTerrainLayer::layers)
    ).apply(instance, instance.stable(NoiseBasedBiomeNestedTerrainLayer::new)));

    private final NoiseType noiseType;
    private final float min;
    private final float max;
    private final List<TerrainLayerEntry> layers;
    private TerrainBiomeSampler sampler;

    public NoiseBasedBiomeNestedTerrainLayer(NoiseType noiseType, float min, float max, List<TerrainLayerEntry> layers) {
        this.noiseType = noiseType;
        this.min = min;
        this.max = max;
        this.layers = layers;
    }

    public NoiseType noiseType() {
        return noiseType;
    }

    public float min() {
        return min;
    }

    public float max() {
        return max;
    }

    public List<TerrainLayerEntry> layers() {
        return layers;
    }

    @Override
    public Codec<NoiseBasedBiomeNestedTerrainLayer> getCodec() {
        return CODEC;
    }

    @Override
    public void init(LayerRandomnessSource random) {
        this.sampler = TerrainLayeredBiomeSource.createSampler(random.getSeed(), this.layers);
    }

    @Override
    public Optional<RegistryKey<Biome>> getBiome(LayerRandomnessSource random, TerrainBiomeSampler parent, int x, int y, int z, TerrainSamplerContext context) {
        Optional<RegistryKey<Biome>> previous = parent.sample(x, y, z, context);

        if (previous.isPresent())
            return previous;

        float noise = NoiseBasedBiomeTerrainLayer.getNoise(this.noiseType, context);

        if (noise >= this.min && noise <= this.max)
            return this.sampler.sample(x, y, z, context);

        return Optional.empty();
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return this.layers.stream().flatMap(entry -> entry.layer().getBiomes(biomeRegistry));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (NoiseBasedBiomeNestedTerrainLayer) obj;
        return Objects.equals(this.noiseType, that.noiseType) &&
            Float.floatToIntBits(this.min) == Float.floatToIntBits(that.min) &&
            Float.floatToIntBits(this.max) == Float.floatToIntBits(that.max) &&
            Objects.equals(this.layers, that.layers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noiseType, min, max, layers);
    }

    @Override
    public String toString() {
        return "NoiseBasedBiomeNestedTerrainLayer[" +
            "noiseType=" + noiseType + ", " +
            "min=" + min + ", " +
            "max=" + max + ", " +
            "layers=" + layers + ']';
    }
}
