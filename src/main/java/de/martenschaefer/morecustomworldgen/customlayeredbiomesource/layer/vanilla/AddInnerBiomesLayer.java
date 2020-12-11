package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import java.util.List;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config.InnerBiomeConfig;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.SouthEastSamplingLayer;

public class AddInnerBiomesLayer implements SouthEastSamplingLayer<RegistryKey<Biome>> {
    private final List<InnerBiomeConfig> innerBiomes;

    public AddInnerBiomesLayer(List<InnerBiomeConfig> innerBiomes) {
        this.innerBiomes = innerBiomes;
    }

    @Override
    public RegistryKey<Biome> sample(LayerRandomnessSource context, RegistryKey<Biome> se) {
        for (InnerBiomeConfig config : this.innerBiomes) {
            if (se.getValue().equals(config.getBiome().getValue()))
                return context.nextInt(config.getChance()) == 0 ? config.getInnerBiome() : se;
        }

        return se;
    }
}
