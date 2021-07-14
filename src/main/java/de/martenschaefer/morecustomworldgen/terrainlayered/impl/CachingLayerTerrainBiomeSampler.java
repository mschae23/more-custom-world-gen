package de.martenschaefer.morecustomworldgen.terrainlayered.impl;

import java.util.Optional;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainBiomeSampler;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainSamplerContext;
import de.martenschaefer.morecustomworldgen.terrainlayered.TerrainLayer;

public class CachingLayerTerrainBiomeSampler extends LayerTerrainBiomeSampler {
    private final Object2ObjectLinkedOpenHashMap<BlockPos, Optional<RegistryKey<Biome>>> cache;
    private final int cacheCapacity;

    public CachingLayerTerrainBiomeSampler(LayerRandomnessSource random, TerrainBiomeSampler parent, TerrainLayer layer, Object2ObjectLinkedOpenHashMap<BlockPos, Optional<RegistryKey<Biome>>> cache, int cacheCapacity) {
        super(random, parent, layer);
        this.cache = cache;
        this.cache.defaultReturnValue(null);
        this.cacheCapacity = cacheCapacity;
    }

    public CachingLayerTerrainBiomeSampler(LayerRandomnessSource random, TerrainBiomeSampler parent, TerrainLayer layer, int cacheCapacity) {
        this(random, parent, layer, new Object2ObjectLinkedOpenHashMap<>(16, 0.25F), cacheCapacity);
    }

    public CachingLayerTerrainBiomeSampler(LayerRandomnessSource random, TerrainBiomeSampler parent, TerrainLayer layer) {
        this(random, parent, layer, parent instanceof CachingLayerTerrainBiomeSampler ?
            Math.min(1024, ((CachingLayerTerrainBiomeSampler) parent).getCacheCapacity() * 4) : 25);
    }

    public Object2ObjectLinkedOpenHashMap<BlockPos, Optional<RegistryKey<Biome>>> getCache() {
        return this.cache;
    }

    public int getCacheCapacity() {
        return this.cacheCapacity;
    }

    @Override
    public Optional<RegistryKey<Biome>> sample(int x, int y, int z, TerrainSamplerContext context) {
        BlockPos pos = new BlockPos(x, y, z);

        synchronized (this.cache) {
            Optional<RegistryKey<Biome>> cachedBiome = this.cache.getAndMoveToLast(pos);

            if (cachedBiome != null) {
                return cachedBiome;
            } else {
                Optional<RegistryKey<Biome>> biome = super.sample(x, y, z, context);
                this.cache.put(pos, biome);

                if (this.cache.size() > this.cacheCapacity) {
                    for (int i = 0; i < this.cacheCapacity / 16; i++) {
                        this.cache.removeFirst();
                    }
                }

                return biome;
            }
        }
    }
}
