package me.marvinweber.isaac.mapgen;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Door {
    public Room room;
    public ArrayList<Vec3d> coords;
    public Room.Face face;
    public DoorState doorState;
    public BlockState defaultDoor = Blocks.BEDROCK.getDefaultState();


    public Door(Room room, ArrayList<Integer> faceCoords, int unchangedCord, Room.Face face, DoorState doorState) {
        this.room = room;
        this.coords = new ArrayList<>();
        this.face = face;
        this.doorState = doorState;

        setCoords(faceCoords, unchangedCord);
    }

    public void setCoords(ArrayList<Integer> faceCoords, int unchangedCord) {
        List<Integer> doorCoords;
        if (this.face == Room.Face.NORTH || this.face == Room.Face.SOUTH) {
            doorCoords = faceCoords.subList(13, 15);
        } else {
            doorCoords = faceCoords.subList(7, 9);
        }

        for (int i :
                faceCoords) {
            for (int y = room.properties.defaultHeight + 1; y < room.properties.defaultHeight + 5; y++) {
                if (y != room.properties.defaultHeight + 4 && doorCoords.contains(i)) {
                    if (face == Room.Face.NORTH || face == Room.Face.SOUTH)
                        coords.add(new Vec3d(i, y, unchangedCord));
                    if (face == Room.Face.EAST || face == Room.Face.WEST)
                        coords.add(new Vec3d(unchangedCord, y, i));
                }
            }
        }

    }

    public void modify(World world, DoorState doorState) {
        for (Vec3d pos :
                this.coords) {
            if (doorState == DoorState.AIR) {
                world.setBlockState(new BlockPos(pos), Blocks.AIR.getDefaultState());
                continue;
            }
            if (doorState == DoorState.FOGGED) {
                world.setBlockState(new BlockPos(pos), Blocks.BLACK_WOOL.getDefaultState());
                continue;
            }
            world.setBlockState(new BlockPos(pos), this.defaultDoor);
        }
        this.doorState = doorState;
    }

    public enum DoorState {
        AIR(true),
        FOGGED(false),
        CLOSED(false),
        LOCKED(false);

        public final boolean open;

        DoorState(boolean open) {
            this.open = open;
        }
    }
}
