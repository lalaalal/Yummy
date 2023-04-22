package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.item.YummyItems;
import com.lalaalal.yummy.misc.ItemDamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class ThrownSpearOfLonginus extends AbstractArrow {
    private ItemStack spearOfLonginusItem = new ItemStack(YummyItems.SPEAR_OF_LONGINUS_ITEM.get());

    public ThrownSpearOfLonginus(EntityType<? extends ThrownSpearOfLonginus> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownSpearOfLonginus(Level level, LivingEntity shooter, ItemStack stack) {
        super(YummyEntities.THROWN_SPEAR_OF_LONGINUS.get(), shooter, level);
        spearOfLonginusItem = stack.copy();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity instanceof LivingEntity livingEntity) {
            killEnemy(livingEntity);
        } else {
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
            AABB area = getBoundingBox().inflate(2.0);
            LivingEntity livingEntity = level.getNearestEntity(LivingEntity.class, TargetingConditions.DEFAULT, null, x, y, z, area);
            killEnemy(livingEntity);
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        this.playSound(soundevent, 1.0F, 1.0F);
    }

    private void killEnemy(@Nullable LivingEntity livingEntity) {
        if (livingEntity == null)
            return;
        DamageSource damageSource = new ItemDamageSource("thrown_spear_of_longinus", getOwner(), spearOfLonginusItem);
        damageSource.bypassArmor().bypassInvul();
        livingEntity.hurt(damageSource, Float.MAX_VALUE);
        if (livingEntity.isAlive())
            livingEntity.setHealth(0);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    protected ItemStack getPickupItem() {
        return spearOfLonginusItem.copy();
    }
}
