package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.model.MeteorModel;
import com.lalaalal.yummy.entity.Meteor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MeteorRenderer extends EntityRenderer<Meteor> {
    public static final ResourceLocation DEFAULT_TEXTURE_LOCATION
            = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/meteor.png");
    public static final ResourceLocation NARAKA_TEXTURE_LOCATION
            = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/naraka_meteor.png");

    private final MeteorModel model;

    public MeteorRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new MeteorModel(context.bakeLayer(MeteorModel.LAYER_LOCATION));
    }

    @Override
    public void render(Meteor pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(0, -1.2, 0);
        pMatrixStack.mulPose(Vector3f.YN.rotationDegrees(pEntity.getRotationDegree()));
        VertexConsumer vertexConsumer = pBuffer.getBuffer(model.renderType(getTextureLocation(pEntity)));
        this.model.renderToBuffer(pMatrixStack, vertexConsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Meteor entity) {
        return entity.isMarkEntities() ? NARAKA_TEXTURE_LOCATION : DEFAULT_TEXTURE_LOCATION;
    }
}
