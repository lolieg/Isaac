package me.marvinweber.isaac.mapgen;

import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.mapgen.rooms.*;
import me.marvinweber.isaac.mapgen.rooms.layouts.RoomLayout;
import me.marvinweber.isaac.mapgen.rooms.layouts.RoomsParser;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static me.marvinweber.isaac.Utility.getRandomNumber;
import static me.marvinweber.isaac.Utility.splitNumber;

public class Level {

    public Random random;

    public int maxrooms = 70;
    public int minrooms;

    public List<Integer> floorplan;
    public int floorplanCount;
    public List<Integer> cellQueue;
    public List<Integer> endrooms;
    public ArrayList<Room> rooms;

    public Room startingRoom;

    public ArrayList<RoomLayout> roomLayouts;
    public ArrayList<RoomLayout> specialRoomLayouts;

    public Level(Random random, int numberOfRooms) {
        this.random = random;
        this.minrooms = numberOfRooms;
        this.rooms = new ArrayList<>();
        setBaseVars();
    }

    public void setBaseVars() {
        floorplan = new ArrayList<>();
        for (var i = 0; i < 100; i++) floorplan.add(i, 0);
        floorplanCount = 0;
        cellQueue = new ArrayList<>();
        endrooms = new ArrayList<>();

        this.roomLayouts = RoomsParser.parse(RoomsParser.STB.BASEMENT_ROOMS);
        this.specialRoomLayouts = RoomsParser.parse(RoomsParser.STB.SPECIAL_ROOMS);

        visit(45);
    }

    public void generate(World world) {
        // Algorithm for Room generation
        while (cellQueue.size() > 0) {
            int i = cellQueue.get(0);
            cellQueue.remove(0);
            int x = i % 10;
            boolean created = false;
            if (x > 1) created = created | visit(i - 1);
            if (x < 9) created = created | visit(i + 1);
            if (i > 20) created = created | visit(i - 10);
            if (i < 70) created = created | visit(i + 10);
            if (!created) {
                endrooms.add(i);
            }

        }
        if (floorplanCount < minrooms) {
            this.setBaseVars();
            this.generate(world);
            return;
        }

        if (endrooms.size() < 5) {
            this.setBaseVars();
            this.generate(world);
            return;
        }


        // Special Rooms
        int bossRoomIndex = endrooms.get(endrooms.size() - 1);
        endrooms.remove(endrooms.size() - 1);
        Pair<Integer, Integer> bossRoomGrid = splitNumber(bossRoomIndex);

        BossRoom bossRoom = new BossRoom(bossRoomGrid.getLeft(), bossRoomGrid.getRight());
        bossRoom.doorFaces = getDoorNeededFaces(bossRoomIndex);
        bossRoom.setRandomRoomLayout(this.specialRoomLayouts, this.random);
        this.specialRoomLayouts.remove(bossRoom.roomLayout);
        bossRoom.createSpawner(world);
        rooms.add(bossRoom);


        int treasureRoomIndex = poprandomendroom();
        Pair<Integer, Integer> treasureRoomGrid = splitNumber(treasureRoomIndex);

        TreasureRoom treasureRoom = new TreasureRoom(treasureRoomGrid.getLeft(), treasureRoomGrid.getRight());
        treasureRoom.doorFaces = getDoorNeededFaces(treasureRoomIndex);
        treasureRoom.setRandomRoomLayout(this.specialRoomLayouts, this.random);
        this.specialRoomLayouts.remove(treasureRoom.roomLayout);
        treasureRoom.createSpawner(world);
        rooms.add(treasureRoom);

        int shopRoomIndex = poprandomendroom();
        Pair<Integer, Integer> shopRoomGrid = splitNumber(shopRoomIndex);

        ShopRoom shopRoom = new ShopRoom(shopRoomGrid.getLeft(), shopRoomGrid.getRight());
        shopRoom.doorFaces = getDoorNeededFaces(shopRoomIndex);
        shopRoom.setRandomRoomLayout(this.specialRoomLayouts, this.random);
        this.specialRoomLayouts.remove(shopRoom.roomLayout);
        shopRoom.createSpawner(world);
        rooms.add(shopRoom);

        int secretIndex = picksecretroom(bossRoomIndex);
        Pair<Integer, Integer> secretGrid = splitNumber(secretIndex);

        SecretRoom secretRoom = new SecretRoom(secretGrid.getLeft(), secretGrid.getRight());
        secretRoom.doorFaces = getDoorNeededFaces(secretIndex);
        secretRoom.setRandomRoomLayout(this.specialRoomLayouts, this.random);
        this.specialRoomLayouts.remove(secretRoom.roomLayout);
        secretRoom.createSpawner(world);
        rooms.add(secretRoom);

        // Normal Rooms
        for (int i = 0; i < floorplan.size(); i++) {
            int cell = floorplan.get(i);
            if (cell == 1) {
                Pair<Integer, Integer> grid = splitNumber(i);
                if (i != bossRoomIndex && i != treasureRoomIndex && i != shopRoomIndex && i != secretIndex) {
                    Room room;
                    if (getRandomNumber(0, 10, random) > 1 && i != 45) {
                        room = new FightingRoom(grid.getLeft(), grid.getRight());
                    } else {
                        room = new NormalRoom(grid.getLeft(), grid.getRight());
                    }
                    if (i == 45)
                        startingRoom = room;
                    room.doorFaces = getDoorNeededFaces(i);
                    if (i != 45) {
                        room.setRandomRoomLayout(this.roomLayouts, this.random);
                        this.roomLayouts.remove(room.roomLayout);
                        if (!room.roomLayout.isEmpty()) {
                            room.createSpawner(world);
                        }
                    }
                    rooms.add(room);
                }
            }
        }

        // generate the rooms in the world
        rooms.forEach((room -> {
            room.generate(world);
        }));
    }

