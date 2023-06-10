package com.lalaalal.yummy.datagen;

import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class YummyBlockLootTables extends BlockLootSubProvider {
    protected YummyBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(YummyBlocks.ALEMBIC_BLOCK.get());
        dropSelf(YummyBlocks.AMETHYST_BLOCK.get());
        add(YummyBlocks.DEEPSLATE_RUBELLITE_ORE.get(), block -> createOreDrop(block, YummyItems.RUBELLITE.get()));
        dropSelf(YummyBlocks.EBONY_BUTTON.get());
        createDoorTable(YummyBlocks.EBONY_DOOR.get());
        dropSelf(YummyBlocks.EBONY_FENCE.get());
        dropSelf(YummyBlocks.EBONY_FENCE_GATE.get());
        add(YummyBlocks.EBONY_LEAVES.get(), block -> createLeavesDrops(block, YummyBlocks.EBONY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(YummyBlocks.EBONY_LOG.get());
        dropSelf(YummyBlocks.EBONY_PLANKS.get());
        dropSelf(YummyBlocks.EBONY_PRESSURE_PLATE.get());
        dropSelf(YummyBlocks.EBONY_SAPLING.get());
        dropOther(YummyBlocks.EBONY_SIGN.get(), YummyItems.EBONY_SIGN.get());
        dropSelf(YummyBlocks.EBONY_SLAB.get());
        dropSelf(YummyBlocks.EBONY_STAIRS.get());
        dropSelf(YummyBlocks.EBONY_TRAPDOOR.get());
        dropOther(YummyBlocks.EBONY_WALL_SIGN.get(), YummyItems.EBONY_SIGN.get());
        dropSelf(YummyBlocks.EBONY_WOOD.get());
        dropSelf(YummyBlocks.HARD_EBONY_PLANKS.get());
        dropSelf(YummyBlocks.MANGANITE.get());
        dropSelf(YummyBlocks.PURIFIED_SOUL_BLOCK.get());
        dropSelf(YummyBlocks.RUBELLITE_BLOCK.get());
        add(YummyBlocks.RUBELLITE_ORE.get(), block -> createOreDrop(block, YummyItems.RUBELLITE.get()));
        dropSelf(YummyBlocks.STRIPPED_EBONY_LOG.get());
        dropSelf(YummyBlocks.STRIPPED_EBONY_WOOD.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return YummyBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
