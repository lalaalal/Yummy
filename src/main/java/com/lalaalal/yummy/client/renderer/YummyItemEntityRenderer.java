package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class YummyItemEntityRenderer<T extends Entity> extends CameraFollowingImageEntityRenderer<T> {
    private final String textureName;
    private final float scale;

    public YummyItemEntityRenderer(EntityRendererProvider.Context context, String textureName, float scale) {
        super(context);
        this.textureName = textureName;
        this.scale = scale;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public RenderType renderType(T entity) {
        return RenderType.entityCutoutNoCull(getTextureLocation());
    }

    @Override
    public ResourceLocation getTextureLocation(Entity pEntity) {
        return getTextureLocation();
    }

    public ResourceLocation getTextureLocation() {
        String pathPattern = "textures/entity/%s.png";

        return new ResourceLocation(YummyMod.MOD_ID, String.format(pathPattern, textureName));
    }
}
