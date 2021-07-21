package de.martenschaefer.morecustomworldgen.test;

import net.minecraft.util.math.Spline;

public final class TestSplines {
    public static final Spline<NoisePoint> OFFSET_SPLINE = createOffsetSpline(createRiverOffsetSpline(createContinentalOffsetSpline(createHillsOffsetSpline())));
    public static final Spline<NoisePoint> FACTOR_SPLINE = createFactorSpline(createRiverFactorSpline(createContinentalFactorSpline(createHillsFactorSpline())));

    private TestSplines() {
    }

    public static Spline<NoisePoint> createRiverOffsetSpline(Spline<NoisePoint> landSpline) {
        return Spline.builder(NoisePoint::river).setName("offset_river")
            .add(-1f, landSpline, 0f).addFixedValue(-0.2f, point -> landSpline.apply(point) * 0.5f, 0f)
            .add(0f, -0.3f, 0f)
            .addFixedValue(0.2f, point -> landSpline.apply(point) * 0.5f, 0f).add(1f, landSpline, 0f)
            .build().getThis();
    }

    public static Spline<NoisePoint> createContinentalOffsetSpline(Spline<NoisePoint> landSpline) {
        return Spline.builder(NoisePoint::continents).setName("offset_continents").add(-1f, 0f, 0f).add(0f, 0f, 0f)
            .addFixedValue(0.3f, point -> landSpline.apply(point) * 0.6f, 0f).add(1f, landSpline, 0f)
            .build().getThis();
    }

    public static Spline<NoisePoint> createHillsOffsetSpline() {
        return Spline.builder(NoisePoint::hilliness).setName("offset_hills")
            .add(-1f, 0.01f, 0f).add(-0.5f, 0.05f, 0f).add(0f, 0.1f, 0f)
            .add(0.2f, 0.15f, 0f).add(0.5f, 0.2f, 0).add(0.7f, 0.25f, 0f).add(1f, 0.45f, 0f)
            .build().getThis();
    }

    public static Spline<NoisePoint> createOffsetSpline(Spline<NoisePoint> landSpline) {
        return Spline.builder(NoisePoint::continents).setName("offset")
            .add(-1.1f, 0.15f, 0f).add(-0.85f, -0.1f, 0f).add(-0.6f, -0.25f, 0f).add(-0.3f, -0.12f, 0f).add(-0.06f, -0.05f, 0f)
            .add(0f, 0.01f, 0f).add(0.1f, 0.01f, 0f).add(0.3f, 0.1f, 0f).add(1f, landSpline, 0f)
            .build().getThis();
    }

    public static Spline<NoisePoint> createHillsFactorSpline() {
        return Spline.builder(NoisePoint::hilliness).setName("factor_hills")
            .add(-1f, 483f, 0f).add(0f, 505f, 0f)
            .add(0.1f, 505f, 0f).add(0.3f, 583f, 0f).add(0.5f, 600f, 0f).add(0.75f, 625f, 0f).add(1f, 700f, 0f)
            .build().getThis();
    }

    public static Spline<NoisePoint> createContinentalFactorSpline(Spline<NoisePoint> landSpline) {
        return Spline.builder(NoisePoint::continents).setName("factor_continents")
            .add(0f, 505f, 0f).add(0.1f, 505f, 0f).add(0.3f, 783f, 0f).add(0.5f, 600f, 0f).add(0.75f, landSpline, 0f).add(1f, landSpline, 0f)
            .build().getThis();
    }

    public static Spline<NoisePoint> createRiverFactorSpline(Spline<NoisePoint> landSpline) {
        return Spline.builder(NoisePoint::river).setName("factor_river")
            .add(-1f, landSpline, 0f).add(-0.2f, 505f, 0f).add(0f, 379f, 0f).add(0.2f, 505f, 0f).add(1f, landSpline, 0f)
            .build().getThis();
    }

    public static Spline<NoisePoint> createFactorSpline(Spline<NoisePoint> landSpline) {
        return Spline.builder(NoisePoint::continents).setName("factor")
            .add(0f, 505f, 0f).add(1f, landSpline, 0f)
            .build().getThis();
    }

    public static NoisePoint createNoisePoint(float continents, float hilliness, float river) {
        return new NoisePoint(continents, hilliness, river);
    }

    public static float getOffset(NoisePoint point) {
        return OFFSET_SPLINE.apply(point);
    }

    public static float getFactor(NoisePoint point) {
        return FACTOR_SPLINE.apply(point);
    }

    public static record NoisePoint(float continents, float hilliness, float river) {
    }
}
