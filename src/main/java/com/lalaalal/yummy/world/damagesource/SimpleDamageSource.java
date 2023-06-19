package com.lalaalal.yummy.world.damagesource;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class SimpleDamageSource extends DamageSource {
    private final Entity source;
    private final Entity indirectSource;
    private final String id;

    public SimpleDamageSource(Holder<DamageType> type, String id, @Nullable Entity source) {
        this(type, id, source, null);
    }

    public SimpleDamageSource(Holder<DamageType> type, String id, @Nullable Entity source, @Nullable LivingEntity indirectSource) {
        super(type, source, indirectSource);
        this.id = id;
        this.source = source;
        this.indirectSource = indirectSource;
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity livingEntity) {
        if (source == null)
            return Component.translatable(getTranslationId(), livingEntity.getDisplayName());
        if (indirectSource == null)
            return Component.translatable(getTranslationId(), livingEntity.getDisplayName(), source.getDisplayName());
        return Component.translatable(getTranslationId(), livingEntity.getDisplayName(), indirectSource.getDisplayName(), source.getDisplayName());
    }

    public String getTranslationId() {
        return "death.attack." + getMsgId();
    }

    @Override
    public String getMsgId() {
        return id;
    }
}