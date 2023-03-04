package com.lalaalal.yummy;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YummyMod.MOD_ID);
    public static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Item> NETHER_AMETHYST_ITEM = ITEMS.register("nether_amethyst",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> ENDERITE_INGOT_ITEM = ITEMS.register("enderite_ingot",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> ECHO_SILVER_SHARD_ITEM = ITEMS.register("echo_silver_shard",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> BONE_MEAL = VANILLA_ITEMS.register("bone_meal", YummyBoneMealItem::new);
}
