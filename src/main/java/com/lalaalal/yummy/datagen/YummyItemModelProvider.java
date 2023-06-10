package com.lalaalal.yummy.datagen;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class YummyItemModelProvider extends ItemModelProvider {
    public YummyItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, YummyMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleBlock(YummyBlocks.ALEMBIC_BLOCK);
        simpleItem(YummyItems.BUNNY_CHEST_ITEM);
        simpleItem(YummyItems.EBONY_BOAT_ITEM);
        simpleItem(YummyItems.EBONY_CHEST_BOAT_ITEM);
    }

    private ItemModelBuilder simpleBlock(RegistryObject<Block> block) {
        return withExistingParent(block.getId().getPath(),
                new ResourceLocation(YummyMod.MOD_ID, "block/" + block.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(YummyMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(YummyMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
