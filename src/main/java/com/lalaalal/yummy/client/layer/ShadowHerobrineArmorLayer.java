package com.lalaalal.yummy.client.layer;

import com.lalaalal.yummy.client.renderer.ShadowHerobrineRenderer;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ShadowHerobrineArmorLayer extends GeoRenderLayer<ShadowHerobrine> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");

    public ShadowHerobrineArmorLayer(ShadowHerobrineRenderer renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, ShadowHerobrine animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.scale(1.01f, 1.01f, 1.01f);
        float offset = ((animatable.tickCount + partialTick) * 0.01f) % 1;
        RenderType energySwirlRenderType = RenderType.energySwirl(TEXTURE_LOCATION, offset, offset);
        ResourceLocation modelResource = getGeoModel().getModelResource(animatable);
        getRenderer().actuallyRender(poseStack, animatable, getGeoModel().getBakedModel(modelResource), energySwirlRenderType, bufferSource, bufferSource.getBuffer(energySwirlRenderType), false, partialTick, packedLight, OverlayTexture.NO_OVERLAY, 0.5f, 0.5f, 0.5f, 1.0f);
        poseStack.popPose();
    }
}
