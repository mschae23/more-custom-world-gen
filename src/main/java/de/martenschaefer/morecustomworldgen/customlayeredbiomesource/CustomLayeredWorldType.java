package de.martenschaefer.morecustomworldgen.customlayeredbiomesource;

import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.CustomLayeredBiomeSource;

public class CustomLayeredWorldType extends GeneratorType {
    public CustomLayeredWorldType() {
        super("morecustomworldgen.custom_layered");
        GeneratorType.VALUES.add(this);
    }

    @Override
    protected ChunkGenerator getChunkGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
        return new NoiseChunkGenerator(CustomLayeredBiomeSource.Preset.VANILLA_OVERWORLD.getBiomeSource(biomeRegistry, seed),
            seed, () -> chunkGeneratorSettingsRegistry.getOrThrow(ChunkGeneratorSettings.OVERWORLD));
    }
}
