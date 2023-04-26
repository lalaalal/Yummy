package com.lalaalal.yummy.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class YummyBoneMealItem extends BoneMealItem {
    public YummyBoneMealItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);

        if (player == null)
            return super.useOn(context);

        if (blockState.is(BlockTags.SMALL_FLOWERS)) {
            if (level.isClientSide)
                doClientEffect(level, player, blockPos, context.getHand());
            else
                provideFlower(level, player, blockPos, blockState, context.getItemInHand());
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    private void provideFlower(Level level, Player player, BlockPos blockPos, BlockState blockState, ItemStack itemStack) {
        double x = blockPos.getX() + 0.5;
        double y = blockPos.getY() + 0.5;
        double z = blockPos.getZ() + 0.5;

        level.addFreshEntity(new ItemEntity(level, x, y, z, new ItemStack(blockState.getBlock(), 1)));
        if (!player.isCreative())
            itemStack.shrink(1);
    }

    private void doClientEffect(Level level, Player player, BlockPos blockPos, InteractionHand hand) {
        addBoneMealParticle(level, blockPos);
        level.playSound(player, blockPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 1);
        player.swing(hand, true);
    }

    private void addBoneMealParticle(Level level, BlockPos blockPos) {
        RandomSource random = level.getRandom();

        for (int i = 0; i < 10; i++) {
            double particleX = blockPos.getX() + random.nextDouble();
            double particleY = blockPos.getY() + random.nextDouble();
            double particleZ = blockPos.getZ() + random.nextDouble();
            double speed = random.nextDouble() * 0.7 + 1;

            level.addParticle(ParticleTypes.HAPPY_VILLAGER, particleX, particleY, particleZ, 0, speed, 0);
        }
    }
}
