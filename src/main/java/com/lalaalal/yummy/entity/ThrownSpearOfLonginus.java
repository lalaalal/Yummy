package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.item.YummyItemRegister;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class ThrownSpearOfLonginus extends AbstractArrow {
    private ItemStack spearOfLonginusItem = new ItemStack(YummyItemRegister.SPEAR_OF_LONGINUS_ITEM.get());

    public ThrownSpearOfLonginus(EntityType<? extends ThrownSpearOfLonginus> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownSpearOfLonginus(Level level, LivingEntity shooter, ItemStack stack) {
        super(YummyEntityRegister.THROWN_SPEAR_OF_LONGINUS.get(), shooter, level);
        spearOfLonginusItem = stack.copy();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        Entity entity = result.getEntity();
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity instanceof LivingEntity livingEntity) {
            DamageSource damageSource = new DamageSource("spear_of_longinus");
            livingEntity.hurt(damageSource, Float.MAX_VALUE);
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        this.playSound(soundevent, 1.0F, 1.0F);
    }

    @NotNull
    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @NotNull
    @Override
    protected ItemStack getPickupItem() {
        return spearOfLonginusItem.copy();
    }
}