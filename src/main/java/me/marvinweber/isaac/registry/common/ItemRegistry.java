package me.marvinweber.isaac.registry.common;

import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.items.*;
import me.marvinweber.isaac.items.passive.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class ItemRegistry {
    public static final TearItem TEAR_ITEM = new TearItem(new Item.Settings().group(Isaac.ISAAC_ITEMS).maxCount(16));
    public static final SadOnionItem SAD_ONION_ITEM = new SadOnionItem();
    public static final InnerEyeItem INNER_EYE_ITEM = new InnerEyeItem();
    public static final SpoonBenderItem SPOON_BENDER_ITEM = new SpoonBenderItem();
    public static final CricketsHeadItem CRICKETS_HEAD_ITEM = new CricketsHeadItem();
    public static final MyReflectionItem MY_REFLECTION_ITEM = new MyReflectionItem();
    public static final NumberOneItem NUMBER_ONE_ITEM = new NumberOneItem();
    public static final BloodOfTheMartyrItem BLOOD_OF_THE_MARTYR_ITEM = new BloodOfTheMartyrItem();

    public static final PentagramItem PENTAGRAM_ITEM = new PentagramItem();

    public static final HashMap<Integer, IsaacItem> ITEMS = new HashMap<>();

    public static void initialize() {
        ITEMS.put(1, SAD_ONION_ITEM);
        ITEMS.put(2, INNER_EYE_ITEM);
        ITEMS.put(3, SPOON_BENDER_ITEM);
        ITEMS.put(4, CRICKETS_HEAD_ITEM);
        ITEMS.put(5, MY_REFLECTION_ITEM);
        ITEMS.put(6, NUMBER_ONE_ITEM);
        ITEMS.put(7, BLOOD_OF_THE_MARTYR_ITEM);

        ITEMS.put(51, PENTAGRAM_ITEM);

        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "tear_item"), TEAR_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "sad_onion_item"), SAD_ONION_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "inner_eye_item"), INNER_EYE_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "spoon_bender_item"), SPOON_BENDER_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "crickets_head_item"), CRICKETS_HEAD_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "my_reflection_item"), MY_REFLECTION_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "number_one_item"), NUMBER_ONE_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "blood_of_the_martyr_item"), BLOOD_OF_THE_MARTYR_ITEM);
        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "pentagram_item"), PENTAGRAM_ITEM);

        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "pedestal_block"), new BlockItem(BlockRegistry.PEDESTAL_BLOCK, new FabricItemSettings().group(Isaac.ISAAC_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "bomb_block"), new BombItem(BlockRegistry.BOMB_BLOCK, new FabricItemSettings().group(Isaac.ISAAC_ITEMS)));
        Registry.register(Registry.ITEM, new Identifier(Isaac.MOD_ID, "rock_block"), new BlockItem(BlockRegistry.ROCK_BLOCK, new FabricItemSettings().group(Isaac.ISAAC_ITEMS)));
    }
}
