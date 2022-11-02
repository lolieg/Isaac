package me.marvinweber.isaac.mapgen.rooms;

import me.marvinweber.isaac.mapgen.rooms.layouts.SpecialRooms;
import net.minecraft.block.Blocks;

public class TreasureRoom extends SpecialRoom {
    public TreasureRoom(int gridX, int gridY) {
        super(gridX, gridY, new RoomProperties(10, Blocks.GOLD_BLOCK, Blocks.YELLOW_WOOL));
        this.type = SpecialRooms.TREASURE;
        this.itemPoolName = "treasure";
    }
}
