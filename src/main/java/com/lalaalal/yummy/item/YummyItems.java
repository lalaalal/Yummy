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
    public static final RegistryObject<Item> SOUL_AMETHYST_SWORD = registerElementSword("soul_amethyst_sword", Element.AMETHYST, 4, -1.6f);
    public static final RegistryObject<Item> SOUL_COPPER_SWORD = registerElementSword("soul_copper_sword", Element.COPPER, 4, -1.6f);
    public static final RegistryObject<Item> SOUL_DIAMOND_SWORD = registerElementSword("soul_diamond_sword", Element.DIAMOND, 8, -1.4f);
    public static final RegistryObject<Item> SOUL_EMERALD_SWORD = registerElementSword("soul_emerald_sword", Element.EMERALD, 4, -1.6f);
    public static final RegistryObject<Item> SOUL_GOLD_SWORD = registerElementSword("soul_gold_sword", Element.GOLD, 4, -1.6f);
    public static final RegistryObject<Item> SOUL_LAPIS_SWORD = registerElementSword("soul_lapis_sword", Element.LAPIS, 4, -1.6f);
    public static final RegistryObject<Item> SOUL_REDSTONE_SWORD = registerElementSword("soul_redstone_sword", Element.REDSTONE, 4, -1.6f);
    public static final RegistryObject<Item> SOUL_RUBELLITE_SWORD = registerElementSword("soul_rubellite_sword", Element.RUBELLITE, 4, -1.6f);

    public static final RegistryObject<Item> SOUL_AMETHYST_ESSENCE = registerElementIngot("soul_amethyst_essence");
    public static final RegistryObject<Item> SOUL_COPPER_ESSENCE = registerElementIngot("soul_copper_essence");
    public static final RegistryObject<Item> SOUL_DIAMOND_ESSENCE = registerElementIngot("soul_diamond_essence");
    public static final RegistryObject<Item> SOUL_EMERALD_ESSENCE = registerElementIngot("soul_emerald_essence");
    public static final RegistryObject<Item> SOUL_GOLD_ESSENCE = registerElementIngot("soul_gold_essence");
    public static final RegistryObject<Item> SOUL_LAPIS_ESSENCE = registerElementIngot("soul_lapis_essence");
    public static final RegistryObject<Item> SOUL_REDSTONE_ESSENCE = registerElementIngot("soul_redstone_essence");
    public static final RegistryObject<Item> SOUL_RUBELLITE_ESSENCE = registerElementIngot("soul_rubellite_essence");

    private static RegistryObject<Item> registerElementSword(String name, Element element, int attackDamageModifier, float attackSpeedModifier) {
        return ITEMS.register(name, () -> new ElementSwordItem(YummyTiers.PURIFIED_SOUL, element, attackDamageModifier, attackSpeedModifier, new Item.Properties()
                .tab(YummyMod.TAB)
                .rarity(Rarity.RARE)));
    }

    private static RegistryObject<Item> registerElementIngot(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties().tab(YummyMod.TAB)));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        VANILLA_ITEMS.register(eventBus);
    }
}
