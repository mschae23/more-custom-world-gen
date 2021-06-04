package de.martenschaefer.morecustomworldgen.biomedecorator.config;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.util.StringIdentifiable;
import com.mojang.serialization.Codec;

public enum ScaleType implements StringIdentifiable {
    SIMPLE("simple"),
    NORMAL("normal"),
    FUZZY("fuzzy");

    public static final Codec<ScaleType> CODEC = StringIdentifiable.createCodec(ScaleType::values, ScaleType::byName);
    private static final Map<String, ScaleType> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(ScaleType::getName, Function.identity()));

    private final String name;

    ScaleType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static ScaleType byName(String name) {
        return BY_NAME.get(name);
    }
}
