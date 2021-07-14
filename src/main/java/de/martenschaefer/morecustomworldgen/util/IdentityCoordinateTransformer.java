package de.martenschaefer.morecustomworldgen.util;

public interface IdentityCoordinateTransformer extends CoordinateTransformer {
    @Override
    default int transformX(int x) {
        return x;
    }

    @Override
    default int transformZ(int z) {
        return z;
    }
}
