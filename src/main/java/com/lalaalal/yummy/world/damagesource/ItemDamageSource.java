package com.lalaalal.yummy.world.damagesource;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemDamageSource extends DamageSource {
    private final Entity source;
    private final ItemStack usedItem;

    public ItemDamageSource(String id, @Nullable Entity source, ItemStack usedItem) {
        super(id);
        this.source = source;
        this.usedItem = usedItem;
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity livingEntity) {
        if (source == null)
            return Component.translatable(getTranslationId(), livingEntity.getDisplayName(), usedItem.getDisplayName());
        return Component.translatable(getTranslationId(), livingEntity.getDisplayName(), usedItem.getDisplayName(), source.getDisplayName());
    }

    public String getTranslationId() {
        String messageId = "death.attack." + super.getMsgId();
        return source == null ? messageId : messageId + ".player";
    }

    @Nullable
    @Override
    public Entity getEntity() {
        return source;
    }
}