package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.models.HerobrineModel;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HerobrineRenderer extends HumanoidMobRenderer<Herobrine, HerobrineModel> {
    private static final ResourceLocation TEXTURE_LOCATION
            = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/herobrine.png");

    public HerobrineRenderer(EntityRendererProvider.Context context) {
        super(context, new HerobrineModel(context.bakeLayer(HerobrineModel.LAYER_LOCATION)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Herobrine entity) {
        return TEXTURE_LOCATION;
    }
}
