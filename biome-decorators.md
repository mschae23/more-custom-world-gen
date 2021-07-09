# Biome decorators

Biome decorators are a new, experimental system similar to feature decorators, but for biomes. It should be even more
customizable than the `custom_layered` biome source.

## Init Decorators
These biome decorators ignore the biomes from the parent biome source.

### `from_biome_source_init`
This decorator ignores biomes from a parent biome source, and uses the specified biome source instead.

- `biome_source`: The biome source it will use.

### `weighted_init`
Ignores parent biome source. Chooses from a list of biomes to place.

- `biomes`: List of:
    - `chance`: Can be either an int (chance 1/x), or an object:
        - `numerator`
        - `denominator`
    - `biome`: Registry ID of biome to place.
- `default_biome`: Registry ID of biome to place if none of the biomes in the list match (random).

### `scale`
Scales the biome layout. TODO: Make this more configurable.

- `scale_type`: Can be either `normal`, `simple` or `fuzzy`.

### `increase_edge_curvature`
Increases edge curvature of biome layout. TODO: Replace this with something more configurable.

No config.

## General biome decorators

### `nope`
This just returns the same biome.

No config.

## "Replacing" biome decorators

### `bordering_replace`
Replaces the current biome with another one if it is bordering other specified biomes.

- `center_and`: If the center *and* the bordering biomes have to match, or the center *or* the bordering biomes.
  (Defaults to `true`)
- `negative_center`: The center biome will have to *not* match the specified center biomes when this is active. (
  Defaults to `false`)
- `negative`: The bordering biomes will have to *not* match the specified biomes when this is active. (Defaults
  to `false`)
- `center_biomes`: Registry ID (or ID list) of the biome that the center biome is compared with.
- `bordering_biomes_and`: Registry ID (or ID list) of the biome that the bordering biomes are compared with. Using this,
  all biomes around the center one have to be in this list for it to match. Can be empty.
- `bordering_biomes_or`: Registry ID (or ID list) of the biome that the bordering biomes are compared with. Using this,
  at least one biome around the center one has to be in this list for it to match. Can be empty.
- `chance`: Int or chance object. The biome is only going to be replaced with a certain chance.
- `biome`: The biome that is going to be used instead of the current biome.

### `array_bordering_replace`
Replaces the current biome with another one if it is bordering other specified biomes.

- `ignored_biomes`: List of ignored biomes.
- `entries`: List of objects in the format of `bordering_replace`.

### `weighted_replace`
Replaces specific biomes with other ones, determined by a weighted list.

- `ignored_biomes`: List of biomes that should be ignored.
- `whitelisted_biomes`: List of biomes that can be replaced. (Optional; empty list means all biomes.)
    - Note that if both `ignored_biomes` and `whitelisted_biomes` are empty, this essentially behaves like an init
      decorator.
- `biomes`: Weighted list of biomes.
    - `weight`: Int.
    - `biome`: The biome to generate.

### `array_weighted_replace`
Replaces specific biomes with other ones, determined by lists of weighted lists.

### `simple_replace`
Replaces specific biomes with another one.

- `biomes`: List of biome entries.
    - `comparing_biome`: Only replaces if this is the current biome.
    - `chance`: Can be an int (1/x chance) or a chance object. Biome is only replaced with this chance.
    - `biome`: The biome to replace the comparing biome with.

## "Decorated" biome sources

### `decorated`
Works similarly to the `minecraft:decorated` feature.

