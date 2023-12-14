package com.lalaalal.yummy.tags;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class YummyBlockTags extends IntrinsicHolderTagsProvider<Block> {
    public YummyBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(output, Registries.BLOCK, future, block -> block.builtInRegistryHolder().key(), YummyMod.MOD_ID, helper);
    }
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

    }
}
