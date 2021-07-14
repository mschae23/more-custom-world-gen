package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class WeightedInitBiomeDecorator extends BiomeDecorator {
    public static final Codec<WeightedInitBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            ChanceEntry.createCodec(RegistryKeys.BIOME_CODEC, "biome").listOf().fieldOf("biomes").forGetter(WeightedInitBiomeDecorator::getBiomes),
            RegistryKeys.BIOME_CODEC.fieldOf("default_biome").forGetter(WeightedInitBiomeDecorator::getDefaultBiome),
            PositionBiomeOverride.CODEC.listOf().fieldOf("position_overrides").forGetter(WeightedInitBiomeDecorator::getPositionOverrides)
        ).apply(instance, instance.stable(WeightedInitBiomeDecorator::new))
    );

    private final List<ChanceEntry<RegistryKey<Biome>>> biomes;
    private final RegistryKey<Biome> defaultBiome;
    private final List<PositionBiomeOverride> positionOverrides;

    public WeightedInitBiomeDecorator(List<ChanceEntry<RegistryKey<Biome>>> biomes, RegistryKey<Biome> defaultBiome, List<PositionBiomeOverride> positionOverrides) {
        this.biomes = biomes;
        this.defaultBiome = defaultBiome;
        this.positionOverrides = positionOverrides;
    }

    public List<ChanceEntry<RegistryKey<Biome>>> getBiomes() {
        return this.biomes;
    }

    public RegistryKey<Biome> getDefaultBiome() {
        return this.defaultBiome;
    }

    public List<PositionBiomeOverride> getPositionOverrides() {
        return this.positionOverrides;
    }

    @Override
    protected Codec<WeightedInitBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryKey<Biome> getBiome(LayerRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        for (var override : this.positionOverrides) {
            if (override.x().isEmpty() && override.y().isEmpty() && override.z().isEmpty())
                continue;

            if ((override.x.isEmpty() || override.x().stream().anyMatch(overrideX -> x == overrideX))
                && (override.y.isEmpty() || override.y().stream().anyMatch(overrideY -> y == overrideY))
                && (override.z.isEmpty() || override.z().stream().anyMatch(overrideZ -> z == overrideZ)))
                return override.biome();
        }

        List<RegistryKey<Biome>> biomeList = this.biomes.stream().collect(ArrayList::new, (list, entry) -> {
            if (entry.chance().get(random))
                list.add(entry.value());
        }, ArrayList::addAll);

        if (biomeList.isEmpty())
            return this.defaultBiome;
        else
            return biomeList.get(0);
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        List<Biome> biomes = new ArrayList<>();
        biomes.add(biomeRegistry.get(this.defaultBiome));
        biomes.addAll(this.biomes.stream()
            .map(ChanceEntry::value)
            .map(biomeRegistry::get)
            .collect(Collectors.toList())
        );

        return biomes;
    }

    public static record PositionBiomeOverride(Optional<Integer> x, Optional<Integer> y, Optional<Integer> z,
                                               RegistryKey<Biome> biome) {
        public static final Codec<PositionBiomeOverride> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("x").forGetter(PositionBiomeOverride::x),
            Codec.INT.optionalFieldOf("y").forGetter(PositionBiomeOverride::y),
            Codec.INT.optionalFieldOf("z").forGetter(PositionBiomeOverride::z),
            RegistryKeys.BIOME_CODEC.fieldOf("biome").forGetter(PositionBiomeOverride::biome)
        ).apply(instance, instance.stable(PositionBiomeOverride::new)));
    }
}
