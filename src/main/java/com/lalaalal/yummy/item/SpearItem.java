
package com.lalaalal.yummy.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.lalaalal.yummy.client.renderer.YummyBlockEntityWithoutLevelRenderer;
import com.lalaalal.yummy.entity.ThrownSpear;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpearItem extends YummyTieredItem implements Vanishable {
    private static final Set<Enchantment> ENCHANTABLE = Set.of(Enchantments.LOYALTY, Enchantments.MENDING, Enchantments.UNBREAKING, Enchantments.IMPALING);
    protected static final UUID REACH_DISTANCE_UUID = UUID.fromString("63d316c1-7d6d-41be-81c3-41fc1a216c27");
    protected static final UUID ATTACK_RANGE_UUID = UUID.fromString("be7b5181-06e6-4268-a1e0-0a767a152901");
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private final Map<Integer, Multimap<Attribute, AttributeModifier>> defaultModifiersByImpalingLevel = new HashMap<>();
    private final SpearProvider spearProvider;
    private final float attackDamage;

    public SpearItem(Properties properties, Tier tier, int attackDamageModifier, float attackSpeedModifier, int attackReachModifier) {
        this(properties, tier, attackDamageModifier, attackSpeedModifier, attackReachModifier, ThrownSpear::new);
    }

    public SpearItem(Properties properties, Tier tier, int attackDamageModifier, float attackSpeedModifier, int attackReachModifier, Supplier<EntityType<? extends ThrownSpear>> spearType) {
        this(properties, tier, attackDamageModifier, attackSpeedModifier, attackReachModifier, (level, shooter, itemStack) -> new ThrownSpear(spearType.get(), level, shooter, itemStack));
    }

    public SpearItem(Properties properties, Tier tier, int attackDamageModifier, float attackSpeedModifier, int attackReachModifier, SpearProvider spearProvider) {
        super(tier, properties.durability(tier.getUses()));
        this.spearProvider = spearProvider;
        this.attackDamage = tier.getAttackDamageBonus() + attackDamageModifier;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(REACH_DISTANCE_UUID, "Weapon modifier", attackReachModifier, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(ATTACK_RANGE_UUID, "Weapon modifier", attackReachModifier, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeedModifier, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();

        defaultModifiersByImpalingLevel.put(0, defaultModifiers);
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
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (state.getDestroySpeed(level, pos) != 0)
            stack.hurtAndBreak(2, miningEntity, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeLeft) {
        int i = this.getUseDuration(itemStack) - timeLeft;
        if (i >= 10 && !level.isClientSide)
            throwSpear(itemStack, level, livingEntity);
    }

    protected void throwSpear(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            itemStack.hurtAndBreak(1, livingEntity, entity -> entity.broadcastBreakEvent(player.getUsedItemHand()));
            ThrownSpear spear = spearProvider.create(level, livingEntity, itemStack);
            spear.setOwner(livingEntity);
            spear.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5f, 1.0f);
            if (player.getAbilities().instabuild)
                spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;

            level.addFreshEntity(spear);
            level.playSound(null, spear, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0f, 1.0f);
            if (!player.getAbilities().instabuild)
                player.getInventory().removeItem(itemStack);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot != EquipmentSlot.MAINHAND)
            return super.getAttributeModifiers(slot, stack);
        int impalingLevel = stack.getEnchantmentLevel(Enchantments.IMPALING);
        Multimap<Attribute, AttributeModifier> modifiers = defaultModifiersByImpalingLevel.get(impalingLevel);
        if (modifiers != null)
            return modifiers;

        return cacheAttributeModifiers(impalingLevel);
    }

    private Multimap<Attribute, AttributeModifier> cacheAttributeModifiers(int impalingLevel) {
        AttributeModifier attributeModifier = new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage + impalingLevel, AttributeModifier.Operation.ADDITION);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        Multimap<Attribute, AttributeModifier> modifiers = builder
                .putAll(ForgeMod.BLOCK_REACH.get(), defaultModifiers.get(ForgeMod.BLOCK_REACH.get()))
                .putAll(ForgeMod.ENTITY_REACH.get(), defaultModifiers.get(ForgeMod.ENTITY_REACH.get()))
                .put(Attributes.ATTACK_DAMAGE, attributeModifier)
                .putAll(Attributes.ATTACK_SPEED, defaultModifiers.get(Attributes.ATTACK_SPEED))
                .build();
        defaultModifiersByImpalingLevel.put(impalingLevel, modifiers);
        return modifiers;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack itemStack, Enchantment enchantment) {
        return ENCHANTABLE.contains(enchantment);
    }

    @Override
    public int getEnchantmentValue(ItemStack itemStack) {
        return getTier().getEnchantmentValue();
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return true;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return YummyBlockEntityWithoutLevelRenderer.getInstance();
            }
        });
    }

    @FunctionalInterface
    public interface SpearProvider {
        ThrownSpear create(Level level, LivingEntity shooter, ItemStack itemStack);
    }
}
