package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.models.HerobrineEntityModel;
import com.lalaalal.yummy.entity.HerobrineEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HerobrineEntityRenderer extends HumanoidMobRenderer<HerobrineEntity, HerobrineEntityModel> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(YummyMod.MOD_ID, "textures/entities/herobrine.png");

    public HerobrineEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new HerobrineEntityModel(context.bakeLayer(HerobrineEntityModel.LAYER_LOCATION)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HerobrineEntity entity) {
        return TEXTURE_LOCATION;
    }
}
