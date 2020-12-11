package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.category;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.StringIdentifiable;
import com.mojang.serialization.Codec;

public enum OceanCategory implements StringIdentifiable {
    NORMAL("normal"),
    WARM("warm"),
    LUKEWARM("lukewarm"),
    COLD("cold"),
    FROZEN("frozen");

    public static final Codec<OceanCategory> CODEC = StringIdentifiable.createCodec(OceanCategory::values, OceanCategory::byName);
    private static final Map<String, OceanCategory> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(OceanCategory::getName, category -> category));

    private final String name;

    OceanCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static OceanCategory byName(String name) {
        return BY_NAME.get(name);
    }

    @Override
    public String asString() {
        return this.name;
    }
}
