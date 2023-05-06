package com.lalaalal.yummy.client.layer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.AbstractLayerGeo;

public class ShadowHerobrineHeadLayer extends AbstractLayerGeo<ShadowHerobrine> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/shadow_herobrine_head.png");

    public ShadowHerobrineHeadLayer(GeoEntityRenderer<ShadowHerobrine> renderer) {
        super(renderer, (entity) -> TEXTURE_LOCATION, renderer.getGeoModelProvider()::getModelResource);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, ShadowHerobrine entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ResourceLocation textureLocation = funcGetCurrentTexture.apply(entityLivingBaseIn);
        reRenderCurrentModelInRenderer(entityLivingBaseIn, partialTicks, matrixStackIn, bufferIn, packedLightIn, getRenderType(textureLocation));
    }
}
