package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.model.HerobrineModel;
import com.lalaalal.yummy.client.model.ShadowHerobrineModel;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ShadowHerobrineRenderer extends HumanoidMobRenderer<ShadowHerobrine, ShadowHerobrineModel> {
    private static final ResourceLocation TEXTURE_LOCATION
            = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/shadow_herobrine.png");

    public ShadowHerobrineRenderer(EntityRendererProvider.Context context) {
        super(context, new ShadowHerobrineModel(context.bakeLayer(HerobrineModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public void render(ShadowHerobrine pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ShadowHerobrine entity) {
        return TEXTURE_LOCATION;
    }
}
