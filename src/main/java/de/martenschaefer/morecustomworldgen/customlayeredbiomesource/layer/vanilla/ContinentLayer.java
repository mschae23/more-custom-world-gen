package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.vanilla;

import de.martenschaefer.morecustomworldgen.LayerRandomnessSource;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.ContinentCategory;
import de.martenschaefer.morecustomworldgen.customlayeredbiomesource.layer.type.InitLayer;

public class ContinentLayer implements InitLayer<ContinentCategory> {
    private final int continentChance;
    private final boolean originContinent;

    public ContinentLayer(int continentChance, boolean originContinent) {
        this.continentChance = continentChance;
        this.originContinent = originContinent;
    }

    public ContinentCategory sample(LayerRandomnessSource context, int x, int y) {
        if (this.originContinent && x == 0 && y == 0) {
            return ContinentCategory.LAND;
        } else {
            return context.nextInt(this.continentChance) == 0 ? ContinentCategory.LAND : ContinentCategory.OCEAN;
        }
    }
}
