package me.marvinweber.isaac.mapgen.rooms;

import me.marvinweber.isaac.mapgen.rooms.layouts.SpecialRooms;
import net.minecraft.block.Blocks;

public class ShopRoom extends SpecialRoom {

    public ShopRoom(int gridX, int gridY) {
        super(gridX, gridY, new RoomProperties(10, Blocks.OAK_WOOD, Blocks.OAK_PLANKS));
        this.type = SpecialRooms.SHOP;
        this.itemPoolName = "shop";
    }

}
