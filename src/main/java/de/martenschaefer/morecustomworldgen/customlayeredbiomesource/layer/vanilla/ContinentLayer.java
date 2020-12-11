package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.ContinentCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.InitLayer;

public class ContinentLayer implements InitLayer<ContinentCategory> {
    private final int continentChance;
    
    public ContinentLayer(int continentChance) {
        this.continentChance = continentChance;
    }

    public ContinentCategory sample(LayerRandomnessSource context, int x, int y) {
        if (x == 0 && y == 0) {
            return ContinentCategory.LAND;
        } else {
            return context.nextInt(this.continentChance) == 0 ? ContinentCategory.LAND : ContinentCategory.OCEAN;
        }
    }
}
