package me.marvinweber.isaac.mapgen.rooms;

import me.marvinweber.isaac.mapgen.rooms.layouts.SpecialRooms;
import net.minecraft.block.Blocks;

public class SecretRoom extends SpecialRoom {

    public SecretRoom(int gridX, int gridY) {
        super(gridX, gridY, new RoomProperties(10, Blocks.STONE, Blocks.STONE_BRICKS));
        this.type = SpecialRooms.SECRET;
        this.itemPoolName = "secret";
    }

}
