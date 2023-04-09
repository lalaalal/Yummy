package com.lalaalal.yummy.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Stun extends MobEffect {
    protected Stun() {
        super(MobEffectCategory.HARMFUL, 0xFFFF00);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "E21757F0-D698-11ED-AFA1-0242AC120002", -15, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        Level level = pLivingEntity.getLevel();
        BlockState blockState = level.getBlockState(pLivingEntity.getOnPos());
        if (!blockState.is(Blocks.AIR))
            pLivingEntity.makeStuckInBlock(level.getBlockState(pLivingEntity.getOnPos()), new Vec3(0.01, 0.01, 0.01));
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
