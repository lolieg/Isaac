package me.marvinweber.isaac.mapgen.rooms.layouts;


public enum Entities {
    RANDOM_COLLECTIBLE(new RoomLayout.Entity("5", "100", "0", "1.0"), SpawnType.SPECIAL),
    DIP_ENTITY(new RoomLayout.Entity("217", "0", "0", "1.0"), SpawnType.MINECRAFT_ENTITY),
    ROCK(new RoomLayout.Entity("1000", "0", "0", "1.0"), SpawnType.MINECRAFT_BLOCK),
    POT(new RoomLayout.Entity("1002", "0", "0", "1.0"), SpawnType.MINECRAFT_BLOCK),
    Fire(new RoomLayout.Entity("1400", "0", "0", "1.0"), SpawnType.MINECRAFT_BLOCK),
    POOP(new RoomLayout.Entity("1500", "0", "0", "1.0"), SpawnType.MINECRAFT_BLOCK),
    METAL_BLOCK(new RoomLayout.Entity("1900", "0", "0", "1.0"), SpawnType.MINECRAFT_BLOCK),;

    public RoomLayout.Entity entity;
    public SpawnType spawnType;
    Entities(RoomLayout.Entity entity, SpawnType spawnType) {
        this.entity = entity;
        this.spawnType = spawnType;
    }

    public enum SpawnType {
        MINECRAFT_ENTITY,
        MINECRAFT_BLOCK,
        SPECIAL
    }
}
