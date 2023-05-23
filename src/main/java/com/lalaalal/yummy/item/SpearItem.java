
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
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

public class SpearItem extends Item {
    private static final Set<Enchantment> ENCHANTABLE = Set.of(Enchantments.LOYALTY, Enchantments.MENDING, Enchantments.UNBREAKING, Enchantments.IMPALING);
    protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("63d316c1-7d6d-41be-81c3-41fc1a216c27");
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    private final Map<Integer, Multimap<Attribute, AttributeModifier>> defaultModifiersByImpalingLevel = new HashMap<>();
    private final SpearProvider spearProvider;
    private final Tier tier;

    public SpearItem(Properties properties, Tier tier, int attackDamageModifier, float attackSpeedModifier, int attackReachModifier) {
        this(properties, tier, attackDamageModifier, attackSpeedModifier, attackReachModifier, ThrownSpear::new);
    }

    public SpearItem(Properties properties, Tier tier, int attackDamageModifier, float attackSpeedModifier, int attackReachModifier, EntityType<? extends ThrownSpear> spearType) {
        this(properties, tier, attackDamageModifier, attackSpeedModifier, attackReachModifier, (level, shooter, itemStack) -> new ThrownSpear(spearType, level, shooter, itemStack));
    }

    public SpearItem(Properties properties, Tier tier, int attackDamageModifier, float attackSpeedModifier, int attackReachModifier, SpearProvider spearProvider) {
        super(properties.durability(tier.getUses()));
        this.spearProvider = spearProvider;
        this.tier = tier;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", attackReachModifier, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", tier.getAttackDamageBonus() + attackDamageModifier, AttributeModifier.Operation.ADDITION));
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
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int timeLeft) {
        int i = this.getUseDuration(itemStack) - timeLeft;
        if (i >= 10 && !level.isClientSide)
            throwSpear(itemStack, level, livingEntity);
    }

    protected void throwSpear(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            ThrownSpear spear = spearProvider.create(level, livingEntity, itemStack);
            spear.setOwner(livingEntity);
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
        float damage = 0;
        for (AttributeModifier attributeModifier : defaultModifiers.get(Attributes.ATTACK_DAMAGE))
            damage += attributeModifier.getAmount();
        AttributeModifier attributeModifier = new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", damage + impalingLevel, AttributeModifier.Operation.ADDITION);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        Multimap<Attribute, AttributeModifier> modifiers = builder
                .putAll(ForgeMod.REACH_DISTANCE.get(), defaultModifiers.get(ForgeMod.REACH_DISTANCE.get()))
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
        return tier.getEnchantmentValue();
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
