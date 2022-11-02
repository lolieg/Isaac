package me.marvinweber.isaac.blocks.multiblock;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Vec3i;

public enum MultiblockPart implements StringIdentifiable {
    BOTTOM("bottom", new Vec3i(0, 0, 0)),
    TOP("top", new Vec3i(0, 1, 0)),
    SOUTH("south", new Vec3i(0, 0, 1)),
    EAST("east", new Vec3i(1, 0, 0)),

    TOP_SOUTH("top_south", new Vec3i(0, 1, 1)),
    TOP_EAST("top_east", new Vec3i(1, 1, 0)),
    TOP_SOUTH_EAST("top_south_east", new Vec3i(1, 1, 1)),
    SOUTH_EAST("south_east", new Vec3i(1, 0, 1));


    public Vec3i getPos() {
        return pos;
    }

    private final String name;
    private final Vec3i pos;

    MultiblockPart(String name, Vec3i pos) {
        this.name = name;
        this.pos = pos;

    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
