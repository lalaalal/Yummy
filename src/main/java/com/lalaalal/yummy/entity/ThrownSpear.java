package com.lalaalal.yummy.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ThrownSpear extends AbstractArrow {
    protected ItemStack spearItem = new ItemStack(Items.AIR);

    public ThrownSpear(EntityType<? extends ThrownSpear> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownSpear(Level level, LivingEntity shooter, ItemStack stack) {
        this(YummyEntities.SPEAR.get(), level, shooter, stack);
    }

    public ThrownSpear(EntityType<? extends ThrownSpear> entityType, Level level, LivingEntity shooter, ItemStack stack) {
        super(entityType, shooter, level);
        spearItem = stack.copy();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        Entity entity = result.getEntity();
        hurtHitEntity(entity);

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        this.playSound(soundevent, 1.0F, 1.0F);
    }

    protected void hurtHitEntity(Entity entity) {
        Entity owner = getOwner();
        DamageSource damageSource = DamageSource.thrown(owner == null ? entity : owner, this);
        entity.hurt(damageSource, 2f);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    protected ItemStack getPickupItem() {
        return spearItem.copy();
    }
}
