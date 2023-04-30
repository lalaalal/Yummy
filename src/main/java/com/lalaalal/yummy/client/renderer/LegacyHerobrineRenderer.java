package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.model.LegacyHerobrineModel;
import com.lalaalal.yummy.entity.LegacyHerobrine;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LegacyHerobrineRenderer extends HumanoidMobRenderer<LegacyHerobrine, LegacyHerobrineModel> {
    private static final ResourceLocation TEXTURE_LOCATION
            = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/herobrine.png");

    public LegacyHerobrineRenderer(EntityRendererProvider.Context context) {
        super(context, new LegacyHerobrineModel(context.bakeLayer(LegacyHerobrineModel.LAYER_LOCATION)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(LegacyHerobrine entity) {
        return TEXTURE_LOCATION;
    }
}
