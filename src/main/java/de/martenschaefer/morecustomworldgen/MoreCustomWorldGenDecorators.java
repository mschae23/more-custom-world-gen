package de.martenschaefer.morecustomworldgen;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.CountNoiseBiasedDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import de.martenschaefer.morecustomworldgen.decorator.CountNoiseBiased3DDecorator;
import de.martenschaefer.morecustomworldgen.decorator.OffsetDecoratorConfig;
import de.martenschaefer.morecustomworldgen.decorator.OffsetVerticallyDecorator;
import de.martenschaefer.morecustomworldgen.decorator.RangeMaskDecorator;
import de.martenschaefer.morecustomworldgen.decorator.SpreadVerticallyDecorator;
import de.martenschaefer.morecustomworldgen.decorator.SpreadVerticallyDecoratorConfig;

@SuppressWarnings("unused")
public class MoreCustomWorldGenDecorators {
    public static final Decorator<OffsetDecoratorConfig> OFFSET_VERTICALLY = register("offset_vertically", new OffsetVerticallyDecorator(OffsetDecoratorConfig.CODEC));
    public static final Decorator<RangeDecoratorConfig> RANGE_MASK = register("range_mask", new RangeMaskDecorator(RangeDecoratorConfig.CODEC));
    public static final Decorator<CountNoiseBiasedDecoratorConfig> COUNT_NOISE_BIASED_3D = register("count_noise_biased_3d", new CountNoiseBiased3DDecorator(CountNoiseBiasedDecoratorConfig.CODEC));
    public static final Decorator<SpreadVerticallyDecoratorConfig> SPREAD_VERTICALLY = register("spread_vertically", new SpreadVerticallyDecorator(SpreadVerticallyDecoratorConfig.CODEC));

    private static <DC extends DecoratorConfig, D extends Decorator<DC>> D register(String name, D decorator) {
        return Registry.register(Registry.DECORATOR, new Identifier(MoreCustomWorldGenMod.MODID, name), decorator);
    }

    public static void init() {
    }
}
