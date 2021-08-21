package de.martenschaefer.morecustomworldgen.test.multinoise;

import net.minecraft.SharedConstants;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

@SuppressWarnings("unchecked")
public final class TestBiomeParameters {
    public static final MultiNoiseUtil.ParameterRange DEFAULT_PARAMETER = MultiNoiseUtil.createParameterRange(-1.0F, 1.0F);
    public static final MultiNoiseUtil.ParameterRange[] TEMPERATURE_PARAMETERS = new MultiNoiseUtil.ParameterRange[] { MultiNoiseUtil.createParameterRange(-1.0F, -0.45F), MultiNoiseUtil.createParameterRange(-0.45F, -0.15F), MultiNoiseUtil.createParameterRange(-0.15F, 0.15F), MultiNoiseUtil.createParameterRange(0.15F, 0.45F), MultiNoiseUtil.createParameterRange(0.45F, 1.0F) };
    public static final MultiNoiseUtil.ParameterRange[] HUMIDITY_PARAMETERS = new MultiNoiseUtil.ParameterRange[] { MultiNoiseUtil.createParameterRange(-1.0F, -0.3F), MultiNoiseUtil.createParameterRange(-0.3F, -0.1F), MultiNoiseUtil.createParameterRange(-0.1F, 0.1F), MultiNoiseUtil.createParameterRange(0.1F, 0.3F), MultiNoiseUtil.createParameterRange(0.3F, 1.0F) };
    public static final MultiNoiseUtil.ParameterRange[] EROSION_PARAMETERS = new MultiNoiseUtil.ParameterRange[] { MultiNoiseUtil.createParameterRange(-1.0F, -0.375F), MultiNoiseUtil.createParameterRange(-0.375F, -0.2225F), MultiNoiseUtil.createParameterRange(-0.2225F, 0.05F), MultiNoiseUtil.createParameterRange(0.05F, 0.45F), MultiNoiseUtil.createParameterRange(0.45F, 0.55F), MultiNoiseUtil.createParameterRange(0.55F, 1.0F) };
    public static final MultiNoiseUtil.ParameterRange FROZEN_TEMPERATURE = TEMPERATURE_PARAMETERS[0];
    public static final MultiNoiseUtil.ParameterRange NON_FROZEN_TEMPERATURE_PARAMETERS = MultiNoiseUtil.combineParameterRange(TEMPERATURE_PARAMETERS[1], TEMPERATURE_PARAMETERS[4]);
    public static final MultiNoiseUtil.ParameterRange MUSHROOM_FIELDS_CONTINENTALNESS = MultiNoiseUtil.createParameterRange(-1.05F, -1.05F);
    public static final MultiNoiseUtil.ParameterRange DEEP_OCEAN_CONTINENTALNESS = MultiNoiseUtil.createParameterRange(-1.05F, -0.455F);
    public static final MultiNoiseUtil.ParameterRange OCEAN_CONTINENTALNESS = MultiNoiseUtil.createParameterRange(-0.455F, -0.19F);
    public static final MultiNoiseUtil.ParameterRange SHORE_CONTINENTALNESS = MultiNoiseUtil.createParameterRange(-0.19F, -0.11F);
    public static final MultiNoiseUtil.ParameterRange RIVER_CONTINENTALNESS = MultiNoiseUtil.createParameterRange(-0.11F, 0.55F);
    public static final MultiNoiseUtil.ParameterRange NEAR_INLAND_CONTINENTALNESS = MultiNoiseUtil.createParameterRange(-0.11F, 0.03F);
    public static final MultiNoiseUtil.ParameterRange MID_INLAND_CONTINENTALNESS = MultiNoiseUtil.createParameterRange(0.03F, 0.55F);
    public static final MultiNoiseUtil.ParameterRange FAR_INLAND_CONTINENTALNESS = MultiNoiseUtil.createParameterRange(0.55F, 1.0F);
    public static final RegistryKey<Biome>[][] OCEAN_BIOMES = new RegistryKey[][] { { BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN, BiomeKeys.DEEP_WARM_OCEAN }, { BiomeKeys.FROZEN_OCEAN, BiomeKeys.COLD_OCEAN, BiomeKeys.OCEAN, BiomeKeys.LUKEWARM_OCEAN, BiomeKeys.WARM_OCEAN } };
    public static final RegistryKey<Biome>[][] COMMON_BIOMES = new RegistryKey[][] { { BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.GIANT_TREE_TAIGA }, { BiomeKeys.PLAINS, BiomeKeys.PLAINS, BiomeKeys.FOREST, BiomeKeys.TAIGA, BiomeKeys.TAIGA }, { BiomeKeys.PLAINS, BiomeKeys.PLAINS, BiomeKeys.FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.DARK_FOREST }, { BiomeKeys.SAVANNA, BiomeKeys.SAVANNA, BiomeKeys.FOREST, BiomeKeys.FOREST, BiomeKeys.JUNGLE }, { BiomeKeys.DESERT, BiomeKeys.DESERT, BiomeKeys.DESERT, BiomeKeys.JUNGLE, BiomeKeys.JUNGLE } };
    public static final RegistryKey<Biome>[][] UNCOMMON_BIOMES = new RegistryKey[][] { { BiomeKeys.ICE_SPIKES, null, null, BiomeKeys.GIANT_SPRUCE_TAIGA, null }, { null, null, null, null, null }, { null, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.FLOWER_FOREST, BiomeKeys.TALL_BIRCH_FOREST, null }, { null, null, BiomeKeys.PLAINS, BiomeKeys.PLAINS, null }, { null, null, null, null, BiomeKeys.BAMBOO_JUNGLE } };
    public static final RegistryKey<Biome>[][] NEAR_MOUNTAIN_BIOMES = new RegistryKey[][] { { BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.GIANT_TREE_TAIGA }, { BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_TUNDRA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_TAIGA }, { BiomeKeys.MEADOW, BiomeKeys.MEADOW, BiomeKeys.MEADOW, BiomeKeys.MEADOW, BiomeKeys.MEADOW }, { BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.FOREST, BiomeKeys.FOREST, BiomeKeys.JUNGLE }, { BiomeKeys.BADLANDS, BiomeKeys.BADLANDS, BiomeKeys.BADLANDS, BiomeKeys.WOODED_BADLANDS_PLATEAU, BiomeKeys.WOODED_BADLANDS_PLATEAU } };
    public static final RegistryKey<Biome>[][] SPECIAL_NEAR_MOUNTAIN_BIOMES = new RegistryKey[][] { { BiomeKeys.ICE_SPIKES, null, null, BiomeKeys.GIANT_SPRUCE_TAIGA, null }, { null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null }, { BiomeKeys.ERODED_BADLANDS, BiomeKeys.ERODED_BADLANDS, null, null, null } };
    public static final RegistryKey<Biome>[][] HILL_BIOMES = new RegistryKey[][] { { BiomeKeys.GRAVELLY_HILLS, BiomeKeys.GRAVELLY_HILLS, BiomeKeys.EXTREME_HILLS, BiomeKeys.WOODED_MOUNTAINS, BiomeKeys.WOODED_MOUNTAINS }, { BiomeKeys.GRAVELLY_HILLS, BiomeKeys.GRAVELLY_HILLS, BiomeKeys.EXTREME_HILLS, BiomeKeys.WOODED_MOUNTAINS, BiomeKeys.WOODED_MOUNTAINS }, { BiomeKeys.EXTREME_HILLS, BiomeKeys.EXTREME_HILLS, BiomeKeys.EXTREME_HILLS, BiomeKeys.WOODED_MOUNTAINS, BiomeKeys.WOODED_MOUNTAINS }, { null, null, null, null, null }, { null, null, null, null, null } };

