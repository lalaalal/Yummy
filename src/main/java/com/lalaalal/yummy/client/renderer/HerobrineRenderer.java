package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.client.layer.HerobrineEyeLayer;
import com.lalaalal.yummy.client.model.HerobrineModel;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HerobrineRenderer<T extends Herobrine> extends GeoEntityRenderer<T> {
    public HerobrineRenderer(EntityRendererProvider.Context renderManager) {
        this(renderManager, new HerobrineModel<>());
    }

    public HerobrineRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<T> model) {
        super(renderManager, model);
        this.shadowRadius = 0.3f;
        addLayer(new HerobrineEyeLayer<>(this));
    }
}
