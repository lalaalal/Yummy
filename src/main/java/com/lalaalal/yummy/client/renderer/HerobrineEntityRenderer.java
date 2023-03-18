package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.models.HerobrineEntityModel;
import com.lalaalal.yummy.entity.HerobrineEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class HerobrineEntityRenderer extends MobRenderer<HerobrineEntity, HerobrineEntityModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(YummyMod.MOD_ID, "textures/entities/herobrine.png");

    public HerobrineEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new HerobrineEntityModel(context.bakeLayer(HerobrineEntityModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HerobrineEntity entity) {
        return TEXTURE;
    }
}
