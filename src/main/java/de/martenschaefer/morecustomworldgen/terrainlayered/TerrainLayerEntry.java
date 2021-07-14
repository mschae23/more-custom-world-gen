package de.martenschaefer.morecustomworldgen.terrainlayered;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TerrainLayerEntry(long salt, TerrainLayer layer) {
    public static final Codec<TerrainLayerEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
       Codec.LONG.fieldOf("salt").forGetter(TerrainLayerEntry::salt),
       TerrainLayer.CODEC.fieldOf("layer").forGetter(TerrainLayerEntry::layer)
    ).apply(instance, instance.stable(TerrainLayerEntry::new)));
}
