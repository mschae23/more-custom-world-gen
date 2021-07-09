package de.martenschaefer.morecustomworldgen.util;

import com.mojang.serialization.Lifecycle;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

public final class FabricRegistryBuilderUtil {
    private FabricRegistryBuilderUtil() {
    }

    public static <T> FabricRegistryBuilder<T, SimpleRegistry<T>> createSimple(Identifier registryId) {
        return FabricRegistryBuilder.from(new SimpleRegistry<T>(RegistryKey.ofRegistry(registryId), Lifecycle.stable()));
    }

    public static <T> FabricRegistryBuilder<T, DefaultedRegistry<T>> createDefaulted(Identifier registryId, Identifier defaultId) {
        return FabricRegistryBuilder.from(new DefaultedRegistry<T>(defaultId.toString(), RegistryKey.ofRegistry(registryId), Lifecycle.stable()));
    }
}
