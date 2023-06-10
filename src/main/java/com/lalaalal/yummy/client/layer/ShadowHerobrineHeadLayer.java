package com.lalaalal.yummy.client.layer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.renderer.ShadowHerobrineRenderer;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class ShadowHerobrineHeadLayer extends GeoRenderLayer<ShadowHerobrine> {
    private static final ResourceLocation HEAD_TEXTURE_LOCATION = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/shadow_herobrine_head.png");

    public ShadowHerobrineHeadLayer(ShadowHerobrineRenderer renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, ShadowHerobrine animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType headRenderType = RenderType.entityTranslucent(HEAD_TEXTURE_LOCATION);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(headRenderType);
        getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, headRenderType, vertexConsumer, partialTick, packedLight, packedOverlay, 1f, 1f, 1f, 1f);
    }
}
