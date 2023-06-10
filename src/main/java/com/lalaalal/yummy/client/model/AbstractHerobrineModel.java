package com.lalaalal.yummy.client.model;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.AbstractHerobrine;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AbstractHerobrineModel<T extends AbstractHerobrine> extends GeoModel<T> {
    public static final ResourceLocation MODEL_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "geo/herobrine.geo.json");
    public static final ResourceLocation SHADOW_MODEL_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "geo/shadow_herobrine.geo.json");
    public static final ResourceLocation TEXTURE_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/herobrine.png");
    public static final ResourceLocation SHADOW_TEXTURE_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/shadow_herobrine.png");
    public static final ResourceLocation ANIMATION_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "animations/herobrine.animation.json");
    public static final ResourceLocation SHADOW_ANIMATION_RESOURCE = new ResourceLocation(YummyMod.MOD_ID, "animations/shadow_herobrine.animation.json");

    @Override
    public final ResourceLocation getModelResource(T object) {
        return object.isShadow ? SHADOW_MODEL_RESOURCE : MODEL_RESOURCE;
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return object.isShadow ? SHADOW_TEXTURE_RESOURCE : TEXTURE_RESOURCE;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animatable.isShadow ? SHADOW_ANIMATION_RESOURCE : ANIMATION_RESOURCE;
    }
}
