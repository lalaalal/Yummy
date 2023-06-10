package com.lalaalal.yummy.client.layer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.AbstractHerobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class HerobrineEyeLayer<T extends AbstractHerobrine> extends AutoGlowingGeoLayer<T> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/herobrine_glowing.png");

    public HerobrineEyeLayer(GeoEntityRenderer<T> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, LightTexture.FULL_BRIGHT, packedOverlay);
    }

    @Override
    protected RenderType getRenderType(T animatable) {
        return RenderType.entityTranslucentEmissive(TEXTURE_LOCATION);
    }
}
