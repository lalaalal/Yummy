package com.lalaalal.yummy.client.layer;

import com.lalaalal.yummy.client.model.ShadowHerobrineModel;
import com.lalaalal.yummy.entity.ShadowHerobrine;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;

public class ShadowHerobrineArmorLayer extends EnergySwirlLayer<ShadowHerobrine, ShadowHerobrineModel> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/creeper/creeper_armor.png");

    private final ShadowHerobrineModel model;

    public ShadowHerobrineArmorLayer(RenderLayerParent<ShadowHerobrine, ShadowHerobrineModel> pRenderer, EntityModelSet entityModelSet) {
        super(pRenderer);
        this.model = new ShadowHerobrineModel(entityModelSet.bakeLayer(ShadowHerobrineModel.LAYER_LOCATION));
    }

    @Override
    protected float xOffset(float offset) {
        return offset * 0.01f;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return TEXTURE_LOCATION;
    }

    @Override
    protected EntityModel<ShadowHerobrine> model() {
        return model;
    }
}
