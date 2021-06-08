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

- `center_biomes`: Registry ID (or ID list) of the biome that the center biome is compared with.
- `comparing_biomes`: Registry ID (or ID list) of the biome that the bordering biomes are compared with.
- `and`: Boolean value. If all the bordering biomes have to match the comparing biome, or if it is enough if one of them
  matches it. (Defaults to `false`)
- `center_and`: If the center *and* the bordering biomes have to match, or the center *or* the bordering biomes.
  (Defaults to `true`)
- `negative`: Negates the `comparing_biome`, meaning the bordering biomes have to *not* match it. (Defaults to `false`)
- `chance`: Int or chance object. The biome is only going to be replaced with a certain chance.
- `biome`: The biome that is going to be used instead of the current biome.

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
            "center_biomes": "minecraft:ocean",
            "comparing_biomes": "minecraft:ocean",
            "chance": 2,
            "biome": "minecraft:plains",
            "and": true,
            "center_and": true,
            "negative": false,
            "type": "morecustomworldgen:bordering_replace"
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
            "center_biomes": "morecustomworldgen:dry",
            "comparing_biomes": [
              "morecustomworldgen:cool",
              "morecustomworldgen:snowy"
            ],
            "chance": 1,
            "biome": "morecustomworldgen:temperate",
            "and": false,
            "center_and": true,
            "negative": false,
            "type": "morecustomworldgen:bordering_replace"
          }
        },
        {
          "salt": 2,
          "decorator": {
            "center_biomes": "morecustomworldgen:snowy",
            "comparing_biomes": [
              "morecustomworldgen:dry",
              "morecustomworldgen:temperate"
            ],
            "chance": 1,
            "biome": "morecustomworldgen:cool",
            "and": false,
            "center_and": true,
            "negative": false,
            "type": "morecustomworldgen:bordering_replace"
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