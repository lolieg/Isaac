package me.marvinweber.isaac.mapgen.rooms;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.blocks.multiblock.Multiblock;
import me.marvinweber.isaac.blocks.multiblock.MultiblockPart;
import me.marvinweber.isaac.entities.BaseEntity;
import me.marvinweber.isaac.entities.monsters.dip.DipEntity;
import me.marvinweber.isaac.items.IsaacItem;
import me.marvinweber.isaac.items.itempools.ItemPool;
import me.marvinweber.isaac.mapgen.Room;
import me.marvinweber.isaac.mapgen.rooms.layouts.Entities;
import me.marvinweber.isaac.mapgen.rooms.layouts.RoomLayout;
import me.marvinweber.isaac.registry.common.BlockRegistry;
import me.marvinweber.isaac.registry.common.EntityRegistry;
import me.marvinweber.isaac.registry.common.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.*;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Spawner {
    private final Room room;
    private final World world;

    public final ArrayList<ItemEntity> multipleChoiceItems = new ArrayList<>();

    public Spawner(Room room, World world) {
        this.room = room;
        this.world = world;
    }

    public void spawn() {
        for (RoomLayout.Spawn spawn :
                this.room.roomLayout.spawns) {
            int x = this.room.X1 + Integer.parseInt(spawn.x) * 2 + 1;
            int z = this.room.Z1 + Integer.parseInt(spawn.y) * 2 + 1;
            Entities entity = getEntityEnum(spawn.entity);
            if (entity == null) {
                DipEntity dip = new DipEntity(EntityRegistry.DIP_ENTITY_TYPE, world);
                dip.setPos(x + 1, this.room.properties.defaultHeight + 2, z + 1);
                world.spawnEntity(dip);
                this.room.enemies.add(dip);
                continue;
            }

            if(entity.spawnType == Entities.SpawnType.MINECRAFT_ENTITY) {
                EntityType<?> entityType = findEntity(entity);
                if (entityType == null) continue;

                BaseEntity entity1 = (BaseEntity) entityType.create(world);
                world.spawnEntity(entity1);
                this.room.enemies.add(entity1);
            }

            if (entity.spawnType == Entities.SpawnType.MINECRAFT_BLOCK) {
                spawnObstacle(world, x, z, getSpawnable(entity));
                continue;
            }

            if (entity.spawnType == Entities.SpawnType.SPECIAL) {
                if (entity == Entities.RANDOM_COLLECTIBLE) {
                    ItemPool itemPool = this.room.getItemPool();
                    IsaacItem item = itemPool.getMinecraftItem(itemPool.pickRandomItemWeighted(Isaac.game.random));
                    while (item == null) {
                        item = itemPool.getMinecraftItem(itemPool.pickRandomItemWeighted(Isaac.game.random));
                    }
                    spawnPedestalWithItem(x, z, new ItemStack(item));
                    continue;
                }
            }

        }

    }

    public Spawnable getSpawnable(Entities entity) {
        return switch (entity.entity.type) {
            case "1400" -> new Spawnable(BlockRegistry.FIRE_BLOCK.getDefaultState(), Arrays.asList(MultiblockPart.BOTTOM, MultiblockPart.EAST, MultiblockPart.SOUTH, MultiblockPart.SOUTH_EAST));
            case "1500" -> new Spawnable(BlockRegistry.POOP_BLOCK.getDefaultState());
            default -> new Spawnable(BlockRegistry.ROCK_BLOCK.getDefaultState());
        };
    }

    public void spawnPedestalWithItem(int x, int z, ItemStack itemStack) {
        world.setBlockState(new BlockPos(x, this.room.properties.defaultHeight + 1, z + 1), BlockRegistry.PEDESTAL_BLOCK.getDefaultState());
        ItemEntity itemEntity = new ItemEntity(world, x + 1, this.room.properties.defaultHeight + 2, z + 1, itemStack, 0, 0, 0);
        itemEntity.setNeverDespawn();
        world.spawnEntity(itemEntity);
        multipleChoiceItems.add(itemEntity);
    }


    private void spawnObstacle(World world, int x, int z, Spawnable spawnable) {
        BlockState blockState = spawnable.state;
        if(blockState.getBlock() instanceof Multiblock) {
            BlockPos pos = new BlockPos(x, this.room.properties.defaultHeight + 1, z);
            if(spawnable.structure != null){
                ((Multiblock) blockState.getBlock()).place(world, pos, blockState, spawnable.structure);
                return;
            }
            ((Multiblock) blockState.getBlock()).place(world, pos, blockState);
            return;
        }
        world.setBlockState(new BlockPos(x + 1, this.room.properties.defaultHeight + 1, z), blockState);


    }

    @Nullable
    public Entities getEntityEnum(RoomLayout.Entity entity) {
        for (Entities e :
                Entities.values()) {
            if(entity.type.equals(e.entity.type) && entity.variant.equals(e.entity.variant) && entity.subtype.equals(e.entity.subtype)) return e;
        }
        return null;
    }

    @Nullable
    public EntityType<?> findEntity(Entities type) {
        for (Map.Entry<Entities, EntityType<?>> entry :
                EntityRegistry.ENTITIES.entrySet()) {
            if (entry.getKey() == type) return entry.getValue();
        }
        return null;
    }

    private static class Spawnable {
        public BlockState state;
        @Nullable public List<MultiblockPart> structure;

        public Spawnable(BlockState state,  @Nullable List<MultiblockPart> structure) {
            this.state = state;
            this.structure = structure;
        }
        public Spawnable(BlockState state) {
            this.state = state;
        }
    }

}
