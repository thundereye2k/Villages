package com.stefthedev.villages.utilities.villages;

public class VillageClaim {

    private final int x, z;

    public VillageClaim(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return x + ":" + z;
    }
}