- `biome_source`: The biome source this decorator is decorating.
- `decorator`: The decorator.
- `seed`: Should be the same as the world seed.
- `salt`: This is like a seed for this decorator (so that random values aren't the same for multiple ones).

### `array_decorated`
Similar to `decorated`, but can be given an array of decorators. This should be preferred.

- `seed`: This should match the world seed.
- `biome_source`: The biome source that is being decorated. Optional; if not given, parent biome is going to
  be `minecraft:the_void`.
- `decorators`: An array of decorator entries that will be layered / chained:
    - `salt`: This is like a seed for this decorator (so that random values aren't the same for multiple ones).
    - `decorator`: The biome decorator.

## Vanilla example
Example dimension which should generate almost exactly like `vanilla_layered`
(when this is complete, not yet, obviously).

```json
{
  "type": "minecraft:overworld",
  "generator": {
    "biome_source": {
      "seed": 123456789,
      "decorators": [
        {
          "salt": 1,
          "decorator": {
            "biomes": [
              {
                "chance": 10,
                "biome": "minecraft:plains"
              }
            ],
            "default_biome": "minecraft:ocean",
            "type": "morecustomworldgen:weighted_init"
          }
        },
        {
          "salt": 2000,
          "decorator": {
            "scale_type": "fuzzy",
            "type": "morecustomworldgen:scale"
          }
        },
        {
          "salt": 1,
          "decorator": {
            "type": "morecustomworldgen:increase_edge_curvature"
          }
        },
        {
          "salt": 2001,
          "decorator": {
            "scale_type": "normal",
            "type": "morecustomworldgen:scale"
          }
        },
        {
          "salt": 2,
          "decorator": {
            "type": "morecustomworldgen:increase_edge_curvature"
          }
        },
        {
          "salt": 50,
          "decorator": {
            "type": "morecustomworldgen:increase_edge_curvature"
          }
        },
        {
          "salt": 70,
          "decorator": {
            "type": "morecustomworldgen:increase_edge_curvature"
          }
        },
        {
          "salt": 2,
          "decorator": {
            "type": "morecustomworldgen:bordering_replace",
            "value": {
              "bordering_biomes_and": "minecraft:ocean",
              "bordering_biomes_or": [],
              "chance": 2,
              "biome": "minecraft:plains",
              "center_and": true,
              "negative_center": false,
              "negative": false,
              "center_biomes": "minecraft:ocean"
            }
          }
        },
        {
          "salt": 2,
          "decorator": {
            "ignored_biomes": [
              "minecraft:ocean"
            ],
            "whitelisted_biomes": [],
            "biomes": [
              {
                "weight": 1,
                "biome": "morecustomworldgen:cool"
              },
              {
                "weight": 1,
                "biome": "morecustomworldgen:snowy"
              },
              {
                "weight": 4,
                "biome": "morecustomworldgen:dry"
              }
            ],
            "type": "morecustomworldgen:weighted_replace"
          }
        },
        {
          "salt": 3,
          "decorator": {
            "type": "morecustomworldgen:increase_edge_curvature"
          }
        },
        {
          "salt": 2,
          "decorator": {
            "type": "morecustomworldgen:bordering_replace",
            "value": {
              "bordering_biomes_and": [],
              "bordering_biomes_or": [
                "morecustomworldgen:cool",
                "morecustomworldgen:snowy"
              ],
              "chance": 1,
              "biome": "morecustomworldgen:temperate",
              "center_and": true,
              "negative_center": false,
              "negative": false,
              "center_biomes": "morecustomworldgen:dry"
            }
          }
        },
        {
          "salt": 2,
          "decorator": {
            "type": "morecustomworldgen:bordering_replace",
            "value": {
              "bordering_biomes_and": [],
              "bordering_biomes_or": [
                "morecustomworldgen:dry",
                "morecustomworldgen:temperate"
              ],
              "chance": 1,
              "biome": "morecustomworldgen:cool",
              "center_and": true,
              "negative_center": false,
              "negative": false,
              "center_biomes": "morecustomworldgen:snowy"
            }
          }
        },
        {
          "salt": 3,
          "decorator": {
            "biomes": [
              {
                "comparing_biome": "morecustomworldgen:dry",
                "chance": 13,
                "biome": "morecustomworldgen:special_dry"
              },
              {
                "comparing_biome": "morecustomworldgen:temperate",
                "chance": 13,
                "biome": "morecustomworldgen:special_temperate"
              },
              {
                "comparing_biome": "morecustomworldgen:cool",
                "chance": 13,
                "biome": "morecustomworldgen:special_cool"
              },
              {
                "comparing_biome": "morecustomworldgen:snowy",
                "chance": 13,
                "biome": "morecustomworldgen:special_snowy"
              }
            ],
            "type": "morecustomworldgen:simple_replace"
          }
        },
        {
          "salt": 2002,
          "decorator": {
            "scale_type": "normal",
            "type": "morecustomworldgen:scale"
          }
        },
        {
          "salt": 2003,
          "decorator": {
            "scale_type": "normal",
            "type": "morecustomworldgen:scale"
          }
        },
        {
          "salt": 4,
          "decorator": {
            "type": "morecustomworldgen:increase_edge_curvature"
          }
        },
        {
          "salt": 5,
          "decorator": {
            "type": "morecustomworldgen:bordering_replace",
            "value": {
              "bordering_biomes_and": "minecraft:ocean",
              "bordering_biomes_or": [],
              "chance": 100,
              "biome": "minecraft:mushroom_fields",
              "center_and": true,
              "negative_center": false,
              "negative": false,
              "center_biomes": "minecraft:ocean"
            }
          }
        },
        {
          "salt": 4,
          "decorator": {
            "type": "morecustomworldgen:bordering_replace",
            "value": {
              "bordering_biomes_and": "minecraft:ocean",
              "bordering_biomes_or": [],
              "chance": 1,
              "biome": "minecraft:deep_ocean",
              "center_and": true,
              "negative_center": false,
              "negative": false,
              "center_biomes": "minecraft:ocean"
            }
          }
        },
        {
          "salt": 200,
          "decorator": {
            "biomes": [
              {
                "comparing_biomes": "morecustomworldgen:dry",
                "biomes": [
                  {
                    "weight": 3,
                    "biome": "minecraft:desert"
                  },
                  {
                    "weight": 2,
                    "biome": "minecraft:savanna"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:plains"
                  }
                ]
              },
              {
                "comparing_biomes": "morecustomworldgen:special_dry",
                "biomes": [
                  {
                    "weight": 2,
                    "biome": "minecraft:wooded_badlands_plateau"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:badlands_plateau"
                  }
                ]
              },
              {
                "comparing_biomes": "morecustomworldgen:temperate",
                "biomes": [
                  {
                    "weight": 1,
                    "biome": "minecraft:forest"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:dark_forest"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:mountains"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:plains"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:birch_forest"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:swamp"
                  }
                ]
              },
              {
                "comparing_biomes": "morecustomworldgen:special_temperate",
                "biomes": [
                  {
                    "weight": 1,
                    "biome": "minecraft:jungle"
                  }
                ]
              },
              {
                "comparing_biomes": "morecustomworldgen:cool",
                "biomes": [
                  {
                    "weight": 1,
                    "biome": "minecraft:forest"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:mountains"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:taiga"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:plains"
                  }
                ]
              },
              {
                "comparing_biomes": "morecustomworldgen:special_cool",
                "biomes": [
                  {
                    "weight": 1,
                    "biome": "minecraft:giant_tree_taiga"
                  }
                ]
              },
              {
                "comparing_biomes": [
                  "morecustomworldgen:snowy",
                  "morecustomworldgen:special_snowy"
                ],
                "biomes": [
                  {
                    "weight": 3,
                    "biome": "minecraft:snowy_tundra"
                  },
                  {
                    "weight": 1,
                    "biome": "minecraft:snowy_taiga"
                  }
                ]
              }
            ],
            "type": "morecustomworldgen:array_weighted_replace"
          }
        },
        {
          "salt": 1001,
          "decorator": {
            "biomes": [
              {
                "comparing_biome": "minecraft:jungle",
                "chance": 10,
                "biome": "minecraft:bamboo_jungle"
              }
            ],
            "type": "morecustomworldgen:simple_replace"
          }
        },
        {
          "salt": 1000,
          "decorator": {
            "scale_type": "normal",
            "type": "morecustomworldgen:scale"
          }
        },
        {
          "salt": 1001,
          "decorator": {
            "scale_type": "normal",
            "type": "morecustomworldgen:scale"
          }
        },
        {
          "salt": 1000,
          "decorator": {
            "ignored_biomes": [
              "minecraft:gravelly_mountains",
              "minecraft:modified_gravelly_mountains",
              "minecraft:mountain_edge",
              "minecraft:mountains",
              "minecraft:wooded_mountains"
            ],
            "entries": [
              {
                "bordering_biomes_and": [],
                "bordering_biomes_or": [
                  "minecraft:badlands_plateau",
                  "minecraft:wooded_badlands_plateau"
                ],
                "chance": 1,
                "biome": "minecraft:badlands",
                "center_and": true,
                "negative_center": false,
                "negative": true,
                "center_biomes": [
                  "minecraft:badlands_plateau",
                  "minecraft:wooded_badlands_plateau"
                ]
              },
              {
                "bordering_biomes_and": [],
                "bordering_biomes_or": [
                  "minecraft:giant_spruce_taiga",
                  "minecraft:giant_spruce_taiga_hills",
                  "minecraft:giant_tree_taiga",
                  "minecraft:giant_tree_taiga_hills",
                  "minecraft:snowy_taiga",
                  "minecraft:snowy_taiga_hills",
                  "minecraft:snowy_taiga_mountains",
                  "minecraft:taiga",
                  "minecraft:taiga_hills",
                  "minecraft:taiga_mountains"
                ],
                "chance": 1,
                "biome": "minecraft:taiga",
                "center_and": true,
                "negative_center": false,
                "negative": true,
                "center_biomes": "minecraft:giant_tree_taiga"
              },
              {
                "bordering_biomes_and": [],
                "bordering_biomes_or": "minecraft:snowy_tundra",
                "chance": 1,
                "biome": "minecraft:wooded_mountains",
                "center_and": true,
                "negative_center": false,
                "negative": false,
                "center_biomes": "minecraft:desert"
              },
              {
                "bordering_biomes_and": [],
                "bordering_biomes_or": [
                  "minecraft:desert",
                  "minecraft:snowy_taiga",
                  "minecraft:snowy_tundra"
                ],
                "chance": 1,
                "biome": "minecraft:plains",
                "center_and": true,
                "negative_center": false,
                "negative": false,
                "center_biomes": "minecraft:swamp"
              },
              {
                "bordering_biomes_and": [],
                "bordering_biomes_or": [
                  "minecraft:jungle",
                  "minecraft:bamboo_jungle"
                ],
                "chance": 1,
                "biome": "minecraft:jungle_edge",
                "center_and": true,
                "negative_center": false,
                "negative": false,
                "center_biomes": "minecraft:swamp"
              }
            ],
            "type": "morecustomworldgen:array_bordering_replace"
          }
        },
        {
          "salt": 1001,
          "decorator": {
            "biomes": [
              {
                "comparing_biome": "minecraft:plains",
                "chance": 57,
                "biome": "minecraft:sunflower_plains"
              }
            ],
            "type": "morecustomworldgen:simple_replace"
          }
        },
        {
          "salt": 1000,
          "decorator": {
            "scale_type": "normal",
            "type": "morecustomworldgen:scale"
          }
        },
        {
          "salt": 3,
          "decorator": {
            "type": "morecustomworldgen:increase_edge_curvature"
          }
        },
        {
          "salt": 1001,
          "decorator": {
            "scale_type": "normal",
            "type": "morecustomworldgen:scale"
          }
        },
        {
          "salt": 1000,
          "decorator": {
            "ignored_biomes": [],
            "entries": [
              {
                "bordering_biomes_and": [],
                "bordering_biomes_or": [
                  "minecraft:ocean",
                  "minecraft:warm_ocean",
                  "minecraft:lukewarm_ocean",
                  "minecraft:cold_ocean",
                  "minecraft:frozen_ocean"
                ],
                "chance": 1,
                "biome": "minecraft:mushroom_field_shore",
                "center_and": true,
                "negative_center": false,
                "negative": false,
                "center_biomes": "minecraft:mushroom_fields"
              },
              {
                "bordering_biomes_and": [],
                "bordering_biomes_or": [
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
                "chance": 1,
                "biome": "minecraft:jungle_edge",
                "center_and": true,
                "negative_center": false,
                "negative": true,
                "center_biomes": [
                  "minecraft:bamboo_jungle",
                  "minecraft:bamboo_jungle_hills",
                  "minecraft:jungle",
                  "minecraft:jungle_hills",
                  "minecraft:jungle_edge",
                  "minecraft:modified_jungle",
                  "minecraft:modified_jungle_edge"
                ]
              },
              {
                "bordering_biomes_and": [],
                "bordering_biomes_or": [
                  "minecraft:ocean",
                  "minecraft:warm_ocean",
                  "minecraft:lukewarm_ocean",
                  "minecraft:cold_ocean",
                  "minecraft:frozen_ocean"
                ],
                "chance": 1,
                "biome": "minecraft:stone_shore",
                "center_and": true,
                "negative_center": false,
                "negative": false,
                "center_biomes": [
                  "minecraft:mountains",
                  "minecraft:wooded_mountains",
                  "minecraft:mountain_edge"
                ]
              },
              {
                "bordering_biomes_and": [],
                "bordering_biomes_or": [
                  "minecraft:ocean",
                  "minecraft:warm_ocean",
                  "minecraft:lukewarm_ocean",
                  "minecraft:cold_ocean",
                  "minecraft:frozen_ocean"
                ],
                "chance": 1,
                "biome": "minecraft:snowy_beach",
                "center_and": true,
                "negative_center": false,
                "negative": false,
                "center_biomes": [
                  "minecraft:snowy_beach",
                  "minecraft:frozen_river",
                  "minecraft:snowy_tundra",
                  "minecraft:snowy_mountains",
                  "minecraft:ice_spikes",
                  "minecraft:snowy_taiga",
                  "minecraft:snowy_taiga_hills",
                  "minecraft:snowy_taiga_mountains",
                  "minecraft:frozen_ocean"
                ]
              },
              {
                "bordering_biomes_and": [
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
                "bordering_biomes_or": [
                  "minecraft:badlands",
                  "minecraft:wooded_badlands_plateau",
                  "minecraft:badlands_plateau",
                  "minecraft:eroded_badlands",
                  "minecraft:modified_wooded_badlands_plateau",
                  "minecraft:modified_badlands_plateau"
                ],
                "chance": 1,
                "biome": "minecraft:desert",
                "center_and": true,
                "negative_center": false,
                "negative": true,
                "center_biomes": [
                  "minecraft:badlands",
                  "minecraft:wooded_badlands_plateau"
                ]
              },
              {
                "bordering_biomes_and": [],
                "bordering_biomes_or": [
                  "minecraft:ocean",
                  "minecraft:warm_ocean",
                  "minecraft:lukewarm_ocean",
                  "minecraft:cold_ocean",
                  "minecraft:frozen_ocean"
                ],
                "chance": 1,
                "biome": "minecraft:beach",
                "center_and": true,
                "negative_center": true,
                "negative": false,
                "center_biomes": [
                  "minecraft:ocean",
                  "minecraft:warm_ocean",
                  "minecraft:lukewarm_ocean",
                  "minecraft:cold_ocean",
                  "minecraft:frozen_ocean",
                  "minecraft:deep_ocean",
                  "minecraft:deep_warm_ocean",
                  "minecraft:deep_lukewarm_ocean",
                  "minecraft:deep_cold_ocean",
                  "minecraft:deep_frozen_ocean",
                  "minecraft:river",
                  "minecraft:swamp"
                ]
              }
            ],
            "type": "morecustomworldgen:array_bordering_replace"
          }
        },
        {
          "salt": 1002,
          "decorator": {
            "scale_type": "normal",
            "type": "morecustomworldgen:scale"
          }
        },
        {
          "salt": 1003,
          "decorator": {
            "scale_type": "normal",
            "type": "morecustomworldgen:scale"
          }
        }
      ],
      "type": "morecustomworldgen:array_decorated"
    },
    "seed": 123456789,
    "settings": "minecraft:overworld",
    "type": "minecraft:noise"
  }
}
```