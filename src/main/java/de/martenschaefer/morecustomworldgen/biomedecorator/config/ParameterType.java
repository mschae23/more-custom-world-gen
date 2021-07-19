package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum ParameterType implements StringIdentifiable {
    TEMPERATURE("temperature"),
    HUMIDITY("humidity"),
    CONTINENTALNESS("continentalness"),
    EROSION("erosion"),
    WEIRDNESS("weirdness"),
    OFFSET("offset"),
    FACTOR("factor");

    public static final Codec<ParameterType> CODEC = StringIdentifiable.createCodec(ParameterType::values, ParameterType::byName);
    private static final Map<String, ParameterType> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(ParameterType::getName, Function.identity()));

    private final String name;

    ParameterType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static ParameterType byName(String name) {
        return BY_NAME.get(name);
    }
}
