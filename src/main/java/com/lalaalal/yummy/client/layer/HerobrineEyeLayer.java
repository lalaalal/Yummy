package com.lalaalal.yummy.client.layer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.Herobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.LayerGlowingAreasGeo;

public class HerobrineEyeLayer<T extends Herobrine> extends LayerGlowingAreasGeo<T> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/herobrine.png");

    public HerobrineEyeLayer(GeoEntityRenderer<T> renderer) {
        super(renderer, (entity) -> TEXTURE_LOCATION, renderer.getGeoModelProvider()::getModelResource, RenderType::entityTranslucentEmissive);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T animatable, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        super.render(poseStack, bufferSource, LightTexture.FULL_BRIGHT, animatable, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
    }
}
