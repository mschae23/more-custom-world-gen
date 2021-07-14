package de.martenschaefer.morecustomworldgen.terrainlayered.util;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum NoiseType implements StringIdentifiable {
    CONTINENTALNESS("continentalness"),
    EROSION("erosion"),
    WEIRDNESS("weirdness");

    public static final Codec<NoiseType> CODEC = StringIdentifiable.createCodec(NoiseType::values, NoiseType::byName);
    private static final Map<String, NoiseType> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(NoiseType::getName, Function.identity()));

    private final String name;

    NoiseType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static NoiseType byName(String name) {
        return BY_NAME.get(name);
    }
}
