package de.martenschaefer.morecustomworldgen.customlayeredbiomesource.biomesource.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.martenschaefer.morecustomworldgen.util.RegistryKeys;

public class BaseBiomesConfig {
    public static final Codec<BaseBiomesConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            BiomeWeightEntry.CODEC.listOf().fieldOf("dry").forGetter(config -> config.dryBiomes),
            BiomeWeightEntry.CODEC.listOf().fieldOf("temperate").forGetter(config -> config.temperateBiomes),
            BiomeWeightEntry.CODEC.listOf().fieldOf("cool").forGetter(config -> config.coolBiomes),
            BiomeWeightEntry.CODEC.listOf().fieldOf("snowy").forGetter(config -> config.snowyBiomes),
            BiomeWeightEntry.CODEC.listOf().fieldOf("special_dry").forGetter(config -> config.specialDryBiomes),
            BiomeWeightEntry.CODEC.listOf().fieldOf("special_temperate").forGetter(config -> config.specialTemperateBiomes),
            BiomeWeightEntry.CODEC.listOf().fieldOf("special_cool").forGetter(config -> config.specialCoolBiomes),
            BiomeWeightEntry.CODEC.listOf().fieldOf("special_snowy").forGetter(config -> config.specialSnowyBiomes),
            BiomeWeightEntry.CODEC.listOf().fieldOf("rare_island").forGetter(config -> config.rareIslandBiomes),
            RegistryKeys.BIOME_CODEC.fieldOf("default_land").forGetter(config -> config.defaultLandBiome)
        ).apply(instance, instance.stable(BaseBiomesConfig::new)));

    private final List<BiomeWeightEntry> dryBiomes;
    private final List<BiomeWeightEntry> temperateBiomes;
    private final List<BiomeWeightEntry> coolBiomes;
    private final List<BiomeWeightEntry> snowyBiomes;
    private final List<BiomeWeightEntry> specialDryBiomes;
    private final List<BiomeWeightEntry> specialTemperateBiomes;
    private final List<BiomeWeightEntry> specialCoolBiomes;
    private final List<BiomeWeightEntry> specialSnowyBiomes;
    private final List<BiomeWeightEntry> rareIslandBiomes;
    private final RegistryKey<Biome> defaultLandBiome;

    public BaseBiomesConfig(List<BiomeWeightEntry> dryBiomes, List<BiomeWeightEntry> temperateBiomes, List<BiomeWeightEntry> coolBiomes, List<BiomeWeightEntry> snowyBiomes, List<BiomeWeightEntry> specialDryBiomes, List<BiomeWeightEntry> specialTemperateBiomes, List<BiomeWeightEntry> specialCoolBiomes, List<BiomeWeightEntry> specialSnowyBiomes, List<BiomeWeightEntry> rareIslandBiomes, RegistryKey<Biome> defaultLandBiome) {
        this.dryBiomes = dryBiomes;
        this.temperateBiomes = temperateBiomes;
        this.coolBiomes = coolBiomes;
        this.snowyBiomes = snowyBiomes;
        this.specialDryBiomes = specialDryBiomes;
        this.specialTemperateBiomes = specialTemperateBiomes;
        this.specialCoolBiomes = specialCoolBiomes;
        this.specialSnowyBiomes = specialSnowyBiomes;
        this.rareIslandBiomes = rareIslandBiomes;
        this.defaultLandBiome = defaultLandBiome;
    }

    public List<BiomeWeightEntry> getDryBiomes() {
        return this.dryBiomes;
    }

    public List<BiomeWeightEntry> getTemperateBiomes() {
        return this.temperateBiomes;
    }

    public List<BiomeWeightEntry> getCoolBiomes() {
        return this.coolBiomes;
    }

    public List<BiomeWeightEntry> getSnowyBiomes() {
        return this.snowyBiomes;
    }

    public List<BiomeWeightEntry> getSpecialDryBiomes() {
        return this.specialDryBiomes;
    }

    public List<BiomeWeightEntry> getSpecialTemperateBiomes() {
        return this.specialTemperateBiomes;
    }

    public List<BiomeWeightEntry> getSpecialCoolBiomes() {
        return this.specialCoolBiomes;
    }

    public List<BiomeWeightEntry> getSpecialSnowyBiomes() {
        return this.specialSnowyBiomes;
    }

    public List<BiomeWeightEntry> getRareIslandBiomes() {
        return this.rareIslandBiomes;
    }

    public RegistryKey<Biome> getDefaultLandBiome() {
        return this.defaultLandBiome;
    }

    public List<Supplier<Biome>> getBiomes(Registry<Biome> biomeRegistry) {
        List<BiomeWeightEntry> biomeEntries = new ArrayList<>(this.dryBiomes);
        biomeEntries.addAll(this.temperateBiomes);
        biomeEntries.addAll(this.coolBiomes);
        biomeEntries.addAll(this.snowyBiomes);
        biomeEntries.addAll(this.specialDryBiomes);
        biomeEntries.addAll(this.specialTemperateBiomes);
        biomeEntries.addAll(this.specialCoolBiomes);
        biomeEntries.addAll(this.specialSnowyBiomes);
        biomeEntries.addAll(this.rareIslandBiomes);

        return biomeEntries.stream()
            .distinct()
            .map(BiomeWeightEntry::getBiome)
            .<Supplier<Biome>>map(key -> () -> biomeRegistry.get(key))
            .collect(Collectors.toList());
    }
}