    public TestBiomeParameters() {
    }

    public static void writeTestBiomeParameters(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
        if (SharedConstants.DEBUG_BIOME_SOURCE) {
            writeDebugBiomeParameters(parameters);
        } else {
            writeOceanBiomes(parameters);
            writeLandBiomes(parameters);
            writeCaveBiomes(parameters);
        }
    }

    public static void writeOceanBiomes(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MUSHROOM_FIELDS_CONTINENTALNESS, DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.MUSHROOM_FIELDS);

        for (int i = 0; i < TEMPERATURE_PARAMETERS.length; ++i) {
            MultiNoiseUtil.ParameterRange parameterRange = TEMPERATURE_PARAMETERS[i];
            writeBiomeParameters(parameters, parameterRange, DEFAULT_PARAMETER, DEEP_OCEAN_CONTINENTALNESS, DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, OCEAN_BIOMES[0][i]);
            writeBiomeParameters(parameters, parameterRange, DEFAULT_PARAMETER, OCEAN_CONTINENTALNESS, DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, OCEAN_BIOMES[1][i]);
        }
    }

    @SuppressWarnings("unused")
    public static void writeLandBiomes(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
        float f = 0.05F;
        float g = 0.06666667F;
        float h = 0.6F;
        float i = 0.73333335F;

        writeMixedBiomes(parameters, MultiNoiseUtil.createParameterRange(-1.0F, -0.93333334F));
        writePlainBiomes(parameters, MultiNoiseUtil.createParameterRange(-0.93333334F, -i));
        writeMountainousBiomes(parameters, MultiNoiseUtil.createParameterRange(-i, -h));
        writePlainBiomes(parameters, MultiNoiseUtil.createParameterRange(-h, -0.4F));
        writeMixedBiomes(parameters, MultiNoiseUtil.createParameterRange(-0.4F, -0.26666668F));
        writeBiomesNearRivers(parameters, MultiNoiseUtil.createParameterRange(-0.26666668F, -f));
        writeRiverBiomes(parameters, MultiNoiseUtil.createParameterRange(-f, f));
        writeBiomesNearRivers(parameters, MultiNoiseUtil.createParameterRange(f, 0.26666668F));
        writeMixedBiomes(parameters, MultiNoiseUtil.createParameterRange(0.26666668F, 0.4F));
        writePlainBiomes(parameters, MultiNoiseUtil.createParameterRange(0.4F, h));
        writeMountainousBiomes(parameters, MultiNoiseUtil.createParameterRange(h, i));
        writePlainBiomes(parameters, MultiNoiseUtil.createParameterRange(i, 0.93333334F));
        writeMixedBiomes(parameters, MultiNoiseUtil.createParameterRange(0.93333334F, 1.0F));
    }

    public static void writeMountainousBiomes(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness) {
        for (int temperature = 0; temperature < TEMPERATURE_PARAMETERS.length; ++temperature) {
            MultiNoiseUtil.ParameterRange temperatureRange = TEMPERATURE_PARAMETERS[temperature];

            for (int humidity = 0; humidity < HUMIDITY_PARAMETERS.length; ++humidity) {
                MultiNoiseUtil.ParameterRange humidityRange = HUMIDITY_PARAMETERS[humidity];

                RegistryKey<Biome> regular = getRegularBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> badlandsOrRegular = getBadlandsOrRegularBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> nearMountain = getNearMountainBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> hill = getHillBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> hillOrShatteredSavanna = getBiomeOrShatteredSavanna(temperature, hill);
                RegistryKey<Biome> peak = getPeakBiome(temperature, humidity, weirdness);

                writeBiomeParameters(parameters, temperatureRange, humidityRange, NEAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[0], weirdness, 0.0F, nearMountain);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[0], weirdness, 0.0F, peak);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, NEAR_INLAND_CONTINENTALNESS, MultiNoiseUtil.combineParameterRange(EROSION_PARAMETERS[1], EROSION_PARAMETERS[2]), weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[1], weirdness, 0.0F, nearMountain);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MID_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[2], weirdness, 0.0F, badlandsOrRegular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, FAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[2], weirdness, 0.0F, nearMountain);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[3], weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, NEAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[4], weirdness, 0.0F, hillOrShatteredSavanna);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[4], weirdness, 0.0F, hill);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[5], weirdness, 0.0F, regular);
            }
        }
    }

    public static void writePlainBiomes(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness) {
        for (int temperature = 0; temperature < TEMPERATURE_PARAMETERS.length; ++temperature) {
            MultiNoiseUtil.ParameterRange temperatureRange = TEMPERATURE_PARAMETERS[temperature];

            for (int humidity = 0; humidity < HUMIDITY_PARAMETERS.length; ++humidity) {
                MultiNoiseUtil.ParameterRange humidityRange = HUMIDITY_PARAMETERS[humidity];

                RegistryKey<Biome> regular = getRegularBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> badlandsOrRegular = getBadlandsOrRegularBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> nearMountain = getNearMountainBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> hill = getHillBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> regularOrShatteredSavanna = getBiomeOrShatteredSavanna(temperature, regular);
                RegistryKey<Biome> mountainSlope = getMountainSlopeBiome(temperature, humidity, weirdness);

                writeBiomeParameters(parameters, temperatureRange, humidityRange, SHORE_CONTINENTALNESS, EROSION_PARAMETERS[0], weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, NEAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[0], weirdness, 0.0F, badlandsOrRegular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[0], weirdness, 0.0F, mountainSlope);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, NEAR_INLAND_CONTINENTALNESS), MultiNoiseUtil.combineParameterRange(EROSION_PARAMETERS[1], EROSION_PARAMETERS[2]), weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[1], weirdness, 0.0F, nearMountain);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MID_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[2], weirdness, 0.0F, badlandsOrRegular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, FAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[2], weirdness, 0.0F, nearMountain);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[3], weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, NEAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[4], weirdness, 0.0F, regularOrShatteredSavanna);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[4], weirdness, 0.0F, hill);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[5], weirdness, 0.0F, regular);
            }
        }
    }

    public static void writeMixedBiomes(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness) {
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, SHORE_CONTINENTALNESS, MultiNoiseUtil.combineParameterRange(EROSION_PARAMETERS[0], EROSION_PARAMETERS[1]), weirdness, 0.0F, BiomeKeys.STONE_SHORE);
        writeBiomeParameters(parameters, NON_FROZEN_TEMPERATURE_PARAMETERS, MultiNoiseUtil.combineParameterRange(HUMIDITY_PARAMETERS[1], HUMIDITY_PARAMETERS[4]), MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, MID_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[5], weirdness, 0.0F, BiomeKeys.SWAMP);

        for (int temperature = 0; temperature < TEMPERATURE_PARAMETERS.length; temperature++) {
            MultiNoiseUtil.ParameterRange temperatureRange = TEMPERATURE_PARAMETERS[temperature];

            for (int humidity = 0; humidity < HUMIDITY_PARAMETERS.length; humidity++) {
                MultiNoiseUtil.ParameterRange humidityRange = HUMIDITY_PARAMETERS[humidity];

                RegistryKey<Biome> regular = getRegularBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> badlandsOrRegular = getBadlandsOrRegularBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> hill = getHillBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> nearMountain = getNearMountainBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> regularOrShatteredSavanna = getBiomeOrShatteredSavanna(temperature, regular);
                RegistryKey<Biome> mountainSlope = getMountainSlopeBiome(temperature, humidity, weirdness);

                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(NEAR_INLAND_CONTINENTALNESS, MID_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[0], weirdness, 0.0F, badlandsOrRegular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, FAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[0], weirdness, 0.0F, mountainSlope);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, NEAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[1], weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MID_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[1], weirdness, 0.0F, badlandsOrRegular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, FAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[1], weirdness, 0.0F, nearMountain);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, NEAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[2], weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[2], weirdness, 0.0F, badlandsOrRegular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[3], weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, NEAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[4], weirdness, 0.0F, regularOrShatteredSavanna);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[4], weirdness, 0.0F, hill);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, FAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[5], weirdness, 0.0F, regular);

                if (temperature == 0 || humidity == 0) {
                    writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, MID_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[5], weirdness, 0.0F, regular);
                }
            }
        }
    }

    public static void writeBiomesNearRivers(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness) {
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, SHORE_CONTINENTALNESS, MultiNoiseUtil.combineParameterRange(EROSION_PARAMETERS[0], EROSION_PARAMETERS[1]), weirdness, 0.0F, BiomeKeys.STONE_SHORE);
        writeBiomeParameters(parameters, FROZEN_TEMPERATURE, DEFAULT_PARAMETER, SHORE_CONTINENTALNESS, MultiNoiseUtil.combineParameterRange(EROSION_PARAMETERS[2], EROSION_PARAMETERS[3]), weirdness, 0.0F, BiomeKeys.SNOWY_BEACH);
        writeBiomeParameters(parameters, NON_FROZEN_TEMPERATURE_PARAMETERS, DEFAULT_PARAMETER, SHORE_CONTINENTALNESS, MultiNoiseUtil.combineParameterRange(EROSION_PARAMETERS[2], EROSION_PARAMETERS[3]), weirdness, 0.0F, BiomeKeys.BEACH);
        writeBiomeParameters(parameters, NON_FROZEN_TEMPERATURE_PARAMETERS, MultiNoiseUtil.combineParameterRange(HUMIDITY_PARAMETERS[1], HUMIDITY_PARAMETERS[4]), MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, MID_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[5], weirdness, 0.0F, BiomeKeys.SWAMP);

        for (int temperature = 0; temperature < TEMPERATURE_PARAMETERS.length; ++temperature) {
            MultiNoiseUtil.ParameterRange temperatureRange = TEMPERATURE_PARAMETERS[temperature];

            for (int humidity = 0; humidity < HUMIDITY_PARAMETERS.length; ++humidity) {
                MultiNoiseUtil.ParameterRange humidityRange = HUMIDITY_PARAMETERS[humidity];

                RegistryKey<Biome> regular = getRegularBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> badlandsOrRegular = getBadlandsOrRegularBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> mountainSlope = getMountainSlopeBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> regularOrShatteredSavanna = getBiomeOrShatteredSavanna(temperature, regular);

                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(NEAR_INLAND_CONTINENTALNESS, MID_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[0], weirdness, 0.0F, badlandsOrRegular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, FAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[0], weirdness, 0.0F, mountainSlope);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, NEAR_INLAND_CONTINENTALNESS, MultiNoiseUtil.combineParameterRange(EROSION_PARAMETERS[1], EROSION_PARAMETERS[2]), weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), MultiNoiseUtil.combineParameterRange(EROSION_PARAMETERS[1], EROSION_PARAMETERS[2]), weirdness, 0.0F, badlandsOrRegular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(NEAR_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[3], weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, NEAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[4], weirdness, 0.0F, regularOrShatteredSavanna);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[4], weirdness, 0.0F, regular);
                writeBiomeParameters(parameters, temperatureRange, humidityRange, FAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[5], weirdness, 0.0F, regular);

                if (temperature == 0 || humidity == 0) {
                    writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, MID_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[5], weirdness, 0.0F, regular);
                }
            }
        }
    }

    public static void writeRiverBiomes(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange weirdness) {
        writeBiomeParameters(parameters, FROZEN_TEMPERATURE, DEFAULT_PARAMETER, SHORE_CONTINENTALNESS, EROSION_PARAMETERS[0], weirdness, 0.0F, weirdness.getMax() < 0.0F ? BiomeKeys.STONE_SHORE : BiomeKeys.FROZEN_RIVER);
        writeBiomeParameters(parameters, NON_FROZEN_TEMPERATURE_PARAMETERS, DEFAULT_PARAMETER, SHORE_CONTINENTALNESS, EROSION_PARAMETERS[0], weirdness, 0.0F, weirdness.getMax() < 0.0F ? BiomeKeys.STONE_SHORE : BiomeKeys.RIVER);
        writeBiomeParameters(parameters, FROZEN_TEMPERATURE, DEFAULT_PARAMETER, NEAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[0], weirdness, 0.0F, BiomeKeys.FROZEN_RIVER);
        writeBiomeParameters(parameters, NON_FROZEN_TEMPERATURE_PARAMETERS, DEFAULT_PARAMETER, NEAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[0], weirdness, 0.0F, BiomeKeys.RIVER);
        writeBiomeParameters(parameters, FROZEN_TEMPERATURE, DEFAULT_PARAMETER, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), MultiNoiseUtil.combineParameterRange(EROSION_PARAMETERS[1], EROSION_PARAMETERS[4]), weirdness, 0.0F, BiomeKeys.FROZEN_RIVER);
        writeBiomeParameters(parameters, NON_FROZEN_TEMPERATURE_PARAMETERS, DEFAULT_PARAMETER, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), MultiNoiseUtil.combineParameterRange(EROSION_PARAMETERS[1], EROSION_PARAMETERS[4]), weirdness, 0.0F, BiomeKeys.RIVER);
        writeBiomeParameters(parameters, FROZEN_TEMPERATURE, DEFAULT_PARAMETER, FAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[5], weirdness, 0.0F, BiomeKeys.FROZEN_RIVER);
        writeBiomeParameters(parameters, NON_FROZEN_TEMPERATURE_PARAMETERS, DEFAULT_PARAMETER, FAR_INLAND_CONTINENTALNESS, EROSION_PARAMETERS[5], weirdness, 0.0F, BiomeKeys.RIVER);
        writeBiomeParameters(parameters, NON_FROZEN_TEMPERATURE_PARAMETERS, MultiNoiseUtil.combineParameterRange(HUMIDITY_PARAMETERS[1], HUMIDITY_PARAMETERS[4]), MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, MID_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[5], weirdness, 0.0F, BiomeKeys.SWAMP);
        writeBiomeParameters(parameters, NON_FROZEN_TEMPERATURE_PARAMETERS, HUMIDITY_PARAMETERS[0], MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, MID_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[5], weirdness, 0.0F, BiomeKeys.RIVER);
        writeBiomeParameters(parameters, FROZEN_TEMPERATURE, DEFAULT_PARAMETER, MultiNoiseUtil.combineParameterRange(SHORE_CONTINENTALNESS, MID_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[5], weirdness, 0.0F, BiomeKeys.FROZEN_RIVER);

        for (int temperature = 0; temperature < TEMPERATURE_PARAMETERS.length; ++temperature) {
            MultiNoiseUtil.ParameterRange temperatureRange = TEMPERATURE_PARAMETERS[temperature];

            for (int humidity = 0; humidity < HUMIDITY_PARAMETERS.length; ++humidity) {
                MultiNoiseUtil.ParameterRange humidityRange = HUMIDITY_PARAMETERS[humidity];

                RegistryKey<Biome> regular = getRegularBiome(temperature, humidity, weirdness);
                RegistryKey<Biome> badlands = getBadlandsBiome(humidity, weirdness);
                RegistryKey<Biome> badlandsOrRegular = temperature == 4 ? badlands : regular;

                writeBiomeParameters(parameters, temperatureRange, humidityRange, MultiNoiseUtil.combineParameterRange(MID_INLAND_CONTINENTALNESS, FAR_INLAND_CONTINENTALNESS), EROSION_PARAMETERS[0], weirdness, 0.0F, badlandsOrRegular);
            }
        }
    }

    public static void writeCaveBiomes(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
        writeCaveBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(0.8F, 1.0F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.DRIPSTONE_CAVES);
        writeCaveBiomeParameters(parameters, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(0.7F, 1.0F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.LUSH_CAVES);
    }

    public static RegistryKey<Biome> getRegularBiome(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
        if (weirdness.getMax() < 0.0F) {
            return COMMON_BIOMES[temperature][humidity];
        } else {
            RegistryKey<Biome> registryKey = UNCOMMON_BIOMES[temperature][humidity];
            return registryKey == null ? COMMON_BIOMES[temperature][humidity] : registryKey;
        }
    }

    public static RegistryKey<Biome> getBadlandsOrRegularBiome(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
        return temperature == 4 ? getBadlandsBiome(humidity, weirdness) : getRegularBiome(temperature, humidity, weirdness);
    }

    public static RegistryKey<Biome> getBiomeOrShatteredSavanna(int temperature, RegistryKey<Biome> biome) {
        return temperature > 2 ? BiomeKeys.SHATTERED_SAVANNA : biome;
    }

    public static RegistryKey<Biome> getBadlandsBiome(int humidity, MultiNoiseUtil.ParameterRange weirdness) {
        if (humidity < 2) {
            return weirdness.getMax() < 0.0F ? BiomeKeys.ERODED_BADLANDS : BiomeKeys.BADLANDS;
        } else {
            return humidity < 3 ? BiomeKeys.BADLANDS : BiomeKeys.WOODED_BADLANDS_PLATEAU;
        }
    }

    public static RegistryKey<Biome> getNearMountainBiome(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
        if (weirdness.getMax() < 0.0F) {
            return NEAR_MOUNTAIN_BIOMES[temperature][humidity];
        } else {
            RegistryKey<Biome> registryKey = SPECIAL_NEAR_MOUNTAIN_BIOMES[temperature][humidity];
            return registryKey == null ? NEAR_MOUNTAIN_BIOMES[temperature][humidity] : registryKey;
        }
    }

    public static RegistryKey<Biome> getPeakBiome(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
        if (temperature <= 2) {
            return weirdness.getMax() < 0.0F ? BiomeKeys.LOFTY_PEAKS : BiomeKeys.SNOWCAPPED_PEAKS;
        } else {
            return temperature == 3 ? BiomeKeys.STONY_PEAKS : getBadlandsBiome(humidity, weirdness);
        }
    }

    public static RegistryKey<Biome> getMountainSlopeBiome(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
        if (temperature >= 3) {
            return getNearMountainBiome(temperature, humidity, weirdness);
        } else {
            return humidity <= 1 ? BiomeKeys.SNOWY_SLOPES : BiomeKeys.GROVE;
        }
    }

    public static RegistryKey<Biome> getHillBiome(int temperature, int humidity, MultiNoiseUtil.ParameterRange weirdness) {
        RegistryKey<Biome> registryKey = HILL_BIOMES[temperature][humidity];
        return registryKey == null ? getRegularBiome(temperature, humidity, weirdness) : registryKey;
    }

    public static void writeBiomeParameters(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome) {
        parameters.add(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.createParameterRange(0.0F), weirdness, offset), biome));
        parameters.add(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.createParameterRange(1.0F), weirdness, offset), biome));
    }

    public static void writeCaveBiomeParameters(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters, MultiNoiseUtil.ParameterRange temperature, MultiNoiseUtil.ParameterRange humidity, MultiNoiseUtil.ParameterRange continentalness, MultiNoiseUtil.ParameterRange erosion, MultiNoiseUtil.ParameterRange weirdness, float offset, RegistryKey<Biome> biome) {
        parameters.add(Pair.of(MultiNoiseUtil.createNoiseHypercube(temperature, humidity, continentalness, erosion, MultiNoiseUtil.createParameterRange(0.2F, 0.9F), weirdness, offset), biome));
    }

    public static void writeDebugBiomeParameters(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.01F, BiomeKeys.PLAINS);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.9F), DEFAULT_PARAMETER, 0.0F, BiomeKeys.DESERT);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.4F), DEFAULT_PARAMETER, 0.0F, BiomeKeys.BADLANDS);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.35F), DEFAULT_PARAMETER, 0.0F, BiomeKeys.DESERT);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.1F), DEFAULT_PARAMETER, 0.0F, BiomeKeys.BADLANDS);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(0.2F), DEFAULT_PARAMETER, 0.0F, BiomeKeys.DESERT);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(1.0F), DEFAULT_PARAMETER, 0.0F, BiomeKeys.BADLANDS);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-1.1F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.SNOWY_TAIGA);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-1.005F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.SNOWY_TAIGA);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.51F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.SNOWY_TAIGA);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.44F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.SNOWY_TAIGA);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.18F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.SNOWY_TAIGA);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.16F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.SNOWY_TAIGA);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.15F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.SNOWY_TAIGA);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.1F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.SNOWY_TAIGA);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(0.25F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.SNOWY_TAIGA);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(1.0F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.SNOWY_TAIGA);
    }

    public static void method_37847(ImmutableList.Builder<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> parameters) {
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.2F, -0.05F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.BADLANDS);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.05F, 1.0F), DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.15F, 0.15F), 0.0F, BiomeKeys.DESERT);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-1.0F, -0.2F), DEFAULT_PARAMETER, DEFAULT_PARAMETER, 0.0F, BiomeKeys.OCEAN);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.05F, 1.0F), DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-1.0F, -0.15F), 0.0F, BiomeKeys.PLAINS);
        writeBiomeParameters(parameters, DEFAULT_PARAMETER, DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(-0.05F, 1.0F), DEFAULT_PARAMETER, MultiNoiseUtil.createParameterRange(0.15F, 1.0F), 0.0F, BiomeKeys.PLAINS);
    }
}
