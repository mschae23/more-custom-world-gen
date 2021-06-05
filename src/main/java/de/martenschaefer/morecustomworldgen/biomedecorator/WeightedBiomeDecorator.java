package de.martenschaefer.morecustomworldgen.biomedecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.CachingBiomeDecorator;
import de.martenschaefer.morecustomworldgen.util.ChanceEntry;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class WeightedBiomeDecorator extends CachingBiomeDecorator {
    public static final Codec<WeightedBiomeDecorator> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            ChanceEntry.createCodec(RegistryKeys.BIOME_CODEC, "biome").listOf().fieldOf("biomes").forGetter(WeightedBiomeDecorator::getBiomes),
            RegistryKeys.BIOME_CODEC.fieldOf("default_biome").forGetter(WeightedBiomeDecorator::getDefaultBiome)
        ).apply(instance, instance.stable(WeightedBiomeDecorator::new))
    );

    private final List<ChanceEntry<RegistryKey<Biome>>> biomes;
    private final RegistryKey<Biome> defaultBiome;
    // private final List<PositionBiomeOverride> positionOverrides;

    public WeightedBiomeDecorator(List<ChanceEntry<RegistryKey<Biome>>> biomes, RegistryKey<Biome> defaultBiome) {
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
    protected Codec<? extends BiomeDecorator> getCodec() {
        return CODEC;
    }

    @Override
    public RegistryKey<Biome> getBiomeCached(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        List<RegistryKey<Biome>> biomeList = this.biomes.stream().collect(ArrayList::new, (list, entry) -> {
            if (entry.getChance().get(random))
                list.add(entry.getValue());
        }, ArrayList::addAll);

        if (biomeList.size() < 1)
            return this.defaultBiome;
        else
            return biomeList.get(0);
    }

    @Override
    public List<Biome> getBiomes(Registry<Biome> biomeRegistry) {
        List<Biome> biomes = new ArrayList<>();
        biomes.add(biomeRegistry.get(this.defaultBiome));
        biomes.addAll(this.biomes.stream()
            .map(ChanceEntry::getValue)
            .map(biomeRegistry::get)
            .collect(Collectors.toList())
        );

        return biomes;
    }
}
