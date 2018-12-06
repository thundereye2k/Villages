package com.stefthedev.villages.villages;

public class VillageClaim {

    private final int x, z;

    public VillageClaim(int x, int z) {
        this.x = x;
        this.z = z;
    }

    int getX() {
        return x;
    }

    int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return x + ":" + z;
    }
}
