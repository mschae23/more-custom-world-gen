package de.martenschaefer.morecustomworldgen.biomedecorator.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import de.martenschaefer.morecustomworldgen.biomedecorator.BiomeSampler;
import de.martenschaefer.morecustomworldgen.biomedecorator.DecoratorRandomnessSource;
import de.martenschaefer.morecustomworldgen.biomedecorator.MergingBiomeDecorator;

public class CachingMergingDecoratorBiomeSampler extends MergingDecoratorBiomeSampler {
    private final Object2ObjectLinkedOpenHashMap<BlockPos, RegistryKey<Biome>> cache;
    private final int cacheCapacity;

    public CachingMergingDecoratorBiomeSampler(DecoratorRandomnessSource random, BiomeSampler parent, BiomeSampler parent2, MergingBiomeDecorator decorator, Object2ObjectLinkedOpenHashMap<BlockPos, RegistryKey<Biome>> cache, int cacheCapacity) {
        super(random, parent, parent2, decorator);
        this.cache = cache;
        this.cache.defaultReturnValue(null);
        this.cacheCapacity = cacheCapacity;
    }

    public CachingMergingDecoratorBiomeSampler(DecoratorRandomnessSource random, BiomeSampler parent, BiomeSampler parent2, MergingBiomeDecorator decorator, int cacheCapacity) {
        this(random, parent, parent2, decorator, new Object2ObjectLinkedOpenHashMap<>(16, 0.25F), cacheCapacity);
    }

    public CachingMergingDecoratorBiomeSampler(DecoratorRandomnessSource random, BiomeSampler parent, BiomeSampler parent2, MergingBiomeDecorator decorator) {
        this(random, parent, parent2, decorator, parent instanceof CachingMergingDecoratorBiomeSampler && parent2 instanceof CachingMergingDecoratorBiomeSampler ?
            Math.min(1024, Math.max(
                ((CachingMergingDecoratorBiomeSampler) parent).getCacheCapacity(),
                ((CachingMergingDecoratorBiomeSampler) parent2).getCacheCapacity()) * 4) : 25);
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
