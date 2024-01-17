package me.marvinweber.isaac.mapgen;

import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.entities.BaseEntity;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.items.itempools.ItemPool;
import me.marvinweber.isaac.mapgen.rooms.RoomProperties;
import me.marvinweber.isaac.mapgen.rooms.Spawner;
import me.marvinweber.isaac.mapgen.rooms.layouts.Entities;
import me.marvinweber.isaac.mapgen.rooms.layouts.RoomLayout;
import me.marvinweber.isaac.mapgen.rooms.layouts.SpecialRooms;
import me.marvinweber.isaac.registry.common.EntityRegistry;
import me.marvinweber.isaac.stats.HealthManager;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Room {
    public List<Integer> X;
    public List<Integer> Z;
    public List<Face> doorFaces;
    public ArrayList<Door> doors;
    public int gridX;
    public int gridY;
    public RoomProperties properties;
    public RoomState roomState;
    public RoomState roomStateBefore;
    public boolean entered;
    public boolean visible;

    public ArrayList<BaseEntity> enemies;
    public RoomLayout roomLayout;
    public int X2;
    public int X1;
    public int Z1;
    public int Z2;
    public Spawner spawner;

    public String itemPoolName;
    public SpecialRooms type;

    public Room(int gridX, int gridY, RoomProperties properties) {

        this.doorFaces = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.gridX = gridX;
        this.gridY = gridY;
        setCoords();

        this.properties = properties;

        this.roomState = RoomState.IDLE;
        this.entered = false;
        this.visible = false;

        this.enemies = new ArrayList<>();
        this.roomLayout = null;
        this.itemPoolName = "";
    }

    public Room(int gridX, int gridY, boolean entered, boolean visible, SpecialRooms type) {
        this(gridX, gridY, new RoomProperties(10, Blocks.DARK_OAK_WOOD, Blocks.DARK_OAK_PLANKS));
        this.entered = entered;
        this.visible = visible;
        this.type = type;
    }

    public static List<Integer> getRange(int first, int second) {
        List<Integer> array = new ArrayList<>();
        if (first < second) {
            for (int i = first; i < second; i++) {
                array.add(i);
            }
            array.add(second);
        } else {
            for (int i = second; i < first; i++) {
                array.add(i);
            }
            array.add(first);
        }
        return array;
    }

    private void setCoords() {
        this.X1 = gridX * 28;
        this.X2 = gridX * 28 + 27;
        this.Z1 = gridY * 16;
        this.Z2 = gridY * 16 + 15;
        this.X = getRange(X1, X2);
        this.Z = getRange(Z1, Z2);
    }

    public void generate(World world) {

        ArrayList<Integer> north = new ArrayList<>();
        ArrayList<Integer> east = new ArrayList<>();
        ArrayList<Integer> south = new ArrayList<>();
        ArrayList<Integer> west = new ArrayList<>();

        Pair<Integer, Integer> center = this.getACenterBlock();
        for (int x : this.X) {
            for (int z : this.Z) {
                // Floor
                world.setBlockState(new BlockPos(x, properties.defaultHeight, z), properties.defaultFloor.getDefaultState());

                // Walls
                if (x == this.X.get(0) || z == this.Z.get(0) || x == this.X.get(this.X.size() - 1) || z == this.Z.get(this.Z.size() - 1)) {
                    for (int i = properties.defaultHeight + 1; i < properties.defaultHeight + 5; i++) {
                        world.setBlockState(new BlockPos(x, i, z), properties.defaultWall.getDefaultState());
                    }

                    // Facing of walls and add door if requested
                    if (z < center.getRight() && z == this.Z.get(0) && doorFaces.contains(Face.NORTH)) {
                        north.add(x);
                    }
                    if (x > center.getLeft() && x == this.X.get(this.X.size() - 1) && this.doorFaces.contains(Face.EAST)) {
                        east.add(z);
                    }
                    if (z > center.getRight() && z == this.Z.get(this.Z.size() - 1) && this.doorFaces.contains(Face.SOUTH)) {
                        south.add(x);
                    }
                    if (x < center.getLeft() && x == this.X.get(0) && this.doorFaces.contains(Face.WEST)) {
                        west.add(z);
                    }
                }
            }
        }
        // doors
        if (north.size() > 0)
            this.doors.add(new Door(this, north, this.Z.get(0), Face.NORTH, Door.DoorState.FOGGED));
        if (east.size() > 0)
            this.doors.add(new Door(this, east, this.X.get(this.X.size() - 1), Face.EAST, Door.DoorState.FOGGED));
        if (south.size() > 0)
            this.doors.add(new Door(this, south, this.Z.get(this.Z.size() - 1), Face.SOUTH, Door.DoorState.FOGGED));
        if (west.size() > 0)
            this.doors.add(new Door(this, west, this.X.get(0), Face.WEST, Door.DoorState.FOGGED));

        changeAllDoors(world, gridX == 5 && gridY == 4 ? Door.DoorState.AIR : Door.DoorState.FOGGED);

    }

    public void createSpawner(World world) {
        this.spawner = new Spawner(this, world);
    }

    public boolean changeDoor(World world, Face face, Door.DoorState doorState) {
        Optional<Door> door = this.doors.stream().filter(room -> room.face == face).findFirst();
        if (door.isPresent()) {
            door.get().modify(world, doorState);
            return true;
        }
        return false;
    }

    public void changeAllDoors(World world, Door.DoorState doorState) {
        for (Door door :
                doors) {
            door.modify(world, doorState);
        }
    }

    public void degenerate(World world) {
        for (int x : this.X) {
            for (int z : this.Z) {
                // Floor
                world.setBlockState(new BlockPos(x, properties.defaultHeight, z), Blocks.AIR.getDefaultState());
                for (int i = properties.defaultHeight + 1; i < properties.defaultHeight + 5; i++) {
                    world.setBlockState(new BlockPos(x, i, z), Blocks.AIR.getDefaultState());
                }
            }
        }
    }

    public void update(Game game) {
        if (enemies.isEmpty()) {
            roomState = RoomState.IDLE;
        } else {
            roomState = RoomState.ACTION;
        }
        if (this.roomState == Room.RoomState.ACTION) {
            this.changeAllDoors(game.world, Door.DoorState.CLOSED);
        }
        if (this.roomState == Room.RoomState.IDLE && this.entered) {
            this.changeAllDoors(game.world, Door.DoorState.AIR);
        }

        for (Door door :
                doors) {
            for (Player player :
                    game.players) {
                if (door.coords.contains(new Vec3i((int) Math.round(player.playerEntity.getX()), (int) Math.round(player.playerEntity.getY()), (int) Math.round(player.playerEntity.getZ())))) {
                    game.onInDoorSpace(this, door, player);
                }

            }
        }

    }

    public Pair<Integer, Integer> getACenterBlock() {
        return new Pair<>(this.X.get(this.X.size() / 2), this.Z.get(this.Z.size() / 2));
    }

    public void setRandomRoomLayout(ArrayList<RoomLayout> roomLayouts, Random random) {

    }

    public static void serialize(PacketByteBuf packetByteBuf, Room room) {
        packetByteBuf.writeInt(room.gridX);
        packetByteBuf.writeInt(room.gridY);
        packetByteBuf.writeBoolean(room.entered);
        packetByteBuf.writeBoolean(room.visible);
        if (room.type != null) {
            packetByteBuf.writeVarInt(room.type.ordinal());
        } {
            packetByteBuf.writeVarInt(-1);
        }
    }

    public static Room deserialize(PacketByteBuf packetByteBuf) {
        int gridX = packetByteBuf.readInt();
        int gridY = packetByteBuf.readInt();
        boolean entered = packetByteBuf.readBoolean();
        boolean visible = packetByteBuf.readBoolean();

        int typeOrdinal = packetByteBuf.readVarInt();
        SpecialRooms type = null;
        if (typeOrdinal >= 0) {
            type = SpecialRooms.values()[typeOrdinal];
        }


        return new Room(gridX, gridY, entered, visible, type);
    }

    /**
     * Gets item pool.
     *
     * @return the item pool. Treasure Room Item Pool if no pool is found.
     */
    public ItemPool getItemPool() {
        for (Map.Entry<String, ItemPool> entry :
                Isaac.game.itemPools.entrySet()) {
            if (entry.getKey().equals(this.itemPoolName)) return entry.getValue();
        }
        return Isaac.game.itemPools.get("treasure");
    }


    public enum Face {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }


    public enum Shape {
        DOT,
        I,
        L,
        SQUARE
    }

    public enum RoomState {
        IDLE,
        ACTION
    }
}
