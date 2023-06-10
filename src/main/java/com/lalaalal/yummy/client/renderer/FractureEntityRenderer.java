package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.YummyUtil;
import com.lalaalal.yummy.entity.FractureEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class FractureEntityRenderer extends EntityRenderer<FractureEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/fracture.png");

    public FractureEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FractureEntity entity) {
        return TEXTURE_LOCATION;
    }

    @Override
    public void render(FractureEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(4, 4, 6);
        poseStack.translate(0, -0.4, 0.1);
        poseStack.mulPose(Axis.YP.rotationDegrees(90 - entity.getYRot()));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getRotateDegree()));
        Matrix4f pose = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
        YummyUtil.vertex(vertexConsumer, pose, normal, 0, 0, 0, 0, 1, 1, 1, 0, packedLight);
        YummyUtil.vertex(vertexConsumer, pose, normal, 0, 1, 0, 1, 1, 1, 1, 0, packedLight);
        YummyUtil.vertex(vertexConsumer, pose, normal, 1, 1, 0, 1, 0, 1, 1, 0, packedLight);
        YummyUtil.vertex(vertexConsumer, pose, normal, 1, 0, 0, 0, 0, 1, 1, 0, packedLight);
        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }
}
