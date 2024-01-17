package me.marvinweber.isaac.entities;

import me.marvinweber.isaac.Game;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.debug.DebugOptions;
import me.marvinweber.isaac.items.IsaacItem;
import me.marvinweber.isaac.packets.HealthUpdatePacket;
import me.marvinweber.isaac.packets.StateUpdatePacket;
import me.marvinweber.isaac.packets.StatsUpdatePacket;
import me.marvinweber.isaac.registry.common.ItemRegistry;
import me.marvinweber.isaac.registry.common.PacketRegistry;
import me.marvinweber.isaac.stats.HealthManager;
import me.marvinweber.isaac.stats.PlayerStats;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Pair;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Player {

    public ServerPlayerEntity playerEntity;
    public PlayerStats playerStats;
    public HealthManager healthManager;
    public Game game;

    public List<IsaacItem> items;
    public boolean dead;
    private int hitCooldown;

    public Player(ServerPlayerEntity playerEntity, PlayerStats character, Game game) {
        this.playerEntity = playerEntity;
        this.playerStats = character;
        this.game = game;
        this.items = new ArrayList<>();
        this.dead = false;
        this.hitCooldown = 0;
    }

    public static @Nullable Player findPlayer(UUID uuid) {
        if (Isaac.game == null || Isaac.game.players.size() <= 0)
            return null;
        List<Player> players = Isaac.game.players.stream().filter(player -> player.playerEntity.getUuid() == uuid).toList();
        if (players.size() <= 0)
            return null;
        return players.get(0);
    }

    public void init(ServerWorld world) {
        this.healthManager = new HealthManager(this, playerStats.hearts);
        Pair<Integer, Integer> center = game.currentRoom.getACenterBlock();
        this.playerEntity.teleport(world, center.getLeft(), 11, center.getRight(), 0, 0);
        this.playerEntity.getInventory().clear();

        ItemStack itemStack = FilledMapItem.createMap(world, game.currentRoom.gridX * 16, game.currentRoom.gridY * 16 - 15, (byte) 1, true, true);
        this.playerEntity.changeGameMode(GameMode.ADVENTURE);
        this.playerEntity.getInventory().setStack(8, itemStack);
        this.playerEntity.setHealth(this.playerEntity.getMaxHealth());
        this.playerEntity.setInvulnerable(true);
        this.playerEntity.giveItemStack(new ItemStack(ItemRegistry.TEAR_ITEM));
        this.playerEntity.getAbilities().allowModifyWorld = true;
        this.playerEntity.sendAbilitiesUpdate();

        this.onUpdateRecalculateStats();
        this.onUpdateHealth();
    }

    public void terminate() {
        this.playerEntity.getInventory().clear();
        this.playerEntity.changeGameMode(GameMode.CREATIVE);
        this.playerStats = null;
        this.healthManager = null;
    }

    public void update() {
        this.playerEntity.getHungerManager().setFoodLevel(20);
        this.playerEntity.setExperiencePoints(0);

        this.checkCollision();

        if (this.hitCooldown >= 0) {
            this.hitCooldown--;
        }
    }

    private void checkCollision() {
        List<Entity> entities = this.playerEntity.world.getOtherEntities(this.playerEntity, this.playerEntity.getBoundingBox());
        for (Entity entity :
                entities) {
            if (entity instanceof BaseEntity) {
                this.hit(1);
            }
        }
    }

    public void onUpdateRecalculateStats() {
        this.playerStats.calculate(items);

        Isaac.PLAYER_CHANNEL.serverHandle(this.playerEntity).send(new StatsUpdatePacket(this.playerStats));
        Objects.requireNonNull(this.playerEntity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(this.playerStats.speed.current * 0.1f * 1.7);
    }

    public void onUpdateHealth() {
        this.healthManager.update();
        Isaac.PLAYER_CHANNEL.serverHandle(this.playerEntity).send(new HealthUpdatePacket(this.healthManager.hearts));
    }

    public void addItem(IsaacItem item) {
        this.items.add(item);
        this.onUpdateHealth();
        this.onUpdateRecalculateStats();
    }

    public void hit(int damage) {
        if(this.hitCooldown > 0 || Isaac.game.activeDebugOptions.contains(DebugOptions.INFINITE_HP)) {
            return;
        }
        this.healthManager.damage(damage);
        this.onUpdateHealth();
        this.hitCooldown = 40;
    }

    public void onNoHealth() {
        this.dead = true;
    }
}
