package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.model.ThrownSpearOfLonginusModel;
import com.lalaalal.yummy.entity.ThrownSpearOfLonginus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ThrownSpearOfLonginusRenderer extends EntityRenderer<ThrownSpearOfLonginus> {
    public static final ResourceLocation TEXTURE_LOCATION
            = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/thrown_spear_of_longinus.png");
    private final ThrownSpearOfLonginusModel model;

    public ThrownSpearOfLonginusRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new ThrownSpearOfLonginusModel(context.bakeLayer(ThrownSpearOfLonginusModel.LAYER_LOCATION));
    }

    @Override
    public void render(ThrownSpearOfLonginus entity, float entityYaw, float partialTick, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTick, entity.yRotO, entity.getYRot()) - 90.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTick, entity.xRotO, entity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(this.getTextureLocation(entity)), false, false);
        this.model.renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
        super.render(entity, entityYaw, partialTick, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownSpearOfLonginus entity) {
        return TEXTURE_LOCATION;
    }

}
