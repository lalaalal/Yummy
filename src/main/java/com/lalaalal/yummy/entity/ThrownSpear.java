package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.entity.ai.YummyAttributeModifiers;
import com.lalaalal.yummy.world.damagesource.YummyDamageSources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownSpear extends AbstractArrow {
    private static final EntityDataAccessor<Byte> ID_LOYALTY = SynchedEntityData.defineId(ThrownSpear.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(ThrownSpear.class, EntityDataSerializers.BOOLEAN);
    protected ItemStack spearItem = new ItemStack(Items.AIR);
    private boolean dealtDamage = false;
    private int clientSideReturnTickCount = 0;

    public ThrownSpear(EntityType<? extends ThrownSpear> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownSpear(Level level, LivingEntity shooter, ItemStack stack) {
        this(YummyEntities.SPEAR.get(), level, shooter, stack);
    }

    public ThrownSpear(EntityType<? extends ThrownSpear> entityType, Level level, LivingEntity shooter, ItemStack stack) {
        super(entityType, shooter, level);
        spearItem = stack.copy();
        this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(stack));
        this.entityData.set(ID_FOIL, spearItem.hasFoil());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_LOYALTY, (byte) 0);
        this.entityData.define(ID_FOIL, false);
    }

    public byte getLoyalty() {
        return this.entityData.get(ID_LOYALTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("DealtDamage", dealtDamage);
        compoundTag.put("Spear", spearItem.save(new CompoundTag()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        dealtDamage = compoundTag.getBoolean("DealtDamage");
        if (compoundTag.contains("Spear")) {
            this.spearItem = ItemStack.of(compoundTag.getCompound("Spear"));
            this.entityData.set(ID_LOYALTY, (byte) EnchantmentHelper.getLoyalty(spearItem));
            this.entityData.set(ID_FOIL, spearItem.hasFoil());
        }
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity owner = this.getOwner();
        int loyaltyLevel = getLoyalty();
        if (loyaltyLevel > 0 && (this.dealtDamage || this.isNoPhysics()) && owner != null) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1f);
                }

                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = owner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * loyaltyLevel, this.getZ());
                if (level().isClientSide) {
                    this.yOld = this.getY();
                }

                double scale = 0.05 * loyaltyLevel;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(scale)));
                if (this.clientSideReturnTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0f, 1.0f);
                }

                ++this.clientSideReturnTickCount;
            }
        }

        super.tick();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        Entity entity = result.getEntity();

        if (this.getType() != YummyEntities.SPEAR_OF_LONGINUS.get() && entity.getType() == EntityType.ENDERMAN)
            return;
        hurtHitEntity(entity);

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        this.playSound(soundevent, 1.0F, 1.0F);
    }

    protected void hurtHitEntity(Entity entity) {
        float damage = YummyAttributeModifiers.calcItemAttribute(1, spearItem, EquipmentSlot.MAINHAND, Attributes.ATTACK_DAMAGE);
        Entity owner = getOwner();
        damage += spearItem.getEnchantmentLevel(Enchantments.IMPALING);
        DamageSource damageSource = YummyDamageSources.spear(level(), owner, spearItem);
        entity.hurt(damageSource, damage);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    protected ItemStack getPickupItem() {
        return spearItem.copy();
    }

    public boolean hasFoil() {
        return this.entityData.get(ID_FOIL);
    }
}
