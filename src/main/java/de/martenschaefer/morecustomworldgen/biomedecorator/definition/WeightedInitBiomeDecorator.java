package de.martenschaefer.morecustomworldgen.biomedecorator.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class WeightedInitBiomeDecorator extends BiomeDecorator {
    public static final Codec<WeightedInitBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            ChanceEntry.createCodec(RegistryKeys.BIOME_CODEC, "biome").listOf().fieldOf("biomes").forGetter(WeightedInitBiomeDecorator::getBiomes),
            RegistryKeys.BIOME_CODEC.fieldOf("default_biome").forGetter(WeightedInitBiomeDecorator::getDefaultBiome)
        ).apply(instance, instance.stable(WeightedInitBiomeDecorator::new))
    );

    private final List<ChanceEntry<RegistryKey<Biome>>> biomes;
    private final RegistryKey<Biome> defaultBiome;
    // private final List<PositionBiomeOverride> positionOverrides;

    public WeightedInitBiomeDecorator(List<ChanceEntry<RegistryKey<Biome>>> biomes, RegistryKey<Biome> defaultBiome) {
        this.biomes = biomes;
        this.defaultBiome = defaultBiome;
    }

    public List<ChanceEntry<RegistryKey<Biome>>> getBiomes() {
        return this.biomes;
    }

    public RegistryKey<Biome> getDefaultBiome() {
        return this.defaultBiome;
    }

    @Override
    protected Codec<WeightedInitBiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
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
}
