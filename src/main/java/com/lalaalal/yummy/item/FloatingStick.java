package com.lalaalal.yummy.item;

import com.lalaalal.yummy.entity.FloatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FloatingStick extends Item {
    public FloatingStick(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);
            FloatingBlockEntity.floatBlock(level, pos, new Vec3(0, 0.5, 0), FloatingBlockEntity.DEFAULT_ACCELERATION, state);
        }
        return super.useOn(context);
    }
}
