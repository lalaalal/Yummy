package com.lalaalal.yummy.client.layer;

import com.lalaalal.yummy.entity.Herobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.LayerGlowingAreasGeo;

import java.util.function.Function;

public class HerobrineEyeLayer extends LayerGlowingAreasGeo<Herobrine> {
    public HerobrineEyeLayer(GeoEntityRenderer<Herobrine> renderer, Function<Herobrine, ResourceLocation> currentTextureFunction, Function<Herobrine, ResourceLocation> currentModelFunction) {
        super(renderer, currentTextureFunction, currentModelFunction, RenderType::entityTranslucentEmissive);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Herobrine animatable, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        super.render(poseStack, bufferSource, LightTexture.FULL_BRIGHT, animatable, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch);
    }
}
