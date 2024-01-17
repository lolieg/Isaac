package me.marvinweber.isaac.debug;

import net.minecraft.util.StringIdentifiable;

public enum DebugOptions implements StringIdentifiable {

    ENTITY_MARKER(1),
    GRID(2),
    INFINITE_HP(3),
    HIGH_DAMAGE(4),
    SHOW_ROOM_INFO(5),
    SHOW_HITSPHERES(6),
    SHOW_DAMAGE_VALUES(7),
    INFINITE_ITEM_CHARGES(8),
    HIGH_LUCK(9),
    QUICK_KILL(10),
    GRID_INFO(11),
    PLAYER_ITEM_INFO(12),
    SHOW_GRID_COLLISION_POINTS(13);
    public final int Id;

    public static final Codec<DebugOptions> CODEC = StringIdentifiable.createCodec(DebugOptions::values);
    DebugOptions(int Id) {
        this.Id = Id;
    }

    @Override
    public String asString() {
        return this.name();
    }
}
