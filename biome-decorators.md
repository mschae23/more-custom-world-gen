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

## "Replacing" biome decorators

### `bordering_replace`

Replaces the current biome with another one if it is bordering other specified biomes.

- `comparing_biome`: Registry ID of biome that the bordering biomes are compared against.
- `and`: Boolean value. If all the bordering biomes and the current biome have to match the comparing biome, or if it is
  enough if one of them matches it.
- `negative`: Negates the `comparing_biome`, meaning the bordering biomes have to *not* match it.
- `chance`: Int or chance object. The biome is only going to be replaced with a certain chance.
- `biome`: The biome that is going to be used instead of the current biome.

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
    - The biome decorator.

## Vanilla example

Example dimension which should generate almost exactly like `vanilla_layered` (when this is complete, not now,
obviously).

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