package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.entity.AnimatedImageEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public abstract class AnimatedImageEntityRenderer extends EntityRenderer<AnimatedImageEntity> {
    protected AnimatedImageEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public abstract ResourceLocation defaultTextureLocation();

    public abstract ResourceLocation[] getTextureList();

    @Override
    public ResourceLocation getTextureLocation(AnimatedImageEntity entity) {
        int index = entity.getCurrentTextureIndex();
        ResourceLocation[] textureList = getTextureList();
        if (textureList.length >= index)
            return defaultTextureLocation();

        return getTextureList()[index];
    }
}
