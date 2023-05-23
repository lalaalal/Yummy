package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.world.damagesource.ItemDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

public class ThrownSpearOfLonginus extends ThrownSpear {
    public ThrownSpearOfLonginus(EntityType<? extends ThrownSpearOfLonginus> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownSpearOfLonginus(Level level, LivingEntity shooter, ItemStack stack) {
        super(YummyEntities.SPEAR_OF_LONGINUS.get(), level, shooter, stack);
    }

    @Override
    protected void hurtHitEntity(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            killEnemy(livingEntity);
        } else if (entity instanceof PartEntity<?> partEntity) {
            Entity parent = partEntity.getParent();
            killEnemy(parent);
        }
    }

    private void killEnemy(@Nullable Entity livingEntity) {
        if (livingEntity == null)
            return;
        DamageSource damageSource = new ItemDamageSource(YummyMod.MOD_ID + ".thrown_spear_of_longinus", getOwner(), spearItem)
                .bypassInvul()
                .bypassArmor()
                .bypassMagic()
                .bypassEnchantments()
                .setProjectile();
        livingEntity.hurt(damageSource, Float.MAX_VALUE);
        if (livingEntity.isAlive())
            livingEntity.kill();
    }
}
