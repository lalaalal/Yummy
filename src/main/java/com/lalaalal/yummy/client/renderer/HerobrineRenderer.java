package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.client.layer.HerobrineEyeLayer;
import com.lalaalal.yummy.client.model.HerobrineModel;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HerobrineRenderer extends GeoEntityRenderer<Herobrine> {
    public HerobrineRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HerobrineModel());
        this.shadowRadius = 0.3f;
        addLayer(new HerobrineEyeLayer(this, this::getTextureLocation, modelProvider::getModelResource));
    }
}
