package com.lalaalal.yummy.block.entity;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlockRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyBlockEntityRegister {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, YummyMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<PollutedBlockEntity>> POLLUTED_BLOCK_ENTITY_TYPE
            = register("polluted_block_entity_type", PollutedBlockEntity::new, YummyBlockRegister.POLLUTED_BLOCK);

    public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> blockEntitySupplier, RegistryObject<Block> block) {
        return BLOCK_ENTITY_TYPES.register(name,
                () -> BlockEntityType.Builder.of(blockEntitySupplier, block.get()).build(null));
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
