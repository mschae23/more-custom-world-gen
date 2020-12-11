package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.StringIdentifiable;
import com.mojang.serialization.Codec;

public enum ContinentCategory implements StringIdentifiable {
    OCEAN("ocean"),
    LAND("land");

    public static final Codec<ContinentCategory> CODEC = StringIdentifiable.createCodec(ContinentCategory::values, ContinentCategory::byName);
    private static final Map<String, ContinentCategory> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(ContinentCategory::getName, category -> category));

    private final String name;

    ContinentCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static ContinentCategory byName(String name) {
        return BY_NAME.get(name);
    }

    @Override
    public String asString() {
        return this.name;
    }
}
