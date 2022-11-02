package me.marvinweber.isaac.mapgen.rooms;

import me.marvinweber.isaac.mapgen.rooms.layouts.SpecialRooms;
import net.minecraft.block.Blocks;

public class BossRoom extends SpecialRoom {

    public BossRoom(int gridX, int gridY) {
        super(gridX, gridY, new RoomProperties(10, Blocks.NETHERRACK, Blocks.NETHER_BRICKS));
        this.type = SpecialRooms.BOSS;
        this.itemPoolName = "boss";
    }

}
