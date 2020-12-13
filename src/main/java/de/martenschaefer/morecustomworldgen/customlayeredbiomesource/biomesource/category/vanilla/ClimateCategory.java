package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category.vanilla;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.StringIdentifiable;
import com.mojang.serialization.Codec;

public enum ClimateCategory implements StringIdentifiable {
    OCEAN("ocean"),
    DEEP_OCEAN("deep_ocean"),
    DRY("dry"),
    TEMPERATE("temperate"),
    COOL("cool"),
    SNOWY("snowy"),
    SPECIAL_DRY("special_dry"),
    SPECIAL_TEMPERATE("special_temperate"),
    SPECIAL_COOL("special_cool"),
    SPECIAL_SNOWY("special_snowy"),
    RARE_ISLAND("rare_island");

    public static final Codec<ClimateCategory> CODEC = StringIdentifiable.createCodec(ClimateCategory::values, ClimateCategory::byName);
    private static final Map<String, ClimateCategory> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(ClimateCategory::getName, category -> category));

    private final String name;

    ClimateCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static ClimateCategory byName(String name) {
        return BY_NAME.get(name);
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static boolean isOcean(ClimateCategory category) {
        return category == OCEAN || category == DEEP_OCEAN;
    }

    public static boolean isShallowOcean(ClimateCategory category) {
        return category == OCEAN;
    }
}
