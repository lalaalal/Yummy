package com.lalaalal.yummy.client.layer;

import com.lalaalal.yummy.client.model.ShadowHerobrineModel;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class ShadowHerobrineLayer extends RenderLayer<ShadowHerobrine, ShadowHerobrineModel> {
    private final ShadowHerobrineModel model;

    public ShadowHerobrineLayer(RenderLayerParent<ShadowHerobrine, ShadowHerobrineModel> pRenderer, EntityModelSet entityModelSet) {
        super(pRenderer);
        this.model = new ShadowHerobrineModel(entityModelSet.bakeLayer(ShadowHerobrineModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, ShadowHerobrine pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(pLivingEntity)));

        this.model.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
        this.model.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, LivingEntityRenderer.getOverlayCoords(pLivingEntity, 0.0F), 1.0F, 1.0F, 1.0F, 0.5F);
    }
}
