package com.lalaalal.yummy.item;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.entity.YummyEntities;
import com.lalaalal.yummy.sound.YummySounds;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
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
            () -> new SpearItem(new Item.Properties()
                    .tab(YummyMod.TAB), Tiers.IRON));
    public static final RegistryObject<Item> MIGHTY_HOLY_SPEAR = ITEMS.register("mighty_holy_spear",
            () -> new SpearItem(new Item.Properties()
                    .tab(YummyMod.TAB)
                    .rarity(Rarity.EPIC), YummyTiers.GOD, YummyEntities.MIGHTY_HOLY_SPEAR.get()));
    public static final RegistryObject<Item> SPEAR_OF_LONGINUS = ITEMS.register("spear_of_longinus",
            () -> new SpearOfLonginusItem(new Item.Properties()
                    .durability(0)
                    .fireResistant()));
    public static final RegistryObject<Item> PURIFIED_SOUL_SWORD = ITEMS.register("purified_soul_sword",
            () -> new PurifiedSoulSwordItem(new Item.Properties().tab(YummyMod.TAB)
                    .durability(0)
                    .fireResistant()
                    .rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MARK_FIREBALL = ITEMS.register("mark_fireball",
            () -> new MarkFireballItem(new Item.Properties()
                    .durability(7)
                    .tab(YummyMod.TAB)
                    .rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> METEOR_STAFF = ITEMS.register("meteor_staff",
            () -> new MeteorStaffItem(new Item.Properties()
                    .durability(14)
                    .tab(YummyMod.TAB)
                    .rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> FLOATING_STICK = ITEMS.register("floating_stick",
            () -> new FloatingStick(new Item.Properties()
                    .durability(6)
                    .tab(YummyMod.TAB)
                    .rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> BUNNY_CHEST_ITEM = ITEMS.register("bunny_chest",
            () -> new BunnyChestItem(new Item.Properties()
                    .stacksTo(1)
                    .tab(YummyMod.TAB)));

    public static final RegistryObject<Item> EBONY_SIGN = ITEMS.register("ebony_sign",
            () -> new SignItem(new Item.Properties().tab(YummyMod.TAB).stacksTo(16), YummyBlocks.EBONY_SIGN.get(), YummyBlocks.EBONY_WALL_SIGN.get()));
    public static final RegistryObject<Item> EBONY_BOAT_ITEM = ITEMS.register("ebony_boat",
            () -> new EbonyBoatItem(false, new Item.Properties()
                    .stacksTo(1)
                    .tab(YummyMod.TAB)));
    public static final RegistryObject<Item> EBONY_CHEST_BOAT_ITEM = ITEMS.register("ebony_chest_boat",
            () -> new EbonyBoatItem(true, new Item.Properties()
                    .stacksTo(1)
                    .tab(YummyMod.TAB)));
    public static final RegistryObject<Item> EBONY_SWORD = ITEMS.register("ebony_sword",
            () -> new EbonySwordItem(Tiers.WOOD, 4, -2.6f, new Item.Properties()
                    .tab(YummyMod.TAB)));

    public static final RegistryObject<Item> HEROBRINE_PHASE1_DISC = ITEMS.register("herobrine_phase1_disc",
            () -> new RecordItem(4, YummySounds.HEROBRINE_MUSIC_PHASE_1,
                    new Item.Properties()
                            .tab(YummyMod.TAB)
                            .stacksTo(1)
                            .fireResistant()
                            .rarity(Rarity.RARE), 2720));
    public static final RegistryObject<Item> HEROBRINE_PHASE2_DISC = ITEMS.register("herobrine_phase2_disc",
            () -> new RecordItem(4, YummySounds.HEROBRINE_MUSIC_PHASE_2,
                    new Item.Properties()
                            .tab(YummyMod.TAB)
                            .stacksTo(1)
                            .fireResistant()
                            .rarity(Rarity.RARE), 3200));
    public static final RegistryObject<Item> HEROBRINE_PHASE3_DISC = ITEMS.register("herobrine_phase3_disc",
            () -> new RecordItem(4, YummySounds.HEROBRINE_MUSIC_PHASE_3,
                    new Item.Properties()
                            .tab(YummyMod.TAB)
                            .stacksTo(1)
                            .fireResistant()
                            .rarity(Rarity.RARE), 3840));
    public static final RegistryObject<Item> GOD_BLOOD = ITEMS.register("god_blood",
            () -> new Item(new Item.Properties()
                    .tab(YummyMod.TAB)
                    .stacksTo(1)
                    .fireResistant()
                    .rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> HEROBRINE_SPAWN_EGG = ITEMS.register("herobrine_spawn_egg",
            () -> new ForgeSpawnEggItem(YummyEntities.HEROBRINE, 0x0f0f0f, 0xff0000, new Item.Properties().tab(YummyMod.TAB)));

    public static final RegistryObject<Item> UNSTABLE_ECHO_MATTER = ITEMS.register("unstable_echo_matter",
            () -> new TooltipItem(new Item.Properties().tab(YummyMod.TAB)));

    public static final RegistryObject<Item> ECHO_INGOT = ITEMS.register("echo_ingot",
            () -> new TooltipItem(new Item.Properties().tab(YummyMod.TAB)));

    public static final RegistryObject<Item> ECHO_KNIFE = ITEMS.register("echo_knife",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));

    public static final RegistryObject<Item> ECHO_SWORD = ITEMS.register("echo_sword",
            () -> new EchoSwordItem(YummyTiers.PURIFIED_SOUL, 9, 8, new Item.Properties()
                    .tab(YummyMod.TAB)
                    .rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GOD_ECHO_SWORD = ITEMS.register("god_echo_sword",
            () -> new EchoSwordItem(YummyTiers.GOD, -51, 8, new Item.Properties()
                    .fireResistant()
                    .tab(YummyMod.TAB)
                    .rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> ESSENCE_OF_EARTH = ITEMS.register("essence_of_earth",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> ESSENCE_OF_ELECTRICITY = ITEMS.register("essence_of_electricity",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> ESSENCE_OF_FIRE = ITEMS.register("essence_of_fire",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> ESSENCE_OF_ICE = ITEMS.register("essence_of_ice",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> ESSENCE_OF_LIFE = ITEMS.register("essence_of_life",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> ESSENCE_OF_SOUND = ITEMS.register("essence_of_sound",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> ESSENCE_OF_WATER = ITEMS.register("essence_of_water",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    public static final RegistryObject<Item> ESSENCE_OF_WIND = ITEMS.register("essence_of_wind",
            () -> new Item(new Item.Properties().tab(YummyMod.TAB)));

    public static final RegistryObject<Item> SWORD_OF_EARTH
            = registerElementSword(Element.EARTH, 2, -2);
    public static final RegistryObject<Item> SWORD_OF_ELECTRICITY
            = registerElementSword(Element.ELECTRICITY, 0, 2);
    public static final RegistryObject<Item> SWORD_OF_FIRE
            = registerElementSword(Element.FIRE, 0, 0);
    public static final RegistryObject<Item> SWORD_OF_ICE
            = registerElementSword(Element.ICE, 0, 0);
    public static final RegistryObject<Item> SWORD_OF_LIFE
            = registerElementSword(Element.LIFE, 0, 0);
    public static final RegistryObject<Item> SWORD_OF_SOUND
            = registerElementSword(Element.SOUND, 0, 0);
    public static final RegistryObject<Item> SWORD_OF_WATER
            = registerElementSword(Element.WATER, 0, 0);
    public static final RegistryObject<Item> SWORD_OF_WIND
            = registerElementSword(Element.WIND, 0, 0);

    private static RegistryObject<Item> registerElementSword(Element element, int attackDamageModifier, float attackSpeedModifier) {
        return ITEMS.register("sword_of_" + element, () -> new ElementSwordItem(YummyTiers.ELEMENT, element, attackDamageModifier, attackSpeedModifier, new Item.Properties()
                .tab(YummyMod.TAB)
                .rarity(Rarity.RARE)));
    }

    private static RegistryObject<Item> registerElementEssence(Element element) {
        return ITEMS.register("essence_of_" + element, () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        VANILLA_ITEMS.register(eventBus);
    }
}
