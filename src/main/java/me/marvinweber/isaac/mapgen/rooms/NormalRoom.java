package me.marvinweber.isaac.mapgen.rooms;

import me.marvinweber.isaac.mapgen.Room;
import me.marvinweber.isaac.mapgen.rooms.layouts.RoomLayout;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

public class NormalRoom extends Room {

    public NormalRoom(int gridX, int gridY) {
        super(gridX, gridY, new RoomProperties(10, Blocks.DARK_OAK_WOOD, Blocks.DARK_OAK_PLANKS));
    }


    public void setRandomRoomLayout(ArrayList<RoomLayout> roomLayouts, Random random) {
        Stream<RoomLayout> filtered = roomLayouts.stream().filter(roomLayout -> {
            if (!Objects.equals(roomLayout.shape, "1")) return false;
            for (Face door :
                    this.doorFaces) {
                ArrayList<Face> doorFaces = new ArrayList<>();
                roomLayout.doors.forEach(door1 -> doorFaces.add(door1.face));
                if (!doorFaces.contains(door) || !roomLayout.doors.stream().filter(door1 -> door1.face == door).findFirst().orElse(new RoomLayout.Door(false, Face.NORTH)).exists)
                    return false;
            }
            return true;
        });
        List<RoomLayout> filteredList = filtered.toList();

        this.roomLayout = RoomLayout.pickRandomLayoutWeighted(filteredList, random);
    }
}
