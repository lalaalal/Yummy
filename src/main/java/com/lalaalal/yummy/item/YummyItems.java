package com.lalaalal.yummy.item;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.sound.YummySounds;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YummyMod.MOD_ID);
    private static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Item> RUBELLITE = ITEMS.register("rubellite",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> SPEAR_OF_LONGINUS_ITEM = ITEMS.register("spear_of_longinus",
            () -> new SpearOfLonginusItem(new Item.Properties().tab(YummyMod.TAB)
                    .durability(0)
                    .fireResistant()));
    public static final RegistryObject<Item> PURIFIED_SOUL_METAL = ITEMS.register("purified_soul_metal",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> PURIFIED_SOUL_SHARD = ITEMS.register("purified_soul_shard",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> PURIFIED_SOUL_SWORD = ITEMS.register("purified_soul_sword",
            () -> new PurifiedSoulSwordItem(new Item.Properties().tab(YummyMod.TAB)
                    .durability(0)
                    .fireResistant()
                    .rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> MARK_FIREBALL = ITEMS.register("mark_fireball",
            () -> new MarkFireballItem(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> METEOR_STAFF = ITEMS.register("meteor_staff",
            () -> new MeteorStaffItem(new Item.Properties().tab(YummyMod.TAB)));


    public static final RegistryObject<Item> HEROBRINE_PHASE1_DISC = ITEMS.register("herobrine_phase1_disc",
            () -> new RecordItem(4, YummySounds.HEROBRINE_MUSIC_PHASE_1,
                    new Item.Properties()
                            .tab(YummyMod.TAB)
                            .stacksTo(1)
                            .rarity(Rarity.RARE), 2720));
    public static final RegistryObject<Item> HEROBRINE_PHASE2_DISC = ITEMS.register("herobrine_phase2_disc",
            () -> new RecordItem(4, YummySounds.HEROBRINE_MUSIC_PHASE_2,
                    new Item.Properties()
                            .tab(YummyMod.TAB)
                            .stacksTo(1)
                            .rarity(Rarity.RARE), 3200));
    public static final RegistryObject<Item> HEROBRINE_PHASE3_DISC = ITEMS.register("herobrine_phase3_disc",
            () -> new RecordItem(4, YummySounds.HEROBRINE_MUSIC_PHASE_3,
                    new Item.Properties()
                            .tab(YummyMod.TAB)
                            .stacksTo(1)
                            .rarity(Rarity.RARE), 3840));

    public static final RegistryObject<Item> BONE_MEAL = VANILLA_ITEMS.register("bone_meal",
            () -> new YummyBoneMealItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        VANILLA_ITEMS.register(eventBus);
    }
}
