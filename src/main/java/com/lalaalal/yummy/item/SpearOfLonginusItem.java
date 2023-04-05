package com.lalaalal.yummy.item;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.ThrownSpearOfLonginus;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpearOfLonginusItem extends TridentItem {
    public SpearOfLonginusItem() {
        super(new Properties().tab(YummyMod.TAB)
                .durability(0));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        DamageSource damageSource = new DamageSource("spear_of_longinus");
        pTarget.hurt(damageSource, Float.MAX_VALUE);
        return true;
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        player.startUsingItem(interactionHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity, int pTimeLeft) {
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(stack) - pTimeLeft;
            if (i >= 10 && !level.isClientSide) {
                ThrownSpearOfLonginus thrownSpearOfLonginus = new ThrownSpearOfLonginus(level, player, stack);
                thrownSpearOfLonginus.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
                if (player.getAbilities().instabuild) {
                    thrownSpearOfLonginus.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }

                level.addFreshEntity(thrownSpearOfLonginus);
                level.playSound(null, thrownSpearOfLonginus, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(stack);
                }
            }
        }
    }
}
