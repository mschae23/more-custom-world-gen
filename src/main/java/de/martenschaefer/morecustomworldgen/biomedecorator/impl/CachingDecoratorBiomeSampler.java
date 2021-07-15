package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.util.math.BlockPos;
import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.ParentedBiomeDecorator;
import de.martenschaefer.morecustomworldgen.biomedecorator.util.BiomeContext;

public class CachingDecoratorBiomeSampler extends DecoratorBiomeSampler {
    private final Object2ObjectLinkedOpenHashMap<BlockPos, BiomeContext> cache;
    private final int cacheCapacity;

    public CachingDecoratorBiomeSampler(LayerRandomnessSource random, BiomeSampler parent, ParentedBiomeDecorator decorator, Object2ObjectLinkedOpenHashMap<BlockPos, BiomeContext> cache, int cacheCapacity) {
        super(random, parent, decorator);
        this.cache = cache;
        this.cache.defaultReturnValue(null);
        this.cacheCapacity = cacheCapacity;
    }

    public CachingDecoratorBiomeSampler(LayerRandomnessSource random, BiomeSampler parent, ParentedBiomeDecorator decorator, int cacheCapacity) {
        this(random, parent, decorator, new Object2ObjectLinkedOpenHashMap<>(16, 0.25F), cacheCapacity);
    }

    public CachingDecoratorBiomeSampler(LayerRandomnessSource random, BiomeSampler parent, ParentedBiomeDecorator decorator) {
        this(random, parent, decorator, parent instanceof CachingDecoratorBiomeSampler ?
            Math.min(1024, ((CachingDecoratorBiomeSampler) parent).getCacheCapacity() * 4) : 25);
    }

    public Object2ObjectLinkedOpenHashMap<BlockPos, BiomeContext> getCache() {
        return this.cache;
    }

    public int getCacheCapacity() {
        return this.cacheCapacity;
    }

    @Override
    public BiomeContext sample(int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);

        synchronized (this.cache) {
            BiomeContext cachedContext = this.cache.getAndMoveToLast(pos);

            if (cachedContext != null) {
                return cachedContext;
            } else {
                BiomeContext context = super.sample(x, y, z);
                this.cache.put(pos, context);

                if (this.cache.size() > this.cacheCapacity) {
                    for (int i = 0; i < this.cacheCapacity / 16; i++) {
                        this.cache.removeFirst();
                    }
                }

                return context;
            }
        }
    }
}
