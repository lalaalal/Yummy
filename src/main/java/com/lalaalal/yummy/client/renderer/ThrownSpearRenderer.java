package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.model.ThrownSpearModel;
import com.lalaalal.yummy.entity.ThrownSpear;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.function.Supplier;

public class ThrownSpearRenderer<T extends ThrownSpear> extends EntityRenderer<T> {
    private final ResourceLocation textureLocation;
    private final EntityModel<? extends ThrownSpear> model;

    public ThrownSpearRenderer(EntityRendererProvider.Context context) {
        this(context, "spear", () -> new ThrownSpearModel<>(context.bakeLayer(ThrownSpearModel.LAYER_LOCATION)));
    }

    public ThrownSpearRenderer(EntityRendererProvider.Context context, String name, Supplier<EntityModel<? extends ThrownSpear>> modelSupplier) {
        super(context);
        String path = String.format("textures/entity/%s.png", name);
        this.textureLocation = new ResourceLocation(YummyMod.MOD_ID, path);
        this.model = modelSupplier.get();
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();
        matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.getTextureLocation(entity)), false, entity.hasFoil());
        this.model.renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
        super.render(entity, entityYaw, partialTick, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return textureLocation;
    }

}
