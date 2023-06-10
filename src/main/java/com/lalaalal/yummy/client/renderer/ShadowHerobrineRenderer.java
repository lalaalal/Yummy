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
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class ShadowHerobrineRenderer extends AbstractHerobrineRenderer<ShadowHerobrine> {
    public ShadowHerobrineRenderer(EntityRendererProvider.Context context) {
        super(context, new AbstractHerobrineModel<>());
        addRenderLayer(new ShadowHerobrineArmorLayer(this));
        addRenderLayer(new ShadowHerobrineHeadLayer(this));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, ShadowHerobrine animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, 0.7f);
    }

    @Override
    public RenderType getRenderType(ShadowHerobrine animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
