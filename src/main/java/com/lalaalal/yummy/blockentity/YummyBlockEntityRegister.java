package com.lalaalal.yummy.blockentity;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.PollutedBlockEntity;
import com.lalaalal.yummy.block.YummyBlockRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyBlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, YummyMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<PollutedBlockEntity>> POLLUTED_BLOCK_ENTITY_TYPE
            = BLOCK_ENTITY_TYPES.register("polluted_block_entity",
            () -> BlockEntityType.Builder.of(PollutedBlockEntity::new, YummyBlockRegister.POLLUTED_BLOCK.get())
                    .build(null));
}
