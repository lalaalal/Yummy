package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.model.AbstractHerobrineModel;
import com.lalaalal.yummy.entity.Herobrine;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;

public class HerobrineRenderer extends AbstractHerobrineRenderer<Herobrine> {
    private static final ResourceLocation HEROBRINE_EXPLODING_LOCATION = new ResourceLocation(YummyMod.MOD_ID, "textures/entity/herobrine_exploding.png");
    private static final float HALF_SQRT_3 = (float) (Math.sqrt(3.0D) / 2.0D);

    public HerobrineRenderer(EntityRendererProvider.Context renderManager) {
        this(renderManager, new AbstractHerobrineModel<>());
    }

    public HerobrineRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<Herobrine> model) {
        super(renderManager, model);
    }

    @Override
    public void render(Herobrine animatable, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        if (animatable.getDeathTick() > 0) {
            float tickRate = animatable.getDeathTick() / (float) Herobrine.DEATH_TICK_DURATION;

            renderExploding(poseStack, bufferSource, partialTick, packedLight, tickRate);
            renderLight(poseStack, bufferSource, tickRate);
            return;
        }

        super.render(animatable, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private void renderExploding(PoseStack poseStack, MultiBufferSource bufferSource, float partialTick, int packedLight, float tickRate) {
        GeoModelProvider<Herobrine> modelProvider = getGeoModelProvider();
        GeoModel model = modelProvider.getModel(modelProvider.getModelResource(animatable));

        RenderType explosionAlpha = RenderType.dragonExplosionAlpha(HEROBRINE_EXPLODING_LOCATION);
        VertexConsumer explosionVertexConsumer = bufferSource.getBuffer(explosionAlpha);
        render(model, animatable, partialTick, explosionAlpha, poseStack, bufferSource, explosionVertexConsumer, packedLight, OverlayTexture.pack(0.0F, true), 1f, 1f, 1f, tickRate);

        RenderType decal = RenderType.entityDecal(getTextureLocation(animatable));
        VertexConsumer decalVertexConsumer = bufferSource.getBuffer(decal);
        render(model, animatable, partialTick, decal, poseStack, bufferSource, decalVertexConsumer, packedLight, OverlayTexture.pack(0.0F, true), 1f, 1f, 1f, 1f);

    }

    private void renderLight(PoseStack poseStack, MultiBufferSource bufferSource, float tickRate) {
        float offset = Math.min(tickRate > 0.8F ? (tickRate - 0.8F) / 0.2F : 0.0F, 1.0F);

        RandomSource randomsource = RandomSource.create(432L);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.lightning());
        poseStack.pushPose();
        poseStack.translate(0, 1, 0);

        for (int i = 0; (float) i < (tickRate + tickRate * tickRate) / 2.0F * 60.0F; ++i) {
            poseStack.mulPose(Vector3f.XP.rotationDegrees(randomsource.nextFloat() * 360.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(randomsource.nextFloat() * 360.0F));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(randomsource.nextFloat() * 360.0F));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(randomsource.nextFloat() * 360.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(randomsource.nextFloat() * 360.0F));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(randomsource.nextFloat() * 360.0F + tickRate * 90.0F));
            float randomY = randomsource.nextFloat() * 20.0F + 5.0F + offset * 10.0F;
            float randomFactor = randomsource.nextFloat() * 2.0F + 1.0F + offset * 2.0F;
            Matrix4f matrix4f = poseStack.last().pose();
            int alpha = (int) (255.0F * (1.0F - offset));
            vertex01(vertexConsumer, matrix4f, alpha);
            vertex2(vertexConsumer, matrix4f, randomY, randomFactor);
            vertex3(vertexConsumer, matrix4f, randomY, randomFactor);
            vertex01(vertexConsumer, matrix4f, alpha);
            vertex3(vertexConsumer, matrix4f, randomY, randomFactor);
            vertex4(vertexConsumer, matrix4f, randomY, randomFactor);
            vertex01(vertexConsumer, matrix4f, alpha);
            vertex4(vertexConsumer, matrix4f, randomY, randomFactor);
            vertex2(vertexConsumer, matrix4f, randomY, randomFactor);
        }
        poseStack.popPose();
    }

    private static void vertex01(VertexConsumer vertexConsumer, Matrix4f matrix4f, int alpha) {
        vertexConsumer.vertex(matrix4f, 0.0F, 0.0F, 0.0F).color(255, 255, 255, alpha).endVertex();
    }

    private static void vertex2(VertexConsumer vertexConsumer, Matrix4f matrix4f, float y, float randomFactor) {
        vertexConsumer.vertex(matrix4f, -HALF_SQRT_3 * randomFactor, y, -0.5F * randomFactor).color(255, 0, 255, 0).endVertex();
    }

    private static void vertex3(VertexConsumer vertexConsumer, Matrix4f matrix4f, float y, float randomFactor) {
        vertexConsumer.vertex(matrix4f, HALF_SQRT_3 * randomFactor, y, -0.5F * randomFactor).color(255, 0, 255, 0).endVertex();
    }

    private static void vertex4(VertexConsumer vertexConsumer, Matrix4f matrix4f, float y, float z) {
        vertexConsumer.vertex(matrix4f, 0.0F, y, z).color(255, 0, 255, 0).endVertex();
    }

    @Override
    public RenderType getRenderType(Herobrine animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        if (animatable.getDeathTick() >= Herobrine.DEATH_TICK_DURATION)
            return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
