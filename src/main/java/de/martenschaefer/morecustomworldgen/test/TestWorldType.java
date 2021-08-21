package de.martenschaefer.morecustomworldgen.test;

import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import de.martenschaefer.morecustomworldgen.test.multinoise.TestMultiNoiseConfigs;

public class TestWorldType extends GeneratorType {
    public TestWorldType() {
        super("morecustomworldgen.test");
        GeneratorType.VALUES.add(this);
    }

    @Override
    protected ChunkGenerator getChunkGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
        return new NoiseChunkGenerator(TestMultiNoiseConfigs.createTestBiomeSource(biomeRegistry, seed),
            seed, () -> chunkGeneratorSettingsRegistry.getOrThrow(ChunkGeneratorSettings.OVERWORLD));
    }
}
