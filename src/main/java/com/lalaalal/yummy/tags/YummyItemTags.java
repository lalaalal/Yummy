package com.lalaalal.yummy.tags;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class YummyItemTags extends ItemTagsProvider {
    /*public YummyItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagsProvider.TagLookup<Block>> provider, ExistingFileHelper helper) {
        super(output, future, provider, YummyMod.MOD_ID, helper);
    }*/
    public YummyItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, YummyMod.MOD_ID, helper);
    }


    @Override
    protected void addTags(HolderLookup.Provider provider){
        tag(ItemTags.MUSIC_DISCS).add(
                YummyItems.HEROBRINE_PHASE1_DISC.get(),
                YummyItems.HEROBRINE_PHASE2_DISC.get(),
                YummyItems.HEROBRINE_PHASE3_DISC.get()
        );

        tag(Tags.Items.ARMORS_HELMETS).add(
                YummyItems.PURIFIED_SOUL_HELMET.get()
        );
        tag(Tags.Items.ARMORS_CHESTPLATES).add(
                YummyItems.PURIFIED_SOUL_CHESTPLATE.get()
        );
        tag(Tags.Items.ARMORS_LEGGINGS).add(
                YummyItems.PURIFIED_SOUL_LEGGINGS.get()
        );
        tag(Tags.Items.ARMORS_BOOTS).add(
                YummyItems.PURIFIED_SOUL_BOOTS.get()
        );
        tag(Tags.Items.ARMORS).add(
                YummyItems.PURIFIED_SOUL_HELMET.get(),
                YummyItems.PURIFIED_SOUL_CHESTPLATE.get(),
                YummyItems.PURIFIED_SOUL_LEGGINGS.get(),
                YummyItems.PURIFIED_SOUL_BOOTS.get()
        );

        tag(ItemTags.TRIMMABLE_ARMOR).add(
                YummyItems.PURIFIED_SOUL_HELMET.get(),
                YummyItems.PURIFIED_SOUL_CHESTPLATE.get(),
                YummyItems.PURIFIED_SOUL_LEGGINGS.get(),
                YummyItems.PURIFIED_SOUL_BOOTS.get()
        );
        tag(ItemTags.CHEST_BOATS).add(
                YummyItems.EBONY_CHEST_BOAT_ITEM.get()
        );

    }
}
