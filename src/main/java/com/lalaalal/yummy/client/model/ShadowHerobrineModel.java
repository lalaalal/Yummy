package com.lalaalal.yummy.client.model;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShadowHerobrineModel extends AnimatedGeoModel<ShadowHerobrine> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/shadow_herobrine.png");

    @Override
    public ResourceLocation getModelResource(ShadowHerobrine object) {
        return HerobrineModel.MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(ShadowHerobrine object) {
        return TEXTURE_LOCATION;
    }

    @Override
    public ResourceLocation getAnimationResource(ShadowHerobrine animatable) {
        return HerobrineModel.ANIMATION_RESOURCE;
    }
}