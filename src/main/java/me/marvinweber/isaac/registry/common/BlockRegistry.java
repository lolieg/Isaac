package me.marvinweber.isaac.registry.common;

import io.wispforest.owo.itemgroup.OwoItemSettings;
import io.wispforest.owo.registration.reflect.BlockRegistryContainer;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.blocks.IsaacFireBlock;
import me.marvinweber.isaac.blocks.IsaacObstacleBlock;
import me.marvinweber.isaac.blocks.bomb.BombBlock;
import me.marvinweber.isaac.blocks.pedestal.PedestalBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;



public class BlockRegistry implements BlockRegistryContainer {
    public static final PedestalBlock PEDESTAL_BLOCK = new PedestalBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));
    public static final BombBlock BOMB_BLOCK = new BombBlock(FabricBlockSettings.of(Material.TNT).strength(4.0f));
    public static final IsaacObstacleBlock ROCK_BLOCK = new IsaacObstacleBlock(FabricBlockSettings.of(Material.STONE, MapColor.DEEPSLATE_GRAY).strength(8));
    public static final IsaacObstacleBlock POOP_BLOCK = new IsaacObstacleBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BROWN).strength(2));
    public static final IsaacFireBlock FIRE_BLOCK = new IsaacFireBlock(FabricBlockSettings.of(Material.FIRE, MapColor.BRIGHT_RED).luminance(15).strength(2));

    @Override
    public BlockItem createBlockItem(Block block, String identifier) {
        return new BlockItem(block, new OwoItemSettings().group(Isaac.ISAAC_ITEMS));
    }
}