    @NotNull
    private ArrayList<Room.Face> getDoorNeededFaces(int i) {
        ArrayList<Room.Face> doors = new ArrayList<>();
        if (floorplan.get(i + 10) == 1) {
            doors.add(Room.Face.SOUTH);
        }
        if (floorplan.get(i - 10) == 1) {
            doors.add(Room.Face.NORTH);
        }
        if (floorplan.get(i + 1) == 1) {
            doors.add(Room.Face.EAST);
        }
        if (floorplan.get(i - 1) == 1) {
            doors.add(Room.Face.WEST);
        }
        return doors;
    }

    public int picksecretroom(int bossl) {
        for (var e = 0; e < 900; e++) {
            var x = Math.floor(getRandomNumber(0, 10, this.random) / 10f * 9) + 1;
            var y = Math.floor(getRandomNumber(0, 10, this.random) / 10f * 8) + 2;
            int i = (int) (y * 10 + x);
            if (floorplan.get(i) == 1)
                continue;

            if (bossl == i - 1 || bossl == i + 1 || bossl == i + 10 || bossl == i - 10)
                continue;

            if (i > 89)
                continue;

            if (ncount(i) >= 3)
                return i;

            if (e > 300 && ncount(i) >= 2)
                return i;

            if (e > 600 && ncount(i) >= 1)
                return i;
        }
        return 0;
    }

    public int ncount(int i) {
        return floorplan.get(i - 10) + floorplan.get(i - 1) + floorplan.get(i + 1) + floorplan.get(i + 10);
    }

    public boolean visit(int i) {
        if (floorplan.get(i) != 0)
            return false;

        int neighbours = ncount(i);

        if (neighbours > 1)
            return false;

        if (floorplanCount >= maxrooms)
            return false;

        if (getRandomNumber(0, 10, random) < 5 && i != 45)
            return false;

        cellQueue.add(i);
        floorplan.set(i, 1);
        floorplanCount++;

        return true;
    }

    public int poprandomendroom() {
        int index = (int) Math.floor(getRandomNumber(0, 10, random) / 10f * endrooms.size());
        int i = endrooms.get(index);
        endrooms.remove(index);
        return i;
    }

    public void degenerate(World world) {
        rooms.forEach((room -> {
            room.degenerate(world);
        }));
        this.rooms = new ArrayList<>();
        setBaseVars();
    }

    public void update(Game game) {
        for (Room room :
                this.rooms) {
            room.update(game);
            for (Player player :
                    game.players) {
                if (room.X.contains((int) player.playerEntity.getX()) && room.Z.contains((int) player.playerEntity.getZ()) && room != game.currentRoom) {
                    game.onRoomChanged(room, player);
                }

            }
        }

    }

    public ArrayList<Room> getAdjacentRooms(Room room) {
        ArrayList<Room> adjacentRooms = new ArrayList<>();
        for (Room.Face face :
                room.doorFaces) {
            Room adRoom = null;
            if (face == Room.Face.NORTH)
                adRoom = getRoom(room.gridX, room.gridY + 1);
            if (face == Room.Face.EAST)
                adRoom = getRoom(room.gridX + 1, room.gridY);
            if (face == Room.Face.SOUTH)
                adRoom = getRoom(room.gridX, room.gridY - 1);
            if (face == Room.Face.WEST)
                adRoom = getRoom(room.gridX - 1, room.gridY);

            if (adRoom != null)
                adjacentRooms.add(adRoom);
        }
        return adjacentRooms;
    }

    // TODO MAKE GOOD
    public Door getAdjacentDoor(Door door) {
        if (door.face == Room.Face.NORTH) {
            Room room = getRoom(door.room.gridX, door.room.gridY - 1);
            if (room == null)
                return null;
            for (Door d :
                    room.doors) {
                if (d.face == Room.Face.SOUTH)
                    return d;
            }
        }
        if (door.face == Room.Face.EAST) {
            Room room = getRoom(door.room.gridX + 1, door.room.gridY);
            if (room == null)
                return null;
            for (Door d :
                    room.doors) {
                if (d.face == Room.Face.WEST)
                    return d;
            }
        }
        if (door.face == Room.Face.WEST) {
            Room room = getRoom(door.room.gridX - 1, door.room.gridY);
            if (room == null)
                return null;
            for (Door d :
                    room.doors) {
                if (d.face == Room.Face.EAST)
                    return d;
            }
        }
        if (door.face == Room.Face.SOUTH) {
            Room room = getRoom(door.room.gridX, door.room.gridY + 1);
            if (room == null)
                return null;
            for (Door d :
                    room.doors) {
                if (d.face == Room.Face.NORTH)
                    return d;
            }
        }
        return null;
    }

    public Room getRoom(int gridX, int gridY) {
        return this.rooms.stream().filter(room -> room.gridX == gridX && room.gridY == gridY).findFirst().orElse(null);
    }
}


