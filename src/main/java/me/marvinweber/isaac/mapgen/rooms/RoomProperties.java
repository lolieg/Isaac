package me.marvinweber.isaac.mapgen.rooms;

import net.minecraft.block.Block;

public class RoomProperties {

    public int defaultHeight;
    public Block defaultFloor;
    public Block defaultWall;

    public RoomProperties(int defaultHeight, Block defaultFloor, Block defaultWall) {
        this.defaultHeight = defaultHeight;
        this.defaultFloor = defaultFloor;
        this.defaultWall = defaultWall;
    }
}
