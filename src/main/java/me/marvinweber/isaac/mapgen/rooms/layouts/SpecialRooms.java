package me.marvinweber.isaac.mapgen.rooms.layouts;

public enum SpecialRooms {
    SHOP("2"),
    ERROR("3"),
    TREASURE("4"),
    BOSS("5"),
    MINI_BOSS("6"),
    SECRET("7"),
    SUPER_SECRET("8"),
    ULTRA_SECRET("29"),
    ARCADE("9"),
    CURSED("10"),
    CHALLENGE("11"),
    LIBRARY("12"),
    SACRIFICE("13"),
    DEVIL("14"),
    ANGEL("15"),
    DUNGEON("16"),
    BOSS_RUSH("17"),
    BEDROOM("18"),
    BARREN("19"),
    VAULT("20"),
    DICE("21"),
    BLACK_MARKET("22"),
    BLUE_ROOM("28"),
    SECRET_EXIT("27"),
    PLANETARIUM("24");

    public String type;

    SpecialRooms(String type) {
        this.type = type;
    }
}
