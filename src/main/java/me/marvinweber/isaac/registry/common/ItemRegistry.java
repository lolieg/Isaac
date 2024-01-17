package me.marvinweber.isaac.registry.common;

import io.wispforest.owo.itemgroup.OwoItemSettings;
import io.wispforest.owo.registration.reflect.ItemRegistryContainer;
import me.marvinweber.isaac.Isaac;
import me.marvinweber.isaac.items.*;
import me.marvinweber.isaac.items.passive.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


import java.util.HashMap;

public class ItemRegistry implements ItemRegistryContainer {
    public static final TearItem TEAR_ITEM = new TearItem(new OwoItemSettings().group(Isaac.ISAAC_ITEMS));
    public static final SadOnionItem SAD_ONION_ITEM = new SadOnionItem();
    public static final InnerEyeItem INNER_EYE_ITEM = new InnerEyeItem();
    public static final SpoonBenderItem SPOON_BENDER_ITEM = new SpoonBenderItem();
    public static final CricketsHeadItem CRICKETS_HEAD_ITEM = new CricketsHeadItem();
    public static final MyReflectionItem MY_REFLECTION_ITEM = new MyReflectionItem();
    public static final NumberOneItem NUMBER_ONE_ITEM = new NumberOneItem();
    public static final BloodOfTheMartyrItem BLOOD_OF_THE_MARTYR_ITEM = new BloodOfTheMartyrItem();
    public static final PentagramItem PENTAGRAM_ITEM = new PentagramItem();

    public static final HashMap<Integer, IsaacItem> ITEMS = new HashMap<>();

    @Override
    public void afterFieldProcessing() {
        ITEMS.put(1, SAD_ONION_ITEM);
        ITEMS.put(2, INNER_EYE_ITEM);
        ITEMS.put(3, SPOON_BENDER_ITEM);
        ITEMS.put(4, CRICKETS_HEAD_ITEM);
        ITEMS.put(5, MY_REFLECTION_ITEM);
        ITEMS.put(6, NUMBER_ONE_ITEM);
        ITEMS.put(7, BLOOD_OF_THE_MARTYR_ITEM);

        ITEMS.put(51, PENTAGRAM_ITEM);
    }
}
