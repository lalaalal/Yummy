package com.lalaalal.yummy.block;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.YummyTypes;
import com.lalaalal.yummy.world.feature.tree.EbonyTreeGrower;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class YummyBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, YummyMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, YummyMod.MOD_ID);

    public static final RegistryObject<Block> AMETHYST_BLOCK = register("amethyst_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)
                    .strength(1.5f, 0.5f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> RUBELLITE_BLOCK = register("rubellite_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(5f, 6f)
                    .requiresCorrectToolForDrops())
            );
    public static final RegistryObject<Block> RUBELLITE_ORE = register("rubellite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(3f, 3f)
                    .requiresCorrectToolForDrops(), UniformInt.of(3, 7))
            );
    public static final RegistryObject<Block> DEEPSLATE_RUBELLITE_ORE = register("deepslate_rubellite_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(4.5f, 3f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE), UniformInt.of(3, 7))
            );
    public static final RegistryObject<Block> MANGANITE = register("manganite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)
                    .strength(5f, 6f)
                    .requiresCorrectToolForDrops())
            );
    public static final RegistryObject<Block> HEROBRINE_SPAWNER_BLOCK = register("herobrine_spawner_block",
            () -> new HerobrineSpawnerBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_NETHER_BRICKS)
                    .requiresCorrectToolForDrops()
                    .noLootTable()
                    .strength(2.0F, 6.0F)
                    .sound(SoundType.NETHER_BRICKS)
                    .lightLevel(blockState -> 7))
            );

    public static final RegistryObject<Block> PURIFIED_SOUL_BLOCK = BLOCKS.register("purified_soul_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)
                    .strength(0.5F)
                    .speedFactor(0.4F)));
    public static final RegistryObject<Block> PURIFIED_SOUL_FIRE_BLOCK = BLOCKS.register("purified_soul_fire",
            () -> new PurifiedSoulFireBlock(BlockBehaviour.Properties.copy(Blocks.SOUL_FIRE)
                    .mapColor(MapColor.COLOR_BLACK)
                    .noCollission()
                    .noLootTable()
                    .instabreak()
                    .lightLevel((blockState) -> 5)
                    .sound(SoundType.WOOL)));

    public static final RegistryObject<FlammableRotatedPillarBlock> EBONY_LOG = register("ebony_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<FlammableRotatedPillarBlock> EBONY_WOOD = register("ebony_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<FlammableRotatedPillarBlock> STRIPPED_EBONY_LOG = register("stripped_ebony_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_EBONY_WOOD = register("stripped_ebony_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> EBONY_PLANKS = register("ebony_planks",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), 5, 20));
    public static final RegistryObject<Block> EBONY_LEAVES = register("ebony_leaves",
            () -> new FlammableLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> EBONY_SAPLING = register("ebony_sapling",
            () -> new SaplingBlock(new EbonyTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> HARD_EBONY_PLANKS = register("hard_ebony_planks",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)
                    .strength(40f, 6f), 5, 20));
    public static final RegistryObject<FenceBlock> EBONY_FENCE = register("ebony_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceGateBlock> EBONY_FENCE_GATE = register("ebony_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), YummyTypes.EBONY_WOOD_TYPE));
    public static final RegistryObject<SlabBlock> EBONY_SLAB = register("ebony_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<StairBlock> EBONY_STAIRS = register("ebony_stairs",
            () -> new StairBlock(() -> EBONY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<ButtonBlock> EBONY_BUTTON = register("ebony_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), YummyTypes.EBONY_BLOCK_SET_TYPE, 20, true));
    public static final RegistryObject<PressurePlateBlock> EBONY_PRESSURE_PLATE = register("ebony_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), YummyTypes.EBONY_BLOCK_SET_TYPE));

    public static final RegistryObject<DoorBlock> EBONY_DOOR = register("ebony_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), YummyTypes.EBONY_BLOCK_SET_TYPE));
    public static final RegistryObject<YummyStandingSignBlock> EBONY_SIGN = BLOCKS.register("ebony_sign",
            () -> new YummyStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), YummyTypes.EBONY_WOOD_TYPE));
    public static final RegistryObject<YummyWallSignBlock> EBONY_WALL_SIGN = BLOCKS.register("ebony_wall_sign",
            () -> new YummyWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), YummyTypes.EBONY_WOOD_TYPE));
    public static final RegistryObject<TrapDoorBlock> EBONY_TRAPDOOR = register("ebony_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), YummyTypes.EBONY_BLOCK_SET_TYPE));

    public static final RegistryObject<Block> POLLUTED_BLOCK = register(PollutedBlock.getName(false),
            () -> new PollutedBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .noLootTable()
                    .strength(66f, 1200f)
                    .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CORRUPTED_POLLUTED_BLOCK = register(PollutedBlock.getName(true),
            () -> new PollutedBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .noLootTable()
                    .strength(-1.0F, 3600000.0F)
                    .requiresCorrectToolForDrops(), true));
    public static final RegistryObject<Block> DISPLAYING_POLLUTED_BLOCK = register("displaying_" + PollutedBlock.getName(false),
            () -> new PollutedBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .noLootTable()
                    .strength(-1.0F, 3600000.0F)
                    .requiresCorrectToolForDrops(), false, false, true, false));

    public static final RegistryObject<Item> PURIFIED_SOUL_BLOCK_ITEM = ITEMS.register("purified_soul_block",
            () -> new BlockItem(PURIFIED_SOUL_BLOCK.get(), new Item.Properties()
                    .fireResistant()));

    public static final RegistryObject<Block> ALEMBIC_BLOCK = register("alembic",
            () -> new AlembicBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> registryObject = BLOCKS.register(name, block);
        registerBlockItem(name, registryObject);

        return registryObject;
    }

    private static <T extends Block> void registerBlockItem(String name, Supplier<T> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
