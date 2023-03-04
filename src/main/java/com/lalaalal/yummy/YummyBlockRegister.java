package com.lalaalal.yummy;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class YummyBlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, YummyMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YummyMod.MOD_ID);

    public static final RegistryObject<Block> NETHER_AMETHYST_BLOCK = register("nether_amethyst_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(1.5f, 0.5f).requiresCorrectToolForDrops()),
            YummyMod.TAB);
    public static final RegistryObject<Block> ENDERITE_BLOCK = register("enderite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(2.5f, 14f).requiresCorrectToolForDrops()),
            YummyMod.TAB);
    public static final RegistryObject<Block> ECHO_SILVER_BLOCK = register("echo_silver_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(2.5f, 14f).requiresCorrectToolForDrops()),
            YummyMod.TAB);
    public static final RegistryObject<Block> CYAN_FLOWER = register("cyan_flower",
            () -> new FlowerBlock(() -> MobEffects.GLOWING, 5,
                    BlockBehaviour.Properties.copy(Blocks.DANDELION).color(MaterialColor.COLOR_CYAN)),
            YummyMod.TAB);
    public static final RegistryObject<Block> POTTED_CYAN_FLOWER = BLOCKS.register("potted_cyan_flower",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CYAN_FLOWER,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION).color(MaterialColor.COLOR_CYAN)));

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> registryObject = BLOCKS.register(name, block);
        registerBlockItem(name, registryObject, tab);

        return registryObject;
    }

    public static <T extends Block> void registerBlockItem(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<Item> itemRegistryObject = ITEMS.register(name,
                () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }
}
