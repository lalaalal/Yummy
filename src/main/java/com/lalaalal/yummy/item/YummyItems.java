package com.lalaalal.yummy.item;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.entity.YummyEntities;
import com.lalaalal.yummy.sound.YummySounds;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.item.Items.registerBlock;

public class YummyItems {
    private static boolean hasArmorSet(LivingEntity entity, Item helmet, Item chestplate, Item leggings, Item boots) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(helmet)
                && entity.getItemBySlot(EquipmentSlot.CHEST).is(chestplate)
                && entity.getItemBySlot(EquipmentSlot.LEGS).is(leggings)
                && entity.getItemBySlot(EquipmentSlot.FEET).is(boots);
    }

    private static boolean hasArmorLeaseOne(LivingEntity entity, Item helmet, Item chestplate, Item leggings, Item boots){
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(helmet)
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(chestplate)
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(leggings)
                || entity.getItemBySlot(EquipmentSlot.FEET).is(boots);
    }

    public static boolean hasPurifiedSoulFullSet(LivingEntity entity){
        return hasArmorSet(entity, YummyItems.PURIFIED_SOUL_HELMET.get(), YummyItems.PURIFIED_SOUL_CHESTPLATE.get(), YummyItems.PURIFIED_SOUL_LEGGINGS.get(), YummyItems.PURIFIED_SOUL_BOOTS.get());
    }
    public static boolean hasPurifiedSoulLeaseOne(LivingEntity entity){
        return hasArmorLeaseOne(entity, YummyItems.PURIFIED_SOUL_HELMET.get(), YummyItems.PURIFIED_SOUL_CHESTPLATE.get(), YummyItems.PURIFIED_SOUL_LEGGINGS.get(), YummyItems.PURIFIED_SOUL_BOOTS.get());
    }

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YummyMod.MOD_ID);

    public static final RegistryObject<Item> FANCY_DIAMOND = ITEMS.register("fancy_diamond",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EBONY_FRUIT = ITEMS.register("ebony_fruit",
            EbonyFruitItem::new);

    public static final RegistryObject<Item> PURIFIED_SOUL_METAL = ITEMS.register("purified_soul_metal",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> PURIFIED_SOUL_SHARD = ITEMS.register("purified_soul_shard",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> SPEAR = ITEMS.register("spear",
            () -> new SpearItem(new Item.Properties(), Tiers.IRON, 6, -2.9F, 0));
    public static final RegistryObject<Item> MIGHTY_HOLY_SPEAR = ITEMS.register("mighty_holy_spear",
            () -> new SpearItem(new Item.Properties()
                    .fireResistant()
                    .rarity(Rarity.EPIC), YummyTiers.GOD, 14, -2.9F, 3, YummyEntities.MIGHTY_HOLY_SPEAR
            ));
    public static final RegistryObject<Item> SPEAR_OF_LONGINUS = ITEMS.register("spear_of_longinus",
            () -> new SpearOfLonginusItem(new Item.Properties()
                    .durability(0)
                    .fireResistant()
            ));
    public static final RegistryObject<Item> PURIFIED_SOUL_SWORD = ITEMS.register("purified_soul_sword",
            () -> new PurifiedSoulSwordItem(YummyTiers.PURIFIED_SOUL_SWORD, 5, 2, new Item.Properties()
                    .fireResistant()
                    .rarity(Rarity.UNCOMMON)
            ));
    public static final RegistryObject<Item> MARK_FIREBALL = ITEMS.register("mark_fireball",
            () -> new MarkFireballItem(new Item.Properties()
                    .durability(7)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
            ));
    public static final RegistryObject<Item> METEOR_STAFF = ITEMS.register("meteor_staff",
            () -> new MeteorStaffItem(new Item.Properties()
                    .durability(14)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
            ));
    public static final RegistryObject<Item> FLOATING_STICK = ITEMS.register("floating_stick",
            () -> new FloatingStick(new Item.Properties()
                    .durability(6)
                    .fireResistant()
                    .rarity(Rarity.EPIC)
            ));
    public static final RegistryObject<Item> BUNNY_CHEST_ITEM = ITEMS.register("bunny_chest",
            () -> new BunnyChestItem(new Item.Properties()
                    .stacksTo(1)
            ));

    public static final RegistryObject<Item> EBONY_SIGN = ITEMS.register("ebony_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), YummyBlocks.EBONY_SIGN.get(), YummyBlocks.EBONY_WALL_SIGN.get()));
    public static final RegistryObject<Item> EBONY_HANGING_SIGN = ITEMS.register("ebony_hanging_sign",
            () -> new HangingSignItem(YummyBlocks.EBONY_HANGING_SIGN.get(), YummyBlocks.EBONY_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> EBONY_BOAT_ITEM = ITEMS.register("ebony_boat",
            () -> new EbonyBoatItem(false, new Item.Properties()
                    .stacksTo(1)
            ));
    public static final RegistryObject<Item> EBONY_CHEST_BOAT_ITEM = ITEMS.register("ebony_chest_boat",
            () -> new EbonyBoatItem(true, new Item.Properties()
                    .stacksTo(1)
            ));
    public static final RegistryObject<Item> EBONY_SWORD = ITEMS.register("ebony_sword",
            () -> new EbonySwordItem(Tiers.WOOD, 3, -2.4F, new Item.Properties()
            ));

    public static final RegistryObject<Item> HEROBRINE_PHASE1_DISC = ITEMS.register("herobrine_phase1_disc",
            () -> new RecordItem(4, YummySounds.HEROBRINE_MUSIC_PHASE_1,
                    new Item.Properties()
                            .stacksTo(1)
                            .fireResistant()
                            .rarity(Rarity.RARE), 2720));
    public static final RegistryObject<Item> HEROBRINE_PHASE2_DISC = ITEMS.register("herobrine_phase2_disc",
            () -> new RecordItem(4, YummySounds.HEROBRINE_MUSIC_PHASE_2,
                    new Item.Properties()
                            .stacksTo(1)
                            .fireResistant()
                            .rarity(Rarity.RARE), 3200));
    public static final RegistryObject<Item> HEROBRINE_PHASE3_DISC = ITEMS.register("herobrine_phase3_disc",
            () -> new RecordItem(4, YummySounds.HEROBRINE_MUSIC_PHASE_3,
                    new Item.Properties()
                            .stacksTo(1)
                            .fireResistant()
                            .rarity(Rarity.RARE), 3980));
    public static final RegistryObject<Item> GOD_BLOOD = ITEMS.register("god_blood",
            () -> new Item(new Item.Properties()
                    .stacksTo(1)
                    .fireResistant()
                    .rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> HEROBRINE_SPAWN_EGG = ITEMS.register("herobrine_spawn_egg",
            () -> new ForgeSpawnEggItem(YummyEntities.HEROBRINE, 0x0f0f0f, 0xff0000, new Item.Properties()));

    public static final RegistryObject<Item> UNSTABLE_ECHO_MATTER = ITEMS.register("unstable_echo_matter",
            () -> new TooltipItem(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ECHO_INGOT = ITEMS.register("echo_ingot",
            () -> new TooltipItem(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ECHO_KNIFE = ITEMS.register("echo_knife",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> ECHO_SWORD = ITEMS.register("echo_sword",
            () -> new EchoSwordItem(YummyTiers.PURIFIED_SOUL, 9, 8, new Item.Properties()
                    .rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> GOD_ECHO_SWORD = ITEMS.register("god_echo_sword",
            () -> new EchoSwordItem(YummyTiers.GOD, -51, 8, new Item.Properties()
                    .fireResistant()
                    .rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> FAKE_GOLD_INGOT = ITEMS.register("fake_gold_ingot",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SOUL_INFUSED_REDSTONE = ITEMS.register("soul_infused_redstone",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SOUL_INFUSED_COPPER = ITEMS.register("soul_infused_copper",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SOUL_INFUSED_GOLD = ITEMS.register("soul_infused_gold",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SOUL_INFUSED_EMERALD = ITEMS.register("soul_infused_emerald",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SOUL_INFUSED_DIAMOND = ITEMS.register("soul_infused_diamond",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SOUL_INFUSED_LAPIS = ITEMS.register("soul_infused_lapis",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SOUL_INFUSED_AMETHYST = ITEMS.register("soul_infused_amethyst",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> SOUL_INFUSED_FANCY_DIAMOND = ITEMS.register("soul_infused_fancy_diamond",
            () -> new Item(new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> ESSENCE_OF_PURITY = ITEMS.register("essence_of_purity",
            () -> new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> SOUL_INFUSED_REDSTONE_SWORD
            = registerSoulInfusedSword("redstone",2, -2);
    public static final RegistryObject<Item> SOUL_INFUSED_COPPER_SWORD
            = registerSoulInfusedSword("copper", 0, 2);
    public static final RegistryObject<Item> SOUL_INFUSED_GOLDEN_SWORD
            = registerSoulInfusedSword("golden", 1, 0);
    public static final RegistryObject<Item> SOUL_INFUSED_EMERALD_SWORD
            = registerSoulInfusedSword("emerald", 1, 0);
    public static final RegistryObject<Item> SOUL_INFUSED_DIAMOND_SWORD
            = registerSoulInfusedSword("diamond", 0, 0);
    public static final RegistryObject<Item> SOUL_INFUSED_LAPIS_SWORD
            = registerSoulInfusedSword("lapis", -4, 0);
    public static final RegistryObject<Item> SOUL_INFUSED_AMETHYST_SWORD
            = registerSoulInfusedSword("amethyst", 0, 0);
    public static final RegistryObject<Item> SOUL_INFUSED_FANCY_DIAMOND_SWORD
            = registerSoulInfusedSword("fancy_diamond", 0, 0);



    private static RegistryObject<Item> registerSoulInfusedSword(String element, int attackDamageModifier, float attackSpeedModifier) {
        return ITEMS.register("soul_infused_" + element + "_sword", () -> new SoulInfusedSwordItem(YummyTiers.SOUL_INFUSED, attackDamageModifier, attackSpeedModifier, new Item.Properties()
                .rarity(Rarity.RARE).fireResistant()));
    }

    private static RegistryObject<Item> registerSoulInfused(String element) {
        return ITEMS.register("soul_infused_" + element, () -> new Item(new Item.Properties()));
    }

    public static final RegistryObject<ArmorItem> PURIFIED_SOUL_HELMET = ITEMS.register("purified_soul_helmet", PurifiedSoulArmor.Helmet::new);
    public static final RegistryObject<ArmorItem> PURIFIED_SOUL_CHESTPLATE = ITEMS.register("purified_soul_chestplate", PurifiedSoulArmor.Chestplate::new);
    public static final RegistryObject<ArmorItem> PURIFIED_SOUL_LEGGINGS = ITEMS.register("purified_soul_leggings", PurifiedSoulArmor.Leggings::new);
    public static final RegistryObject<ArmorItem> PURIFIED_SOUL_BOOTS = ITEMS.register("purified_soul_boots", PurifiedSoulArmor.Boots::new);

    public static final RegistryObject<Item> PURIFIED_SOUL_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("purified_soul_upgrade_smithing_template", (() -> new PurifiedSoulTemplateItem().createPurifiedSoulUpgradeTemplate()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
