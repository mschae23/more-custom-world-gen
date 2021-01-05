# More Custom World Generation

## Custom layered biome source
Example world settings for a vanilla overworld, using `morecustomworldgen:custom_layered`:

```
{
  "bonus_chest": false,
  "dimensions": {
    "minecraft:overworld": {
      "type": "minecraft:overworld",
      "generator": {
        "biome_source": {
          "seed": 123456789,
          "config": {
            "biome_categories": [
              {
                "biome": "minecraft:beach",
                "category": "beach"
              },
              {
                "biome": "minecraft:snowy_beach",
                "category": "beach"
              },
              {
                "biome": "minecraft:desert",
                "category": "desert"
              },
              {
                "biome": "minecraft:desert_hills",
                "category": "desert"
              },
              {
                "biome": "minecraft:desert_lakes",
                "category": "desert"
              },
              {
                "biome": "minecraft:gravelly_mountains",
                "category": "mountains"
              },
              {
                "biome": "minecraft:modified_gravelly_mountains",
                "category": "mountains"
              },
              {
                "biome": "minecraft:mountain_edge",
                "category": "mountains"
              },
              {
                "biome": "minecraft:mountains",
                "category": "mountains"
              },
              {
                "biome": "minecraft:wooded_mountains",
                "category": "mountains"
              },
              {
                "biome": "minecraft:birch_forest",
                "category": "forest"
              },
              {
                "biome": "minecraft:birch_forest_hills",
                "category": "forest"
              },
              {
                "biome": "minecraft:dark_forest",
                "category": "forest"
              },
              {
                "biome": "minecraft:dark_forest_hills",
                "category": "forest"
              },
              {
                "biome": "minecraft:flower_forest",
                "category": "forest"
              },
              {
                "biome": "minecraft:forest",
                "category": "forest"
              },
              {
                "biome": "minecraft:tall_birch_forest",
                "category": "forest"
              },
              {
                "biome": "minecraft:tall_birch_hills",
                "category": "forest"
              },
              {
                "biome": "minecraft:ice_spikes",
                "category": "icy"
              },
              {
                "biome": "minecraft:snowy_mountains",
                "category": "icy"
              },
              {
                "biome": "minecraft:snowy_tundra",
                "category": "icy"
              },
              {
                "biome": "minecraft:bamboo_jungle",
                "category": "jungle"
              },
              {
                "biome": "minecraft:bamboo_jungle_hills",
                "category": "jungle"
              },
              {
                "biome": "minecraft:jungle",
                "category": "jungle"
              },
              {
                "biome": "minecraft:jungle_edge",
                "category": "jungle"
              },
              {
                "biome": "minecraft:jungle_hills",
                "category": "jungle"
              },
              {
                "biome": "minecraft:modified_jungle",
                "category": "jungle"
              },
              {
                "biome": "minecraft:modified_jungle_edge",
                "category": "jungle"
              },
              {
                "biome": "minecraft:badlands",
                "category": "badlands"
              },
              {
                "biome": "minecraft:eroded_badlands",
                "category": "badlands"
              },
              {
                "biome": "minecraft:modified_badlands_plateau",
                "category": "badlands"
              },
              {
                "biome": "minecraft:modified_wooded_badlands_plateau",
                "category": "badlands"
              },
              {
                "biome": "minecraft:badlands_plateau",
                "category": "badlands_plateau"
              },
              {
                "biome": "minecraft:wooded_badlands_plateau",
                "category": "badlands_plateau"
              },
              {
                "biome": "minecraft:mushroom_fields",
                "category": "mushroom_fields"
              },
              {
                "biome": "minecraft:mushroom_field_shore",
                "category": "mushroom_fields"
              },
              {
                "biome": "minecraft:stone_shore",
                "category": "none"
              },
              {
                "biome": "minecraft:ocean",
                "category": "ocean"
              },
              {
                "biome": "minecraft:warm_ocean",
                "category": "ocean"
              },
              {
                "biome": "minecraft:lukewarm_ocean",
                "category": "ocean"
              },
              {
                "biome": "minecraft:cold_ocean",
                "category": "ocean"
              },
              {
                "biome": "minecraft:frozen_ocean",
                "category": "ocean"
              },
              {
                "biome": "minecraft:deep_ocean",
                "category": "ocean"
              },
              {
                "biome": "minecraft:deep_warm_ocean",
                "category": "ocean"
              },
              {
                "biome": "minecraft:deep_lukewarm_ocean",
                "category": "ocean"
              },
              {
                "biome": "minecraft:deep_cold_ocean",
                "category": "ocean"
              },
              {
                "biome": "minecraft:deep_frozen_ocean",
                "category": "ocean"
              },
              {
                "biome": "minecraft:plains",
                "category": "plains"
              },
              {
                "biome": "minecraft:sunflower_plains",
                "category": "plains"
              },
              {
                "biome": "minecraft:frozen_river",
                "category": "river"
              },
              {
                "biome": "minecraft:river",
                "category": "river"
              },
              {
                "biome": "minecraft:savanna",
                "category": "savanna"
              },
              {
                "biome": "minecraft:savanna_plateau",
                "category": "savanna"
              },
              {
                "biome": "minecraft:shattered_savanna",
                "category": "savanna"
              },
              {
                "biome": "minecraft:shattered_savanna_plateau",
                "category": "savanna"
              },
              {
                "biome": "minecraft:swamp",
                "category": "swamp"
              },
              {
                "biome": "minecraft:swamp_hills",
                "category": "swamp"
              },
              {
                "biome": "minecraft:giant_spruce_taiga",
                "category": "taiga"
              },
              {
                "biome": "minecraft:giant_spruce_taiga_hills",
                "category": "taiga"
              },
              {
                "biome": "minecraft:giant_tree_taiga",
                "category": "taiga"
              },
              {
                "biome": "minecraft:giant_tree_taiga_hills",
                "category": "taiga"
              },
              {
                "biome": "minecraft:snowy_taiga",
                "category": "taiga"
              },
              {
                "biome": "minecraft:snowy_taiga_hills",
                "category": "taiga"
              },
              {
                "biome": "minecraft:snowy_taiga_mountains",
                "category": "taiga"
              },
              {
                "biome": "minecraft:taiga",
                "category": "taiga"
              },
              {
                "biome": "minecraft:taiga_hills",
                "category": "taiga"
              },
              {
                "biome": "minecraft:taiga_mountains",
                "category": "taiga"
              }
            ],
            "continents": {
              "continent_chance": 10,
              "origin_continent": true,
              "island_chance": 2
            },
            "climates": {
              "snowy_climate_weight": 1,
              "cool_climate_weight": 1,
              "dry_climate_weight": 4,

              "climate_size": 2,
              "rare_island_chance": 100
            },
            "biome_layout": {
              "base_biomes": {
                "dry": [
                  {
                    "biome": "minecraft:desert",
                    "weight": 3
                  },
                  {
                    "biome": "minecraft:savanna",
                    "weight": 2
                  },
                  {
                    "biome": "minecraft:plains",
                    "weight": 1
                  }
                ],
                "temperate": [
                  {
                    "biome": "minecraft:forest",
                    "weight": 1
                  },
                  {
                    "biome": "minecraft:dark_forest",
                    "weight": 1
                  },
                  {
                    "biome": "minecraft:mountains",
                    "weight": 1
                  },
                  {
                    "biome": "minecraft:plains",
                    "weight": 1
                  },
                  {
                    "biome": "minecraft:birch_forest",
                    "weight": 1
                  },
                  {
                    "biome": "minecraft:swamp",
                    "weight": 1
                  }
                ],
                "cool": [
                  {
                    "biome": "minecraft:forest",
                    "weight": 1
                  },
                  {
                    "biome": "minecraft:mountains",
                    "weight": 1
                  },
                  {
                    "biome": "minecraft:taiga",
                    "weight": 1
                  },
                  {
                    "biome": "minecraft:plains",
                    "weight": 1
                  }
                ],
                "snowy": [
                  {
                    "biome": "minecraft:snowy_tundra",
                    "weight": 3
                  },
                  {
                    "biome": "minecraft:snowy_taiga",
                    "weight": 1
                  }
                ],
                "special_dry": [
                  {
                    "biome": "minecraft:wooded_badlands_plateau",
                    "weight": 2
                  },
                  {
                    "biome": "minecraft:badlands_plateau",
                    "weight": 1
                  }
                ],
                "special_temperate": [
                  {
                    "biome": "minecraft:jungle",
                    "weight": 1
                  }
                ],
                "special_cool": [
                  {
                    "biome": "minecraft:giant_tree_taiga",
                    "weight": 1
                  }
                ],
                "special_snowy": [

                ],
                "rare_island": [
                  {
                    "biome": "minecraft:mushroom_fields",
                    "weight": 1
                  }
                ],
                "default_land": "minecraft:plains"
              },
              "large_inner_biomes": [
                {
                  "biome": "minecraft:jungle",
                  "inner_biome": "minecraft:bamboo_jungle",
                  "chance": 10
                }
              ],
              "biome_edges": {
                "ignored_categories": [
                  "mountains"
                ],
                "category_edge_biomes": [
                  {
                    "biome": "minecraft:wooded_badlands_plateau",
                    "edge_biome": "minecraft:badlands"
                  },
                  {
                    "biome": "minecraft:badlands_plateau",
                    "edge_biome": "minecraft:badlands"
                  },
                  {
                    "biome": "minecraft:giant_tree_taiga",
                    "edge_biome": "minecraft:taiga"
                  }
                ],
                "edge_biomes": [
                  {
                    "biome": "minecraft:desert",
                    "bordering_biomes": [
                      "minecraft:snowy_tundra"
                    ],
                    "edge_biome": "minecraft:wooded_mountains"
                  },
                  {
                    "biome": "minecraft:swamp",
                    "bordering_biomes": [
                      "minecraft:desert",
                      "minecraft:snowy_taiga",
                      "minecraft:snowy_tundra"
                    ],
                    "edge_biome": "minecraft:plains"
                  },
                  {
                    "biome": "minecraft:swamp",
                    "bordering_biomes": [
                      "minecraft:jungle",
                      "minecraft:bamboo_jungle"
                    ],
                    "edge_biome": "minecraft:jungle_edge"
                  }
                ]
              },
              "hill_biomes": {
                "ignored_categories": [

                ],
                "special_hill_biomes": [
                  {
                    "biome": "minecraft:plains",
                    "special_hill_biome": "minecraft:sunflower_plains"
                  },
                  {
                    "biome": "minecraft:desert",
                    "special_hill_biome": "minecraft:desert_lakes"
                  },
                  {
                    "biome": "minecraft:mountains",
                    "special_hill_biome": "minecraft:gravelly_mountains"
                  },
                  {
                    "biome": "minecraft:forest",
                    "special_hill_biome": "minecraft:flower_forest"
                  },
                  {
                    "biome": "minecraft:taiga",
                    "special_hill_biome": "minecraft:taiga_mountains"
                  },
                  {
                    "biome": "minecraft:swamp",
                    "special_hill_biome": "minecraft:swamp_hills"
                  },
                  {
                    "biome": "minecraft:snowy_tundra",
                    "special_hill_biome": "minecraft:ice_spikes"
                  },
                  {
                    "biome": "minecraft:jungle",
                    "special_hill_biome": "minecraft:modified_jungle"
                  },
                  {
                    "biome": "minecraft:jungle_edge",
                    "special_hill_biome": "minecraft:modified_jungle_edge"
                  },
                  {
                    "biome": "minecraft:birch_forest",
                    "special_hill_biome": "minecraft:tall_birch_forest"
                  },
                  {
                    "biome": "minecraft:birch_forest_hills",
                    "special_hill_biome": "minecraft:tall_birch_hills"
                  },
                  {
                    "biome": "minecraft:dark_forest",
                    "special_hill_biome": "minecraft:dark_forest_hills"
                  },
                  {
                    "biome": "minecraft:snowy_taiga",
                    "special_hill_biome": "minecraft:snowy_taiga_mountains"
                  },
                  {
                    "biome": "minecraft:giant_tree_taiga",
                    "special_hill_biome": "minecraft:giant_spruce_taiga"
                  },
                  {
                    "biome": "minecraft:giant_tree_taiga_hills",
                    "special_hill_biome": "minecraft:giant_spruce_taiga_hills"
                  },
                  {
                    "biome": "minecraft:wooded_mountains",
                    "special_hill_biome": "minecraft:modified_gravelly_mountains"
                  },
                  {
                    "biome": "minecraft:savanna",
                    "special_hill_biome": "minecraft:shattered_savanna"
                  },
                  {
                    "biome": "minecraft:savanna_plateau",
                    "special_hill_biome": "minecraft:shattered_savanna_plateau"
                  },
                  {
                    "biome": "minecraft:badlands",
                    "special_hill_biome": "minecraft:eroded_badlands"
                  },
                  {
                    "biome": "minecraft:wooded_badlands_plateau",
                    "special_hill_biome": "minecraft:modified_wooded_badlands_plateau"
                  },
                  {
                    "biome": "minecraft:badlands_plateau",
                    "special_hill_biome": "minecraft:modified_badlands_plateau"
                  }
                ],
                "hill_biomes": [
                  {
                    "biome": "minecraft:desert",
                    "hill_biome": "minecraft:desert_hills"
                  },
                  {
                    "biome": "minecraft:forest",
                    "hill_biome": "minecraft:wooded_hills"
                  },
                  {
                    "biome": "minecraft:birch_forest",
                    "hill_biome": "minecraft:birch_forest_hills"
                  },
                  {
                    "biome": "minecraft:dark_forest",
                    "hill_biome": "minecraft:plains"
                  },
                  {
                    "biome": "minecraft:taiga",
                    "hill_biome": "minecraft:taiga_hills"
                  },
                  {
                    "biome": "minecraft:giant_tree_taiga",
                    "hill_biome": "minecraft:giant_tree_taiga_hills"
                  },
                  {
                    "biome": "minecraft:snowy_taiga",
                    "hill_biome": "minecraft:snowy_taiga_hills"
                  },
                  {
                    "biome": "minecraft:snowy_tundra",
                    "hill_biome": "minecraft:snowy_mountains"
                  },
                  {
                    "biome": "minecraft:jungle",
                    "hill_biome": "minecraft:jungle_hills"
                  },
                  {
                    "biome": "minecraft:bamboo_jungle",
                    "hill_biome": "minecraft:bamboo_jungle_hills"
                  },
                  {
                    "biome": "minecraft:ocean",
                    "hill_biome": "minecraft:deep_ocean"
                  },
                  {
                    "biome": "minecraft:lukewarm_ocean",
                    "hill_biome": "minecraft:deep_lukewarm_ocean"
                  },
                  {
                    "biome": "minecraft:cold_ocean",
                    "hill_biome": "minecraft:deep_cold_ocean"
                  },
                  {
                    "biome": "minecraft:frozen_river",
                    "hill_biome": "minecraft:deep_frozen_ocean"
                  },
                  {
                    "biome": "minecraft:mountains",
                    "hill_biome": "minecraft:wooded_mountains"
                  },
                  {
                    "biome": "minecraft:savanna",
                    "hill_biome": "minecraft:savanna_plateau"
                  },
                  {
                    "category": "badlands_plateau",
                    "hill_biome": "minecraft:badlands"
                  }
                ],
                "complex_hill_biomes": [
                  {
                    "biomes": [
                      "minecraft:plains"
                    ],
                    "chance": 1,
                    "hill_biomes": [
                      "minecraft:forest",
                      "minecraft:forest",
                      "minecraft:wooded_hills"
                    ]
                  },
                  {
                    "biomes": [
                      "minecraft:deep_ocean",
                      "minecraft:deep_lukewarm_ocean",
                      "minecraft:deep_cold_ocean",
                      "minecraft:deep_frozen_ocean"
                    ],
                    "chance": 3,
                    "hill_biomes": [
                      "minecraft:plains",
                      "minecraft:forest"
                    ]
                  }
                ]
              },
              "spot_inner_biomes": [
                {
                  "biome": "minecraft:plains",
                  "inner_biome": "minecraft:sunflower_plains",
                  "chance": 57
                }
              ],
              "shore_biomes": {
                "ignored_categories": [

                ],
                "overrides": [
                  {
                    "biomes": [
                      "minecraft:mushroom_fields"
                    ],
                    "bordering_biomes": [

                    ],
                    "or_bordering_biomes": [

                    ],
                    "shore_biome": "minecraft:mushroom_field_shore"
                  },
                  {
                    "biomes": [
                      "minecraft:bamboo_jungle",
                      "minecraft:bamboo_jungle_hills",
                      "minecraft:jungle",
                      "minecraft:jungle_hills",
                      "minecraft:jungle_edge",
                      "minecraft:modified_jungle",
                      "minecraft:modified_jungle_edge"
                    ],
                    "bordering_biomes": [

                    ],
                    "or_bordering_biomes": [
                      "minecraft:bamboo_jungle",
                      "minecraft:bamboo_jungle_hills",
                      "minecraft:jungle",
                      "minecraft:jungle_hills",
                      "minecraft:jungle_edge",
                      "minecraft:modified_jungle",
                      "minecraft:modified_jungle_edge",
                      "minecraft:forest",
                      "minecraft:taiga"
                    ],
                    "shore_biome": "minecraft:jungle_edge",
                    "negative": true,
                    "continue": true
                  },
                  {
                    "biomes": [
                      "minecraft:mountains",
                      "minecraft:wooded_mountains",
                      "minecraft:mountain_edge"
                    ],
                    "bordering_biomes": [

                    ],
                    "or_bordering_biomes": [

                    ],
                    "shore_biome": "minecraft:stone_shore"
                  },
                  {
                    "biomes": [
                      "minecraft:snowy_beach",
                      "minecraft:frozen_river",
                      "minecraft:snowy_tundra",
                      "minecraft:snowy_mountains",
                      "minecraft:ice_spikes",
                      "minecraft:snowy_taiga",
                      "minecraft:snowy_taiga_hills",
                      "minecraft:snowy_taiga_mountains",
                      "minecraft:frozen_ocean"
                    ],
                    "bordering_biomes": [

                    ],
                    "or_bordering_biomes": [

                    ],
                    "shore_biome": "minecraft:snowy_beach"
                  },
                  {
                    "biomes": [
                      "minecraft:badlands",
                      "minecraft:wooded_badlands_plateau"
                    ],
                    "bordering_biomes": [
                      "minecraft:ocean",
                      "minecraft:warm_ocean",
                      "minecraft:lukewarm_ocean",
                      "minecraft:cold_ocean",
                      "minecraft:frozen_ocean",
                      "minecraft:deep_ocean",
                      "minecraft:deep_warm_ocean",
                      "minecraft:deep_lukewarm_ocean",
                      "minecraft:deep_cold_ocean",
                      "minecraft:deep_frozen_ocean"
                    ],
                    "or_bordering_biomes": [
                      "minecraft:badlands",
                      "minecraft:wooded_badlands_plateau",
                      "minecraft:badlands_plateau",
                      "minecraft:eroded_badlands",
                      "minecraft:modified_wooded_badlands_plateau",
                      "minecraft:modified_badlands_plateau"
                    ],
                    "shore_biome": "minecraft:desert",
                    "negative": true,
                    "check_if_bordering_ocean": false
                  },
                  {
                    "biomes": [
                      "minecraft:river"
                    ],
                    "bordering_biomes": [

                    ],
                    "or_bordering_biomes": [

                    ],
                    "shore_biome": "minecraft:river",
                    "check_if_bordering_ocean": false
                  },
                  {
                    "biomes": [
                      "minecraft:swamp"
                    ],
                    "bordering_biomes": [

                    ],
                    "or_bordering_biomes": [

                    ],
                    "shore_biome": "minecraft:swamp",
                    "check_if_bordering_ocean": false
                  }
                ],
                "default_beach": "minecraft:beach"
              },
              "shallow_ocean_biomes": [
                "minecraft:ocean",
                "minecraft:warm_ocean",
                "minecraft:lukewarm_ocean",
                "minecraft:cold_ocean",
                "minecraft:frozen_ocean"
              ],
              "forest_biome": "minecraft:forest",
              "plains_biome": "minecraft:plains"
            },
            "biome_size": {
              "biome_scale": 4,
              "biome_and_river_scale": 0,
              "ocean_climate_size": 6
            },
            "ocean_category": "ocean",
            "rivers": {
              "generate_rivers": true,
              "overrides": [
                {
                  "biomes": [
                    "minecraft:snowy_tundra"
                  ],
                  "river": "minecraft:frozen_river"
                },
                {
                  "biomes": [
                    "minecraft:mushroom_fields",
                    "minecraft:mushroom_field_shore"
                  ],
                  "river": "minecraft:mushroom_field_shore"
                }
              ],
              "river": "minecraft:river"
            },
            "ocean_biomes": {
              "apply_ocean_temperatures": true,
              "ocean": "minecraft:ocean",
              "warm_ocean": "minecraft:warm_ocean",
              "lukewarm_ocean": "minecraft:lukewarm_ocean",
              "cold_ocean": "minecraft:cold_ocean",
              "frozen_ocean": "minecraft:frozen_ocean",
              "deep_ocean": "minecraft:deep_ocean",
              "deep_warm_ocean": "minecraft:deep_warm_ocean",
              "deep_lukewarm_ocean": "minecraft:deep_lukewarm_ocean",
              "deep_cold_ocean": "minecraft:deep_cold_ocean",
              "deep_frozen_ocean": "minecraft:deep_frozen_ocean"
            }
          },
          "type": "morecustomworldgen:custom_layered"
        },
        "seed": 123456789,
        "settings": "minecraft:overworld",
        "type": "minecraft:noise"
      }
    },
    "minecraft:the_nether": {
      "type": "minecraft:the_nether",
      "generator": {
        "biome_source": {
          "preset": "minecraft:nether",
          "seed": 123456789,
          "type": "minecraft:multi_noise"
        },
        "seed": 123456789,
        "settings": "minecraft:nether",
        "type": "minecraft:noise"
      }
    },
    "minecraft:the_end": {
      "type": "minecraft:the_end",
      "generator": {
        "biome_source": {
          "seed": 123456789,
          "type": "minecraft:the_end"
        },
        "seed": 123456789,
        "settings": "minecraft:end",
        "type": "minecraft:noise"
      }
    }
  },
  "seed": 123456789,
  "generate_features": true
}
```

