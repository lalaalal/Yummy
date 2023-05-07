package com.lalaalal.yummy.client.model;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HerobrineModel extends AnimatedGeoModel<Herobrine> {
    public static final ResourceLocation MODEL_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "geo/herobrine.geo.json");
    public static final ResourceLocation TEXTURE_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/herobrine.png");
    public static final ResourceLocation ANIMATION_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "animations/herobrine.animation.json");

    @Override
    public final ResourceLocation getModelResource(Herobrine object) {
        return MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(Herobrine object) {
        return TEXTURE_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(Herobrine animatable) {
        return ANIMATION_RESOURCE;
    }
}
