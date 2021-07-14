package de.martenschaefer.morecustomworldgen.util;

public interface NorthWestCoordinateTransformer extends CoordinateTransformer {
    @Override
    default int transformX(int x) {
        return x - 1;
    }

    @Override
    default int transformZ(int z) {
        return z - 1;
    }
}
