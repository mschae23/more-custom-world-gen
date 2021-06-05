# Biome decorators

Biome decorators are a new, experimental system similar to feature decorators, but for biomes. It should be even more
customizable than the `custom_layered` biome source.

## Decorators

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

## "Decorated" biome sources

### `decorated`
Works similarly to the `minecraft:decorated` feature.

- `biome_source`: The biome source this decorator is decorating.
- `decorator`: The decorator.
- `seed`: Should be the same as the world seed.
- `salt`: This is like a seed for this decorator (so that random values aren't the same for multiple ones).

### `array_decorated`
Similar to `decorated`, but can be given an array of decorators. This should be preferred.

## Vanilla example

Example dimension which should generate almost exactly like `vanilla_layered` (when this is complete, not now,
obviously).

```json
{
  "type": "minecraft:overworld",
  "generator": {
    "biome_source": {
      "biome_source": {
        "biome_source": {
          "biome_source": {
            "biome_source": {
              "biome_source": {
                "biome_source": {
                  "biome_source": {
                    "biome": "minecraft:the_void",
                    "type": "minecraft:fixed"
                  },
                  "decorator": {
                    "biomes": [
                      {
                        "chance": 10,
                        "biome": "minecraft:plains"
                      }
                    ],
                    "default_biome": "minecraft:ocean",
                    "type": "morecustomworldgen:weighted_init"
                  },
                  "seed": 8581807989614358238,
                  "salt": 1,
                  "type": "morecustomworldgen:decorated"
                },
                "decorator": {
                  "scale_type": "fuzzy",
                  "type": "morecustomworldgen:scale"
                },
                "seed": 8581807989614358238,
                "salt": 2000,
                "type": "morecustomworldgen:decorated"
              },
              "decorator": {
                "type": "morecustomworldgen:increase_edge_curvature"
              },
              "seed": 8581807989614358238,
              "salt": 1,
              "type": "morecustomworldgen:decorated"
            },
            "decorator": {
              "scale_type": "normal",
              "type": "morecustomworldgen:scale"
            },
            "seed": 8581807989614358238,
            "salt": 2001,
            "type": "morecustomworldgen:decorated"
          },
          "decorator": {
            "type": "morecustomworldgen:increase_edge_curvature"
          },
          "seed": 8581807989614358238,
          "salt": 2,
          "type": "morecustomworldgen:decorated"
        },
        "decorator": {
          "type": "morecustomworldgen:increase_edge_curvature"
        },
        "seed": 8581807989614358238,
        "salt": 50,
        "type": "morecustomworldgen:decorated"
      },
      "decorator": {
        "type": "morecustomworldgen:increase_edge_curvature"
      },
      "seed": 8581807989614358238,
      "salt": 70,
      "type": "morecustomworldgen:decorated"
    },
    "seed": 8581807989614358238,
    "settings": "minecraft:overworld",
    "type": "minecraft:noise"
  }
}
```