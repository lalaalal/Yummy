package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.FractureExplosion;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FractureExplosionRenderer extends CameraFollowingImageEntityRenderer<FractureExplosion> {
    private static final ResourceLocation[] TEXTURE_LOCATIONS = new ResourceLocation[7];

    static {
        for (int i = 0; i < TEXTURE_LOCATIONS.length; i++)
            TEXTURE_LOCATIONS[i] = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/fracture_explosion/fracture_explosion_" + (i + 1) + ".png");
    }

    public FractureExplosionRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(FractureExplosion entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float scale = entity.getScale();
        poseStack.scale(scale, scale, scale);
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    @Override
    public RenderType renderType(FractureExplosion entity) {
        return RenderType.entityCutoutNoCull(getTextureLocation(entity));
    }

    @Override
    public ResourceLocation getTextureLocation(FractureExplosion entity) {
        return TEXTURE_LOCATIONS[entity.getCurrentFrame()];
    }
}
