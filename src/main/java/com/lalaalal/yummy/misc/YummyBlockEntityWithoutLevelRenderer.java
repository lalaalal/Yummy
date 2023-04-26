package com.lalaalal.yummy.misc;

import com.lalaalal.yummy.client.model.ThrownSpearModel;
import com.lalaalal.yummy.entity.ThrownSpear;
import com.lalaalal.yummy.item.YummyItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ForgeItemModelShaper;

@OnlyIn(Dist.CLIENT)
public class YummyBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    private ThrownSpearModel<ThrownSpear> thrownSpearModel;
    private final EntityModelSet entityModelSet;
    private ItemModelShaper itemModelShaper;
    private ItemRenderer itemRenderer;

    private static YummyBlockEntityWithoutLevelRenderer INSTANCE;

    public static YummyBlockEntityWithoutLevelRenderer getInstance() {
        if (INSTANCE == null) {
            Minecraft minecraft = Minecraft.getInstance();
            ModelManager modelManager = minecraft.getModelManager();
            INSTANCE = new YummyBlockEntityWithoutLevelRenderer(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels());
            INSTANCE.itemModelShaper = new ForgeItemModelShaper(modelManager);
            INSTANCE.itemRenderer = minecraft.getItemRenderer();
            INSTANCE.onResourceManagerReload(minecraft.getResourceManager());
        }
        return INSTANCE;
    }

    public YummyBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(pBlockEntityRenderDispatcher, entityModelSet);
        this.entityModelSet = entityModelSet;
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourcemanager) {
        super.onResourceManagerReload(resourcemanager);
        thrownSpearModel = new ThrownSpearModel<>(entityModelSet.bakeLayer(ThrownSpearModel.LAYER_LOCATION));
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        super.renderByItem(pStack, pTransformType, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
        boolean flag = pTransformType == ItemTransforms.TransformType.GUI || pTransformType == ItemTransforms.TransformType.GROUND || pTransformType == ItemTransforms.TransformType.FIXED;
        BakedModel model;
        if (flag) {
            if (pStack.is(YummyItems.SPEAR.get())) {
                model = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("yummy:spear_inventory#inventory"));
                itemRenderer.render(pStack, pTransformType, false, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, model);
            }
        } else {
            if (pStack.is(YummyItems.SPEAR.get())) {
                pPoseStack.pushPose();
                pPoseStack.scale(1.0F, -1.0F, -1.0F);
                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.thrownSpearModel.renderType(ThrownSpearModel.TEXTURE_LOCATION), false, pStack.hasFoil());
                this.thrownSpearModel.renderToBuffer(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                pPoseStack.popPose();
            }
        }
    }
}
