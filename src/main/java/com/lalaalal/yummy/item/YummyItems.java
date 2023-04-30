package com.lalaalal.yummy.item;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.entity.YummyEntities;
import com.lalaalal.yummy.sound.YummySounds;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YummyMod.MOD_ID);
    private static final DeferredRegister<Item> VANILLA_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static final RegistryObject<Item> RUBELLITE = ITEMS.register("rubellite",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));

    public static final RegistryObject<Item> MANGANESE_NODULE = ITEMS.register("manganese_nodule",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> MANGANESE_COMPOUNDS = ITEMS.register("manganese_compounds",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));

    public static final RegistryObject<Item> PURIFIED_SOUL_METAL = ITEMS.register("purified_soul_metal",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> PURIFIED_SOUL_SHARD = ITEMS.register("purified_soul_shard",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));

    public static final RegistryObject<Item> SPEAR = ITEMS.register("spear",
            () -> new SpearItem(new Item.Properties().durability(250).tab(YummyMod.TAB)));
    public static final RegistryObject<Item> MIGHTY_HOLY_SPEAR = ITEMS.register("mighty_holy_spear",
            () -> new SpearItem(new Item.Properties()
                    .tab(YummyMod.TAB)
                    .rarity(Rarity.EPIC), YummyEntities.MIGHTY_HOLY_SPEAR.get()));
    public static final RegistryObject<Item> SPEAR_OF_LONGINUS = ITEMS.register("spear_of_longinus",
            () -> new SpearOfLonginusItem(new Item.Properties().tab(YummyMod.TAB)
                    .durability(0)
                    .fireResistant()));
    public static final RegistryObject<Item> STEEL_HELMET = ITEMS.register("steel_helmet",
            () -> new ArmorItem(RubelliteMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate",
            () -> new ArmorItem(RubelliteMaterial.INSTANCE, EquipmentSlot.CHEST, new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> STEEL_LEGGINGS = ITEMS.register("steel_leggings",
            () -> new ArmorItem(RubelliteMaterial.INSTANCE, EquipmentSlot.LEGS, new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> STEEL_BOOTS = ITEMS.register("steel_boots",
            () -> new ArmorItem(RubelliteMaterial.INSTANCE, EquipmentSlot.FEET, new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword",
            () -> new SwordItem(SteelTier.INSTANCE, 3, -2.4f, new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe",
            () -> new AxeItem(SteelTier.INSTANCE, 5, -3, new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe",
            () -> new HoeItem(SteelTier.INSTANCE, -3, 0, new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe",
            () -> new PickaxeItem(SteelTier.INSTANCE, 1, -2.8f, new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel",
            () -> new ShovelItem(SteelTier.INSTANCE, 1.5f, -3, new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> PURIFIED_SOUL_SWORD = ITEMS.register("purified_soul_sword",
            () -> new PurifiedSoulSwordItem(new Item.Properties().tab(YummyMod.TAB)
                    .durability(0)
                    .fireResistant()
                    .rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> MARK_FIREBALL = ITEMS.register("mark_fireball",
            () -> new MarkFireballItem(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> METEOR_STAFF = ITEMS.register("meteor_staff",
            () -> new MeteorStaffItem(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> FLOATING_STICK = ITEMS.register("floating_stick",
            () -> new FloatingStick(new Item.Properties().tab(YummyMod.TAB)));

    public static final RegistryObject<Item> EBONY_SIGN = ITEMS.register("ebony_sign",
            () -> new SignItem(new Item.Properties().tab(YummyMod.TAB).stacksTo(16), YummyBlocks.EBONY_SIGN.get(), YummyBlocks.EBONY_WALL_SIGN.get()));

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
    public static final RegistryObject<Item> GOD_BLOOD = ITEMS.register("god_blood",
            () -> new Item(new Item.Properties()
                    .tab(YummyMod.TAB)
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> HEROBRINE_SPAWN_EGG = ITEMS.register("herobrine_spawn_egg",
            () -> new ForgeSpawnEggItem(YummyEntities.HEROBRINE, 0x0f0f0f, 0xff0000, new Item.Properties().tab(YummyMod.TAB)));

    public static final RegistryObject<Item> BONE_MEAL = VANILLA_ITEMS.register("bone_meal",
            () -> new YummyBoneMealItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        VANILLA_ITEMS.register(eventBus);
    }
}
