package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.client.layer.HerobrineEyeLayer;
import com.lalaalal.yummy.entity.AbstractHerobrine;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AbstractHerobrineRenderer<T extends AbstractHerobrine> extends GeoEntityRenderer<T> {
    public AbstractHerobrineRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
        this.shadowRadius = 0.3f;
        addRenderLayer(new HerobrineEyeLayer<>(this));
    }
}
