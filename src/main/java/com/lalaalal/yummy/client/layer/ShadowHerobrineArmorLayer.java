package com.lalaalal.yummy.client.layer;

import com.lalaalal.yummy.client.model.ShadowHerobrineModel;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class ShadowHerobrineArmorLayer extends GeoLayerRenderer<ShadowHerobrine> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");

    public ShadowHerobrineArmorLayer(GeoEntityRenderer<ShadowHerobrine> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, ShadowHerobrine entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStackIn.pushPose();
        matrixStackIn.scale(1.01f, 1.01f, 1.01f);
        float offset = ((entityLivingBaseIn.tickCount + partialTicks) * 0.01f) % 1;
        RenderType renderType = RenderType.energySwirl(TEXTURE_LOCATION, offset, offset);
        getRenderer().render(getEntityModel().getModel(ShadowHerobrineModel.MODEL_RESOURCE), entityLivingBaseIn, partialTicks, renderType, matrixStackIn, bufferIn, bufferIn.getBuffer(renderType), packedLightIn, OverlayTexture.NO_OVERLAY, 0.5f, 0.5f, 0.5f, 1.0f);
        matrixStackIn.popPose();
    }

    @Override
    public RenderType getRenderType(ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
