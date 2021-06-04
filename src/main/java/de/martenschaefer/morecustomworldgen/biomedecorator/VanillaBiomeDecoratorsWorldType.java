package de.martenschaefer.morecustomworldgen.biomedecorator;

import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.CustomLayeredBiomeSource;

public class VanillaBiomeDecoratorsWorldType extends GeneratorType {
    public VanillaBiomeDecoratorsWorldType() {
        super("morecustomworldgen.decorated");
        GeneratorType.VALUES.add(this);
    }

    @Override
    protected ChunkGenerator getChunkGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
        return new NoiseChunkGenerator(BiomeDecoratorConfigs.getVanillaBiomeSource(seed, biomeRegistry),
            seed, () -> chunkGeneratorSettingsRegistry.getOrThrow(ChunkGeneratorSettings.OVERWORLD));
    }
}
