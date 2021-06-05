package de.martenschaefer.morecustomworldgen.biomedecorator.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;

public abstract class CachingBiomeDecorator extends BiomeDecorator {
    private final Long2ObjectLinkedOpenHashMap<RegistryKey<Biome>> cache;
    private final int cacheCapacity;

    public CachingBiomeDecorator(Long2ObjectLinkedOpenHashMap<RegistryKey<Biome>> cache, int cacheCapacity) {
        this.cache = cache;
        this.cacheCapacity = cacheCapacity;
    }

    public CachingBiomeDecorator(int cacheCapacity) {
        this(new Long2ObjectLinkedOpenHashMap<>(16, 0.25F), cacheCapacity);
    }

    public CachingBiomeDecorator() {
        this(25);
    }

    @Override
    public RegistryKey<Biome> getBiome(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z) {
        long pos = BlockPos.asLong(x, y, z);

        synchronized (this.cache) {
            RegistryKey<Biome> cachedBiome = this.cache.get(pos);
            if (cachedBiome != null) {
                return cachedBiome;
            } else {
                RegistryKey<Biome> biome = this.getBiomeCached(random, parent, x, y, z);
                this.cache.put(pos, biome);
                if (this.cache.size() > this.cacheCapacity) {
                    for (int k = 0; k < this.cacheCapacity / 16; k++) {
                        this.cache.removeFirst();
                    }
                }
                return biome;
            }
        }
    }

    public abstract RegistryKey<Biome> getBiomeCached(DecoratorRandomnessSource random, BiomeSampler parent, int x, int y, int z);
}
