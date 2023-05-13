
package com.lalaalal.yummy.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.lalaalal.yummy.entity.ThrownSpear;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class SpearItem extends Item {
    protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("63d316c1-7d6d-41be-81c3-41fc1a216c27");
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private final SpearProvider spearProvider;

    public SpearItem(Properties properties) {
        this(properties, ThrownSpear::new);
    }

    public SpearItem(Properties properties, SpearProvider spearProvider) {
        super(properties);
        this.spearProvider = spearProvider;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", 3, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public SpearItem(Properties properties, EntityType<? extends ThrownSpear> spearType) {
        this(properties, (level, shooter, itemStack) -> new ThrownSpear(spearType, level, shooter, itemStack));
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        player.startUsingItem(interactionHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeLeft) {
        int i = this.getUseDuration(itemStack) - timeLeft;
        if (i >= 10 && !level.isClientSide)
            throwSpear(itemStack, level, livingEntity);
    }

    protected void throwSpear(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            ThrownSpear spear = spearProvider.create(level, livingEntity, itemStack);
            spear.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
            if (player.getAbilities().instabuild)
                spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;

            level.addFreshEntity(spear);
            level.playSound(null, spear, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
            if (!player.getAbilities().instabuild)
                player.getInventory().removeItem(itemStack);
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? defaultModifiers : super.getAttributeModifiers(slot, stack);
    }

    @FunctionalInterface
    public interface SpearProvider {
        ThrownSpear create(Level level, LivingEntity shooter, ItemStack itemStack);
    }
}
