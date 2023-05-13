package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.client.layer.HerobrineEyeLayer;
import com.lalaalal.yummy.entity.AbstractHerobrine;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AbstractHerobrineRenderer<T extends AbstractHerobrine> extends GeoEntityRenderer<T> {
    public AbstractHerobrineRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
        this.shadowRadius = 0.3f;
        addLayer(new HerobrineEyeLayer<>(this));
    }
}
