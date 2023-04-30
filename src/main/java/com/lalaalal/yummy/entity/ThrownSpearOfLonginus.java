package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.misc.ItemDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

public class ThrownSpearOfLonginus extends ThrownSpear {
    public ThrownSpearOfLonginus(EntityType<? extends ThrownSpearOfLonginus> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownSpearOfLonginus(Level level, LivingEntity shooter, ItemStack stack) {
        super(YummyEntities.THROWN_SPEAR_OF_LONGINUS.get(), level, shooter, stack);
    }

    @Override
    protected void hurtHitEntity(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            killEnemy(livingEntity);
        } else if (entity instanceof PartEntity<?> partEntity) {
            Entity parent = partEntity.getParent();
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
            AABB area = getBoundingBox().inflate(2.0);
            LivingEntity livingEntity = level.getNearestEntity(LivingEntity.class, TargetingConditions.DEFAULT, null, x, y, z, area);
            killEnemy(parent);
        }
    }

    private void killEnemy(@Nullable Entity livingEntity) {
        if (livingEntity == null)
            return;
        DamageSource damageSource = new ItemDamageSource("thrown_spear_of_longinus", getOwner(), spearItem);
        damageSource.bypassArmor().bypassInvul();
        livingEntity.hurt(damageSource, Float.MAX_VALUE);
        if (livingEntity.isAlive())
            livingEntity.kill();
    }
}
