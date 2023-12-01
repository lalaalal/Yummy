package com.lalaalal.yummy.block.entity;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, YummyMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<PollutedBlockEntity>> POLLUTED_BLOCK_ENTITY_TYPE
            = register("polluted_block_entity_type", PollutedBlockEntity::new, YummyBlocks.POLLUTED_BLOCK);

    public static final RegistryObject<BlockEntityType<PollutedBlockEntity>> CORRUPTED_POLLUTED_BLOCK_ENTITY_TYPE
            = register("corrupted_polluted_block_entity_type", (blockPos, blockState) -> new PollutedBlockEntity(YummyBlockEntities.CORRUPTED_POLLUTED_BLOCK_ENTITY_TYPE.get(), blockPos, blockState), YummyBlocks.CORRUPTED_POLLUTED_BLOCK);

    public static final RegistryObject<BlockEntityType<HerobrineSpawnerBlockEntity>> HEROBRINE_SPAWNER_BLOCK_ENTITY
            = register("herobrine_spawner_block_entity_type", (blockPos, blockState) -> new HerobrineSpawnerBlockEntity(YummyBlockEntities.HEROBRINE_SPAWNER_BLOCK_ENTITY.get(), blockPos, blockState), YummyBlocks.HEROBRINE_SPAWNER_BLOCK);

    public static final RegistryObject<BlockEntityType<SoulCrafterBlockEntity>> SOUL_CRAFTER_BLOCK_ENTITY_TYPE
            = register("soul_crafter_block_entity_type", SoulCrafterBlockEntity::new, YummyBlocks.SOUL_CRAFTER);

    public static final RegistryObject<BlockEntityType<YummySignBlockEntity>> YUMMY_SIGN_BLOCK_ENTITY
            = BLOCK_ENTITY_TYPES.register("yummy_sign_block_entity", () -> BlockEntityType.Builder.of(YummySignBlockEntity::new,
            YummyBlocks.EBONY_SIGN.get(),
            YummyBlocks.EBONY_WALL_SIGN.get()
    ).build(null));

    public static final RegistryObject<BlockEntityType<YummyHangingSignBlockEntity>> YUMMY_HANGING_SIGN_BLOCK_ENTITY
            = BLOCK_ENTITY_TYPES.register("yummy_hanging_sign_block_entity", () -> BlockEntityType.Builder.of(YummyHangingSignBlockEntity::new,
            YummyBlocks.EBONY_HANGING_SIGN.get(),
            YummyBlocks.EBONY_WALL_HANGING_SIGN.get()
    ).build(null));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> blockEntitySupplier, RegistryObject<Block> block) {
        return BLOCK_ENTITY_TYPES.register(name,
                () -> BlockEntityType.Builder.of(blockEntitySupplier, block.get()).build(null));
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
