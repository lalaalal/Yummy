package com.lalaalal.yummy.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemDamageSource extends DamageSource {
    private final Entity source;
    private final ItemStack usedItem;

    public ItemDamageSource(String messageId, Entity source, ItemStack usedItem) {
        super(messageId);
        this.source = source;
        this.usedItem = usedItem;
    }

    @NotNull
    @Override
    public Component getLocalizedDeathMessage(@NotNull LivingEntity livingEntity) {
        String messageId = "death.attack." + getMsgId();
        return Component.translatable(messageId, livingEntity.getDisplayName(), source.getDisplayName(), usedItem.getDisplayName());
    }
}