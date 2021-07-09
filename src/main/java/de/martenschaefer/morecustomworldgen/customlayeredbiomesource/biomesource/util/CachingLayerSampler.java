package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.util;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import net.minecraft.util.math.ChunkPos;

public class CachingLayerSampler<T> implements LayerSampler<T> {
    private final LayerOperator<T> operator;
    private final Long2ObjectLinkedOpenHashMap<T> cache;
    private final int cacheCapacity;

    public CachingLayerSampler(Long2ObjectLinkedOpenHashMap<T> cache, int cacheCapacity, LayerOperator<T> operator) {
        this.cache = cache;
        this.cacheCapacity = cacheCapacity;
        this.operator = operator;
    }

    public T sample(int x, int z) {
        long l = ChunkPos.toLong(x, z);
        synchronized (this.cache) {
            T obj = this.cache.get(l);
            if (obj != null) {
                return obj;
            } else {
                T j = this.operator.apply(x, z);
                this.cache.put(l, j);
                if (this.cache.size() > this.cacheCapacity) {
                    for (int k = 0; k < this.cacheCapacity / 16; ++k) {
                        this.cache.removeFirst();
                    }
                }
                return j;
            }
        }
    }

    public int getCapacity() {
        return this.cacheCapacity;
    }
}
