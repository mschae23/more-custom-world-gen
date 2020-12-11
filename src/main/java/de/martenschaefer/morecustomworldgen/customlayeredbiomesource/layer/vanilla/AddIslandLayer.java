package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.ContinentCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.CrossSamplingLayer;

public class AddIslandLayer implements CrossSamplingLayer<ContinentCategory> {
    private final int islandChance;
    
    public AddIslandLayer(int islandChance) {
        this.islandChance = islandChance;
    }

    public ContinentCategory sample(LayerRandomnessSource context, ContinentCategory n, ContinentCategory e, ContinentCategory s, ContinentCategory w, ContinentCategory center) {
        return center == ContinentCategory.OCEAN && n == ContinentCategory.OCEAN && e == ContinentCategory.OCEAN && w == ContinentCategory.OCEAN && s == ContinentCategory.OCEAN && context.nextInt(this.islandChance) == 0 ? ContinentCategory.LAND : center;
    }
}
