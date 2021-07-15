package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;

public record WeightedInitBiomeDecorator(List<ChanceEntry<BiomeContext>> biomes, BiomeContext defaultBiome,
                                         List<PositionBiomeOverride> positionOverrides) implements ParentedBiomeDecorator {
    public static final Codec<WeightedInitBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            ChanceEntry.createCodec(BiomeContext.CODEC, "biome").listOf().fieldOf("biomes").forGetter(WeightedInitBiomeDecorator::biomes),
            BiomeContext.CODEC.fieldOf("default_biome").forGetter(WeightedInitBiomeDecorator::defaultBiome),
            PositionBiomeOverride.CODEC.listOf().fieldOf("position_overrides").forGetter(WeightedInitBiomeDecorator::positionOverrides)
        ).apply(instance, instance.stable(WeightedInitBiomeDecorator::new))
    );

    @Override
    public Codec<WeightedInitBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeContext sample(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        for (var override : this.positionOverrides) {
            if (override.x().isEmpty() && override.y().isEmpty() && override.z().isEmpty())
                continue;

            if ((override.x.isEmpty() || override.x().stream().anyMatch(overrideX -> x == overrideX))
                && (override.y.isEmpty() || override.y().stream().anyMatch(overrideY -> y == overrideY))
                && (override.z.isEmpty() || override.z().stream().anyMatch(overrideZ -> z == overrideZ)))
                return override.biome();
        }

        List<BiomeContext> biomeList = this.biomes.stream().collect(ArrayList::new, (list, entry) -> {
            if (entry.chance().get(random))
                list.add(entry.value());
        }, ArrayList::addAll);

        if (biomeList.isEmpty())
            return this.defaultBiome;
        else
            return biomeList.get(0);
    }

    @Override
    public Stream<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        return Stream.concat(Stream.concat(Stream.of(() -> biomeRegistry.get(this.defaultBiome.biome())),
            this.biomes.stream()
                .map(ChanceEntry::value)
                .map(BiomeContext::biome)
                .map(biome -> () -> biomeRegistry.get(biome))), this.positionOverrides.stream()
            .map(PositionBiomeOverride::biome)
            .map(BiomeContext::biome)
            .map(biome -> () -> biomeRegistry.get(biome)));
    }

    public static record PositionBiomeOverride(Optional<Integer> x, Optional<Integer> y, Optional<Integer> z,
                                               BiomeContext biome) {
        public static final Codec<PositionBiomeOverride> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("x").forGetter(PositionBiomeOverride::x),
            Codec.INT.optionalFieldOf("y").forGetter(PositionBiomeOverride::y),
            Codec.INT.optionalFieldOf("z").forGetter(PositionBiomeOverride::z),
            BiomeContext.CODEC.fieldOf("biome").forGetter(PositionBiomeOverride::biome)
        ).apply(instance, instance.stable(PositionBiomeOverride::new)));
    }
}
