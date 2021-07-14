package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;

public class CachingDecoratorBiomeSampler extends DecoratorBiomeSampler {
    private final Object2ObjectLinkedOpenHashMap<BlockPos, RegistryKey<Biome>> cache;
    private final int cacheCapacity;

    public CachingDecoratorBiomeSampler(LayerRandomnessSource random, BiomeSampler parent, BiomeDecorator decorator, Object2ObjectLinkedOpenHashMap<BlockPos, RegistryKey<Biome>> cache, int cacheCapacity) {
        super(random, parent, decorator);
        this.cache = cache;
        this.cache.defaultReturnValue(null);
        this.cacheCapacity = cacheCapacity;
    }

    public CachingDecoratorBiomeSampler(LayerRandomnessSource random, BiomeSampler parent, BiomeDecorator decorator, int cacheCapacity) {
        this(random, parent, decorator, new Object2ObjectLinkedOpenHashMap<>(16, 0.25F), cacheCapacity);
    }

    public CachingDecoratorBiomeSampler(LayerRandomnessSource random, BiomeSampler parent, BiomeDecorator decorator) {
        this(random, parent, decorator, parent instanceof CachingDecoratorBiomeSampler ?
            Math.min(1024, ((CachingDecoratorBiomeSampler) parent).getCacheCapacity() * 4) : 25);
    }

    public Object2ObjectLinkedOpenHashMap<BlockPos, RegistryKey<Biome>> getCache() {
        return this.cache;
    }

    public int getCacheCapacity() {
        return this.cacheCapacity;
    }

    @Override
    public RegistryKey<Biome> sample(int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        synchronized (this.cache) {
            RegistryKey<Biome> cachedBiome = this.cache.getAndMoveToLast(pos);

            if (cachedBiome != null) {
                return cachedBiome;
            } else {
                RegistryKey<Biome> biome = super.sample(x, y, z);
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
