package me.marvinweber.isaac.registry.common;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.blocks.IsaacFireBlock;
import me.marvinweber.isaac.blocks.IsaacObstacleBlock;
import me.marvinweber.isaac.blocks.bomb.BombBlock;
import me.marvinweber.isaac.blocks.pedestal.PedestalBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {
    public static final PedestalBlock PEDESTAL_BLOCK = new PedestalBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final BombBlock BOMB_BLOCK = new BombBlock(FabricBlockSettings.of(Material.TNT).strength(4.0f));
    public static final IsaacObstacleBlock ROCK_BLOCK = new IsaacObstacleBlock(FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).strength(8));
    public static final IsaacObstacleBlock POOP_BLOCK = new IsaacObstacleBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BROWN).strength(2));
    public static final IsaacFireBlock FIRE_BLOCK = new IsaacFireBlock(FabricBlockSettings.of(Material.FIRE, MapColor.BRIGHT_RED).luminance(15).strength(2));

    public static void initialize() {
        Registry.register(Registry.BLOCK, new Identifier(Isaac.MOD_ID, "pedestal_block"), PEDESTAL_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Isaac.MOD_ID, "bomb_block"), BOMB_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Isaac.MOD_ID, "rock_block"), ROCK_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Isaac.MOD_ID, "poop_block"), POOP_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(Isaac.MOD_ID, "fire_block"), FIRE_BLOCK);
    }
}
