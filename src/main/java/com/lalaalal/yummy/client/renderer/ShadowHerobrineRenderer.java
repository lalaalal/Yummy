package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.client.layer.ShadowHerobrineArmorLayer;
import com.lalaalal.yummy.client.layer.ShadowHerobrineHeadLayer;
import com.lalaalal.yummy.client.model.AbstractHerobrineModel;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;

public class ShadowHerobrineRenderer extends AbstractHerobrineRenderer<ShadowHerobrine> {
    public ShadowHerobrineRenderer(EntityRendererProvider.Context context) {
        super(context, new AbstractHerobrineModel<>());
        addLayer(new ShadowHerobrineArmorLayer(this));
        addLayer(new ShadowHerobrineHeadLayer(this));
    }

    @Override
    public void render(GeoModel model, ShadowHerobrine animatable, float partialTick, RenderType type, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTick, type, poseStack, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, 0.8f);
    }

    @Override
    public RenderType getRenderType(ShadowHerobrine animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }
}
