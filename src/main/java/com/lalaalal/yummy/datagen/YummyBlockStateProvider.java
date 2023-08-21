package com.lalaalal.yummy.datagen;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class YummyBlockStateProvider extends BlockStateProvider {

    public YummyBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, YummyMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(YummyBlocks.AMETHYST_BLOCK);
        blockWithItem(YummyBlocks.DEEPSLATE_RUBELLITE_ORE);
        blockWithItem(YummyBlocks.EBONY_PLANKS);
        blockWithItem(YummyBlocks.HARD_EBONY_PLANKS);
        blockWithItem(YummyBlocks.HEROBRINE_SPAWNER_BLOCK);
        blockWithItem(YummyBlocks.MANGANITE);
        blockWithItem(YummyBlocks.PURIFIED_SOUL_BLOCK);
        blockWithItem(YummyBlocks.RUBELLITE_BLOCK);
        blockWithItem(YummyBlocks.RUBELLITE_ORE);
        buttonBlock(YummyBlocks.EBONY_BUTTON.get(), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_planks"));
        doorBlock(YummyBlocks.EBONY_DOOR.get(), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_door_bottom"), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_door_top"));
        fenceBlock(YummyBlocks.EBONY_FENCE.get(), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_planks"));
        fenceGateBlock(YummyBlocks.EBONY_FENCE_GATE.get(), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_planks"));
        logBlock(YummyBlocks.EBONY_LOG.get());
        logBlock(YummyBlocks.STRIPPED_EBONY_LOG.get());
        pressurePlateBlock(YummyBlocks.EBONY_PRESSURE_PLATE.get(), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_planks"));
        signBlock(YummyBlocks.EBONY_SIGN.get(), YummyBlocks.EBONY_WALL_SIGN.get(), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_planks"));
        slabBlock(YummyBlocks.EBONY_SLAB.get(), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_planks"), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_planks"));
        stairsBlock(YummyBlocks.EBONY_STAIRS.get(), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_planks"));
        trapdoorBlock(YummyBlocks.EBONY_TRAPDOOR.get(), new ResourceLocation(YummyMod.MOD_ID, "block/ebony_planks"), true);
    }

    private void blockWithItem(RegistryObject<? extends Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}
