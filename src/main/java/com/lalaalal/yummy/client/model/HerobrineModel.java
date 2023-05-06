package com.lalaalal.yummy.client.model;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HerobrineModel<T extends Herobrine> extends AnimatedGeoModel<T> {
    public static final ResourceLocation MODEL_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "geo/herobrine.geo.json");
    public static final ResourceLocation TEXTURE_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/herobrine.png");
    public static final ResourceLocation ANIMATION_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "animations/herobrine.animation.json");

    @Override
    public final ResourceLocation getModelResource(T object) {
        return MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return TEXTURE_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return ANIMATION_RESOURCE;
    }
}
