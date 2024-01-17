package me.marvinweber.isaac.mapgen.rooms;

import me.marvinweber.isaac.mapgen.Room;
import me.marvinweber.isaac.mapgen.rooms.layouts.RoomLayout;
import me.marvinweber.isaac.mapgen.rooms.layouts.SpecialRooms;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.stream.Stream;

public class SpecialRoom extends Room {
    public SpecialRooms type;

    public SpecialRoom(int gridX, int gridY, RoomProperties properties) {
        super(gridX, gridY, properties);
    }

    @Override
    public void setRandomRoomLayout(ArrayList<RoomLayout> roomLayouts, Random random) {
        Stream<RoomLayout> filtered = roomLayouts.stream().filter(roomLayout -> {
            if (!Objects.equals(roomLayout.shape, "1")) return false;
            if (this.type == null || !Objects.equals(roomLayout.type, this.type.type)) return false;
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
