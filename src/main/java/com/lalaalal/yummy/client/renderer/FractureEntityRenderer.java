package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.entity.FractureEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class FractureEntityRenderer extends EntityRenderer<FractureEntity> {
    private static final Vec3[][] FRACTURE_A = {
            {new Vec3(0, 0, 0), new Vec3(1, 2, 0), new Vec3(1.2, 2, 0.2), new Vec3(0.2, 0, 0.2)},
            {new Vec3(0.2, 0, 0.2), new Vec3(1.2, 2, 0.2), new Vec3(1, 2, 0), new Vec3(0, 0, 0)},
    };

    private static final Vec3[][] FRACTURE_B = {
            {new Vec3(0, 2, 0), new Vec3(0.2, 2, 0.2), new Vec3(1.2, 0, 0.2), new Vec3(1, 0, 0)},
            {new Vec3(1, 0, 0), new Vec3(1.2, 0, 0.2), new Vec3(0.2, 2, 0.2), new Vec3(0, 2, 0)}
    };

    public FractureEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FractureEntity entity) {
        return TheEndPortalRenderer.END_PORTAL_LOCATION;
    }

    @Override
    public void render(FractureEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(1.5f, 1, 1);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(90 - entity.getYRot()));
        Matrix4f pose = poseStack.last().pose();
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.endPortal());
        vertexByShape(vertexConsumer, pose, entity.shape);
        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private void vertexByShape(VertexConsumer vertexConsumer, Matrix4f pose, int shape) {
        switch (shape) {
            case 0 -> {
                vertex(FRACTURE_A, vertexConsumer, pose, Vec3.ZERO);
                vertex(FRACTURE_B, vertexConsumer, pose, new Vec3(1.4, 0, 0));
                vertex(FRACTURE_B, vertexConsumer, pose, new Vec3(-1.4, 0, 0));
            }
            case 2 -> {
                vertex(FRACTURE_B, vertexConsumer, pose, Vec3.ZERO);
                vertex(FRACTURE_A, vertexConsumer, pose, new Vec3(1.4, 0, 0));
                vertex(FRACTURE_A, vertexConsumer, pose, new Vec3(-1.4, 0, 0));
            }
        }
    }

    private void vertex(Vec3[][] fractureVertex, VertexConsumer vertexConsumer, Matrix4f pose, Vec3 offset) {
        for (Vec3[] shape : fractureVertex) {
            for (Vec3 vertex : shape) {
                float x = (float) (vertex.x + offset.x);
                float y = (float) (vertex.y + offset.y);
                float z = (float) (vertex.z + offset.z);
                vertexConsumer.vertex(pose, x, y, z).endVertex();
            }
        }
    }
}
