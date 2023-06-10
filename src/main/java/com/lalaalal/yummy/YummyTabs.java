package com.lalaalal.yummy;

import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class YummyTabs {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, YummyMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> YUMMY_TAB = CREATIVE_MODE_TABS.register("yummy", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + YummyMod.MOD_ID))
            .icon(() -> new ItemStack(YummyItems.RUBELLITE.get()))
            .displayItems((features, entries) -> {
                entries.accept(YummyBlocks.EBONY_LOG.get());
                entries.accept(YummyBlocks.EBONY_WOOD.get());
                entries.accept(YummyBlocks.STRIPPED_EBONY_LOG.get());
                entries.accept(YummyBlocks.STRIPPED_EBONY_WOOD.get());
                entries.accept(YummyBlocks.EBONY_PLANKS.get());
                entries.accept(YummyBlocks.EBONY_STAIRS.get());
                entries.accept(YummyBlocks.EBONY_SLAB.get());
                entries.accept(YummyBlocks.EBONY_FENCE.get());
                entries.accept(YummyBlocks.EBONY_FENCE_GATE.get());
                entries.accept(YummyBlocks.EBONY_DOOR.get());
                entries.accept(YummyBlocks.EBONY_TRAPDOOR.get());
                entries.accept(YummyBlocks.EBONY_PRESSURE_PLATE.get());
                entries.accept(YummyBlocks.EBONY_BUTTON.get());
                entries.accept(YummyItems.EBONY_BOAT_ITEM.get());
                entries.accept(YummyItems.EBONY_CHEST_BOAT_ITEM.get());
                entries.accept(YummyItems.EBONY_SIGN.get());
                entries.accept(YummyBlocks.EBONY_LEAVES.get());
                entries.accept(YummyBlocks.EBONY_SAPLING.get());
                entries.accept(YummyItems.RUBELLITE.get());
                entries.accept(YummyItems.SPEAR.get());
                entries.accept(YummyItems.MIGHTY_HOLY_SPEAR.get());
                entries.accept(YummyItems.EBONY_SWORD.get());
                entries.accept(YummyItems.PURIFIED_SOUL_SWORD.get());
                entries.accept(YummyItems.ECHO_SWORD.get());
                entries.accept(YummyItems.GOD_ECHO_SWORD.get());
                entries.accept(YummyItems.SWORD_OF_EARTH.get());
                entries.accept(YummyItems.SWORD_OF_ELECTRICITY.get());
                entries.accept(YummyItems.SWORD_OF_FIRE.get());
                entries.accept(YummyItems.SWORD_OF_ICE.get());
                entries.accept(YummyItems.SWORD_OF_LIFE.get());
                entries.accept(YummyItems.SWORD_OF_SOUND.get());
                entries.accept(YummyItems.SWORD_OF_WATER.get());
                entries.accept(YummyItems.SWORD_OF_WIND.get());
                entries.accept(YummyItems.ECHO_INGOT.get());
                entries.accept(YummyBlocks.MANGANITE.get());
                entries.accept(YummyItems.MANGANESE_NODULE.get());
                entries.accept(YummyItems.MANGANESE_COMPOUNDS.get());
                entries.accept(YummyItems.ESSENCE_OF_EARTH.get());
                entries.accept(YummyItems.ESSENCE_OF_ELECTRICITY.get());
                entries.accept(YummyItems.ESSENCE_OF_FIRE.get());
                entries.accept(YummyItems.ESSENCE_OF_ICE.get());
                entries.accept(YummyItems.ESSENCE_OF_LIFE.get());
                entries.accept(YummyItems.ESSENCE_OF_SOUND.get());
                entries.accept(YummyItems.ESSENCE_OF_WATER.get());
                entries.accept(YummyItems.ESSENCE_OF_WIND.get());
                entries.accept(YummyItems.GOD_BLOOD.get());
                entries.accept(YummyItems.PURIFIED_SOUL_METAL.get());
                entries.accept(YummyItems.BUNNY_CHEST_ITEM.get());
                entries.accept(YummyItems.HEROBRINE_SPAWN_EGG.get());
                entries.accept(YummyItems.HEROBRINE_PHASE1_DISC.get());
                entries.accept(YummyItems.HEROBRINE_PHASE2_DISC.get());
                entries.accept(YummyItems.HEROBRINE_PHASE3_DISC.get());
                entries.accept(YummyItems.MARK_FIREBALL.get());
                entries.accept(YummyItems.FLOATING_STICK.get());
                entries.accept(YummyItems.METEOR_STAFF.get());
                entries.accept(YummyBlocks.AMETHYST_BLOCK.get());
                entries.accept(YummyBlocks.RUBELLITE_ORE.get());
                entries.accept(YummyBlocks.DEEPSLATE_RUBELLITE_ORE.get());
                entries.accept(YummyBlocks.HEROBRINE_SPAWNER_BLOCK.get());
                entries.accept(YummyBlocks.POLLUTED_BLOCK.get());
                entries.accept(YummyBlocks.CORRUPTED_POLLUTED_BLOCK.get());
                entries.accept(YummyBlocks.DISPLAYING_POLLUTED_BLOCK.get());
                entries.accept(YummyBlocks.PURIFIED_SOUL_BLOCK.get());
                entries.accept(YummyBlocks.HARD_EBONY_PLANKS.get());
            })
            .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