## Decorators
Example configs for the decorators added by this mod.

### `morecustomworldgen:offset_vertically`
```
{
  "config": {
    "offset": 10,
    "factor": 1
  },
  "type": "morecustomworldgen:offset_vertically"
}
```

### `morecustomworldgen:range_mask`
Same config as `minecraft:range`.
```
{
  "config": {
    "bottom_offset": 10,
    "top_offset": 0,
    "maximum": 20
  },
  "type": "morecustomworldgen:range_mask"
}
```

### `morecustomworldgen:count_noise_biased_3d`
Same config as `minecraft:count_noise_biased`.
```
{
  "config": {
    "noise_to_count_ratio": 1,
    "noise_factor": 2.5,
    "noise_offset": 0
  },
  "type": "morecustomworldgen:count_noise_biased_3d"
}
```

### `morecustomworldgen:spread_vertically`
```
{
  "config": {
    "factor": 2
  },
  "type": "morecustomworldgen:spread_vertically"
}
```

## Features
Example configs for the features added by this mod.

### `morecustomworldgen:disk`
Same config as `minecraft:disk`.

### `morecustomworldgen:fill_layers`
```
{
  "config": {
    "target": {
      "block": "minecraft:air",
      "predicate_type": "minecraft:block_match"
    },
    "start_height": 10,
    "height": 5,
    "state": {
      "Name": "minecraft:dirt"
    }
  },
  "type": "morecustomworldgen:fill_layers"
}
```


This mod is available under the MIT License.
