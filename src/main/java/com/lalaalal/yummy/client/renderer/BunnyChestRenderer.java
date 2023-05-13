package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.model.BunnyChestModel;
import com.lalaalal.yummy.entity.BunnyChest;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BunnyChestRenderer extends LivingEntityRenderer<BunnyChest, BunnyChestModel> {
    public static final ResourceLocation PINK_TEXTURE_LOCATION = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/pink_bunny_chest.png");

    public BunnyChestRenderer(EntityRendererProvider.Context context) {
        super(context, new BunnyChestModel(context.bakeLayer(BunnyChestModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(BunnyChest entity) {
        return switch (entity.color) {
            case PINK -> PINK_TEXTURE_LOCATION;
        };
    }

    @Override
    protected void renderNameTag(BunnyChest entity, Component displayName, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        if (entity.hasCustomName()) {
            matrixStack.pushPose();
            matrixStack.translate(0, 0.4, 0);
            super.renderNameTag(entity, displayName, matrixStack, buffer, packedLight);
            matrixStack.popPose();
        }
    }
}
