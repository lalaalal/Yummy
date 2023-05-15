package com.lalaalal.yummy.block;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.YummyTypes;
import com.lalaalal.yummy.world.feature.tree.EbonyTreeGrower;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class YummyBlocks {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, YummyMod.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YummyMod.MOD_ID);

    public static final RegistryObject<Block> AMETHYST_BLOCK = register("amethyst_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(1.5f, 0.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)),
            CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RUBELLITE_BLOCK = register("rubellite_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f, 6f)
                    .requiresCorrectToolForDrops()),
            YummyMod.TAB);
    public static final RegistryObject<Block> RUBELLITE_ORE = register("rubellite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(3f, 3f)
                    .requiresCorrectToolForDrops(), UniformInt.of(3, 7)),
            YummyMod.TAB);
    public static final RegistryObject<Block> DEEPSLATE_RUBELLITE_ORE = register("deepslate_rubellite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4.5f, 3f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE), UniformInt.of(3, 7)),
            YummyMod.TAB);
    public static final RegistryObject<Block> MANGANITE = register("manganite",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f, 6f)
                    .requiresCorrectToolForDrops()),
            YummyMod.TAB);
    public static final RegistryObject<Block> HEROBRINE_SPAWNER_BLOCK = register("herobrine_spawner_block",
            () -> new HerobrineSpawnerBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NETHER)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F, 6.0F)
                    .sound(SoundType.NETHER_BRICKS)
                    .lightLevel(blockState -> 7)),
            YummyMod.TAB);

    public static final RegistryObject<Block> PURIFIED_SOUL_BLOCK = BLOCKS.register("purified_soul_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT)
                    .strength(0.5F)
                    .speedFactor(0.4F)));
    public static final RegistryObject<Block> PURIFIED_SOUL_FIRE_BLOCK = BLOCKS.register("purified_soul_fire",
            () -> new PurifiedSoulFireBlock(BlockBehaviour.Properties.of(Material.FIRE, MaterialColor.COLOR_BLACK)
                    .noCollission()
                    .instabreak()
                    .lightLevel((blockState) -> 5)
                    .sound(SoundType.WOOL)));

    public static final RegistryObject<FlammableRotatedPillarBlock> EBONY_LOG = register("ebony_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), YummyMod.TAB);
    public static final RegistryObject<FlammableRotatedPillarBlock> EBONY_WOOD = register("ebony_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), YummyMod.TAB);
    public static final RegistryObject<Block> STRIPPED_EBONY_LOG = register("stripped_ebony_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)), YummyMod.TAB);
    public static final RegistryObject<Block> STRIPPED_EBONY_WOOD = register("stripped_ebony_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), YummyMod.TAB);
    public static final RegistryObject<Block> EBONY_PLANKS = register("ebony_planks",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), 5, 20), YummyMod.TAB);
    public static final RegistryObject<Block> EBONY_LEAVES = register("ebony_leaves",
            () -> new FlammableLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)
                    .requiresCorrectToolForDrops()), YummyMod.TAB);
    public static final RegistryObject<Block> EBONY_SAPLING = register("ebony_sapling",
            () -> new SaplingBlock(new EbonyTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), YummyMod.TAB);
    public static final RegistryObject<Block> HARD_EBONY_PLANKS = register("hard_ebony_planks",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(40f, 6f), 5, 20), YummyMod.TAB);
    public static final RegistryObject<Block> EBONY_FENCE = register("ebony_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)), YummyMod.TAB);
    public static final RegistryObject<Block> EBONY_FENCE_GATE = register("ebony_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)), YummyMod.TAB);
    public static final RegistryObject<Block> EBONY_SLAB = register("ebony_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)), YummyMod.TAB);
    public static final RegistryObject<Block> EBONY_STAIRS = register("ebony_stairs",
            () -> new StairBlock(() -> EBONY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)), YummyMod.TAB);
    public static final RegistryObject<Block> EBONY_BUTTON = register("ebony_button",
            () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)), YummyMod.TAB);
    public static final RegistryObject<Block> EBONY_PRESSURE_PLATE = register("ebony_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)), YummyMod.TAB);

    public static final RegistryObject<Block> EBONY_DOOR = register("ebony_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)), YummyMod.TAB);
    public static final RegistryObject<SignBlock> EBONY_SIGN = BLOCKS.register("ebony_sign",
            () -> new YummyStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), YummyTypes.WOOD_EBONY));
    public static final RegistryObject<SignBlock> EBONY_WALL_SIGN = BLOCKS.register("ebony_wall_sign",
            () -> new YummyWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), YummyTypes.WOOD_EBONY));
    public static final RegistryObject<Block> EBONY_TRAPDOOR = register("ebony_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)), YummyMod.TAB);

    public static final RegistryObject<Block> POLLUTED_BLOCK = register(PollutedBlock.getName(false),
            () -> new PollutedBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(66f, 1200f)
                    .requiresCorrectToolForDrops()), YummyMod.TAB);
    public static final RegistryObject<Block> CORRUPTED_POLLUTED_BLOCK = register(PollutedBlock.getName(true),
            () -> new PollutedBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .requiresCorrectToolForDrops(), true), YummyMod.TAB);
    public static final RegistryObject<Block> DISPLAYING_POLLUTED_BLOCK = register("displaying_" + PollutedBlock.getName(false),
            () -> new PollutedBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(-1.0F, 3600000.0F)
                    .requiresCorrectToolForDrops(), false, false, true, false), YummyMod.TAB);

    public static final RegistryObject<Item> PURIFIED_SOUL_BLOCK_ITEM = ITEMS.register("purified_soul_block",
            () -> new BlockItem(PURIFIED_SOUL_BLOCK.get(), new Item.Properties()
                    .fireResistant()
                    .tab(YummyMod.TAB)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> registryObject = BLOCKS.register(name, block);
        registerBlockItem(name, registryObject, tab);

        return registryObject;
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block, CreativeModeTab tab) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
