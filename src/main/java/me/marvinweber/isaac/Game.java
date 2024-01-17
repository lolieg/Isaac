package me.marvinweber.isaac;

import me.marvinweber.isaac.debug.DebugOptions;
import me.marvinweber.isaac.entities.BaseEntity;
import me.marvinweber.isaac.entities.Player;
import me.marvinweber.isaac.items.itempools.ItemPool;
import me.marvinweber.isaac.items.itempools.ItemPoolsParser;
import me.marvinweber.isaac.mapgen.Door;
import me.marvinweber.isaac.mapgen.Level;
import me.marvinweber.isaac.mapgen.Room;
import me.marvinweber.isaac.packets.MapUpdatePacket;
import me.marvinweber.isaac.packets.StateUpdatePacket;
import me.marvinweber.isaac.stats.CharacterBaseStats;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static me.marvinweber.isaac.Utility.getRandomNumber;
import static me.marvinweber.isaac.Utility.randomString;

public class Game {
    public Random random;
    public MinecraftServer server;
    public ServerWorld world;
    public State gameState;
    public Level level;
    public Integer chapter;
    public Room currentRoom;
    public ArrayList<Player> players;

    public HashMap<String, ItemPool> itemPools;

    public Collection<DebugOptions> activeDebugOptions;

    public Game(MinecraftServer server) {

        this.random = Random.create(randomString(8).hashCode());
        this.server = server;
        this.world = server.getOverworld();
        this.gameState = State.STOPPED;
        this.chapter = 1;
        this.level = new Level(this.random, (int) Math.round(3.33 * chapter + Math.round(getRandomNumber(5, 6, random))));
//        this.level = new Level(this.random, 30);
        this.players = new ArrayList<>();
        this.itemPools = ItemPoolsParser.parse();
        this.activeDebugOptions = new ArrayList<>();
        onGameStateChanged();
    }

    public void pause() {
        onGameStateChanged();
    }

    public void start() {
        this.gameState = State.GENERATING;

        this.server.getGameRules().get(GameRules.DO_FIRE_TICK).set(false, this.server);
        this.server.getGameRules().get(GameRules.BLOCK_EXPLOSION_DROP_DECAY).set(false, this.server);
        this.world.setSpawnPos(new BlockPos(0, 0, -21474836), 0.0f);

        updateRandomSeed();
        level.generate(this.server.getOverworld());
        this.itemPools = ItemPoolsParser.parse();

        this.currentRoom = level.startingRoom;
        this.currentRoom.entered = true;

        level.getAdjacentRooms(currentRoom).forEach(room -> room.changeAllDoors(world, Door.DoorState.FOGGED));
        setPlayers();

        players.forEach(player -> {
            player.init(world);
        });
        ScoreboardUtil.start(server);
        this.gameState = State.RUNNING;
        onGameStateChanged();
        onMapUpdate();
    }

    private void updateRandomSeed() {
        this.random.setSeed(randomString(8).hashCode());
        this.level.random = random;
    }

    public void stop() {
        ScoreboardUtil.stop(server);
        players.forEach(Player::terminate);

        level.degenerate(world);

        players = new ArrayList<>();

        world.iterateEntities().forEach(entity -> {
            if (!(entity instanceof PlayerEntity)) {
                entity.kill();
            }
        });

        this.gameState = State.STOPPED;
        onGameStateChanged();
    }

    public void restart() {
        stop();
        start();
    }

    public void update() {
        if (this.gameState == State.RUNNING) {
            level.update(this);
            this.players.forEach(player -> {
                if(player.dead)
                    player.terminate();
            });
            this.players.removeIf(player -> player.dead);
            if(this.players.size() < 1) {
                stop();
                return;
            }
            this.players.forEach(Player::update);
            ScoreboardUtil.tick(server, this);
            this.tickDebug();
        }
    }

    private void tickDebug() {
        if(this.activeDebugOptions.contains(DebugOptions.QUICK_KILL)) {
            assert this.currentRoom != null;
            this.currentRoom.enemies.forEach(baseEntity -> {
                baseEntity.damage(baseEntity.getDamageSources().outOfWorld(), Float.MAX_VALUE);
            });
        }
    }

    public void onEnemyDeath(BaseEntity enemy, DamageSource source) {
        if (enemy == null) return;
        this.currentRoom.enemies.remove(enemy);
    }

    public void onRoomChanged(Room newRoom, Player player) {
        System.out.println("room changed");
        if (newRoom.roomLayout != null)
            System.out.println("Room variant: " + newRoom.roomLayout.variant);

        player.onUpdateRecalculateStats();
        player.onUpdateHealth();

        if (newRoom.spawner != null && !newRoom.entered)
            newRoom.spawner.spawn();

        newRoom.entered = true;
        this.currentRoom = newRoom;

        onMapUpdate();
    }

    public void onMapUpdate() {
        this.level.rooms.stream()
                .filter(room -> (room.gridX == currentRoom.gridX + 1 && room.gridY == currentRoom.gridY) ||
                        (room.gridX == currentRoom.gridX - 1 && room.gridY == currentRoom.gridY) ||
                        (room.gridX == currentRoom.gridX && room.gridY == currentRoom.gridY + 1) ||
                        (room.gridX == currentRoom.gridX && room.gridY == currentRoom.gridY - 1))
                .forEach(room -> room.visible = true);

        Isaac.GAME_CHANNEL.serverHandle(players.stream().map(player1 -> player1.playerEntity).toList()).send(new MapUpdatePacket(this.level.rooms, this.currentRoom));
    }

    public void onInDoorSpace(Room room, Door door, Player player) {
        Door adjacentDoor = level.getAdjacentDoor(door);
        if (adjacentDoor != null){
            adjacentDoor.modify(world, Door.DoorState.AIR);
            Room newRoom = adjacentDoor.room;
            Vec3d doorEntry = new Vec3d(
                    player.playerEntity.getX() + ((newRoom.gridX - currentRoom.gridX) * 2),
                    11,
                    player.playerEntity.getZ() + ((newRoom.gridY - currentRoom.gridY) * 2));
            players.forEach(player1 -> {
                if (newRoom.entered && this.players.size() <= 1) return;
                player1.playerEntity.teleport(doorEntry.x, doorEntry.y, doorEntry.z);
            });
        }
    }

    public void onGameStateChanged() {
        Isaac.GAME_CHANNEL.serverHandle(this.server.getPlayerManager().getPlayerList()).send(new StateUpdatePacket(this.gameState));
    }

    private void setPlayers() {
        server.getPlayerManager().getPlayerList().forEach(playerEntity -> players.add(new Player(playerEntity, CharacterBaseStats.getIsaacBaseStats(), this)));
    }

    @Nullable
    public Player getPlayer(PlayerEntity playerEntity) {
        return players.stream().filter(player -> player.playerEntity == playerEntity).findFirst().orElse(null);
    }

    public enum State {
        RUNNING,
        GENERATING,
        PAUSED,
        STOPPED
    }
}
