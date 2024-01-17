package me.marvinweber.isaac.mapgen.rooms.layouts;

import me.marvinweber.isaac.mapgen.Room;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public class RoomLayout {
    public String variant;
    public String name;
    public String type;
    public String subtype;
    public String shape;
    public String width;
    public String height;
    public String difficulty;
    public String weight;

    public ArrayList<Door> doors;
    public ArrayList<Spawn> spawns;

    public RoomLayout(String variant, String name, String type, String subtype, String shape, String width, String height, String difficulty, String weight, ArrayList<Door> doors, ArrayList<Spawn> spawns) {
        this.variant = variant;
        this.name = name;
        this.type = type;
        this.subtype = subtype;
        this.shape = shape;
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.weight = weight;
        this.doors = doors;
        this.spawns = spawns;
    }

    public static RoomLayout pickRandomLayoutWeighted(List<RoomLayout> roomLayouts, Random random) {
        double totalWeight = 0.0;
        for (RoomLayout room :
                roomLayouts) {
            totalWeight += Double.parseDouble(room.weight);
        }

        int idx = 0;
        for (double r = random.nextDouble() * totalWeight; idx < roomLayouts.size() - 1; ++idx) {
            r -= Double.parseDouble(roomLayouts.get(idx).weight);
            if (r <= 0.0) break;
        }

        return roomLayouts.get(idx);
    }

    public static class Door {
        public boolean exists;
        public Room.Face face;

        public Door(boolean exists, Room.Face face) {
            this.exists = exists;
            this.face = face;
        }
    }

    public static class Spawn {
        public String x;
        public String y;
        public Entity entity;

        public Spawn(String x, String y, Entity entity) {
            this.x = x;
            this.y = y;
            this.entity = entity;
        }
    }

    public static class Entity {
        public String type;
        public String variant;
        public String subtype;
        public String weight;

        public Entity(String type, String variant, String subtype, String weight) {
            this.type = type;
            this.variant = variant;
            this.subtype = subtype;
            this.weight = weight;
        }
    }
    public boolean isEmpty() {
        return this.spawns.isEmpty();
    }
}
