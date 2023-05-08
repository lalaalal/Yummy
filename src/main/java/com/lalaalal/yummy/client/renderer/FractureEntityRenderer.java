package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.entity.FractureEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FractureEntityRenderer extends EntityRenderer<FractureEntity> {
    public FractureEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FractureEntity entity) {
        return TheEndPortalRenderer.END_PORTAL_LOCATION;
    }

    @Override
    public void render(FractureEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Matrix4f pose = poseStack.last().pose();
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.endPortal());
        vertexConsumer.vertex(pose, 0, 0, 0).endVertex();
        vertexConsumer.vertex(pose, 0, 0, 0).endVertex();
        vertexConsumer.vertex(pose, 5, 3, -2).endVertex();
        vertexConsumer.vertex(pose, 0, 3, 0).endVertex();

        vertexConsumer.vertex(pose, 0, 3, 0).endVertex();
        vertexConsumer.vertex(pose, 5, 3, -2).endVertex();
        vertexConsumer.vertex(pose, 0, 0, 0).endVertex();
        vertexConsumer.vertex(pose, 0, 0, 0).endVertex();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }
}
