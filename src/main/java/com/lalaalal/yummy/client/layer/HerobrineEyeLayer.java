package com.lalaalal.yummy.client.layer;

import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.LayerGlowingAreasGeo;

import java.util.function.Function;

public class HerobrineEyeLayer extends LayerGlowingAreasGeo<Herobrine> {
    public HerobrineEyeLayer(GeoEntityRenderer<Herobrine> renderer, Function<Herobrine, ResourceLocation> currentTextureFunction, Function<Herobrine, ResourceLocation> currentModelFunction) {
        super(renderer, currentTextureFunction, currentModelFunction, RenderType::entityTranslucentEmissive);
    }
}
