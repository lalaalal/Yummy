package com.lalaalal.yummy.client.renderer;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.client.model.ThrownMightyHolySpearModel;
import com.lalaalal.yummy.client.model.ThrownSpearModel;
import com.lalaalal.yummy.client.model.ThrownSpearOfLonginusModel;
import com.lalaalal.yummy.entity.ThrownSpear;
import com.lalaalal.yummy.item.YummyItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class YummyBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    private static final Map<Item, ResourceLocation> MODEL_TEXTURE_LOCATIONS = Map.of(
            YummyItems.SPEAR.get(), ThrownSpearModel.TEXTURE_LOCATION,
            YummyItems.MIGHTY_HOLY_SPEAR.get(), ThrownMightyHolySpearModel.TEXTURE_LOCATION,
            YummyItems.SPEAR_OF_LONGINUS.get(), ThrownSpearOfLonginusModel.TEXTURE_LOCATION
    );
    private static final Map<Item, ModelResourceLocation> MODEL_RESOURCE_LOCATIONS = Map.of(
            YummyItems.SPEAR.get(), new ModelResourceLocation(YummyMod.MOD_ID, "spear_inventory", "inventory"),
            YummyItems.MIGHTY_HOLY_SPEAR.get(), new ModelResourceLocation(YummyMod.MOD_ID, "mighty_holy_spear_inventory", "inventory"),
            YummyItems.SPEAR_OF_LONGINUS.get(), new ModelResourceLocation(YummyMod.MOD_ID, "spear_of_longinus_inventory", "inventory")
    );

    private static YummyBlockEntityWithoutLevelRenderer INSTANCE;

    private Map<Item, EntityModel<? extends ThrownSpear>> modelMap = Map.of();
    private final EntityModelSet entityModelSet;
    private final ItemRenderer itemRenderer;
    private final ItemModelShaper itemModelShaper;

    public static YummyBlockEntityWithoutLevelRenderer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new YummyBlockEntityWithoutLevelRenderer(Minecraft.getInstance());
        return INSTANCE;
    }

    private YummyBlockEntityWithoutLevelRenderer(Minecraft minecraft) {
        super(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels());
        this.entityModelSet = minecraft.getEntityModels();
        this.itemRenderer = minecraft.getItemRenderer();
        this.itemModelShaper = itemRenderer.getItemModelShaper();
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        EntityModel<ThrownSpear> thrownSpearModel = new ThrownSpearModel<>(entityModelSet.bakeLayer(ThrownSpearModel.LAYER_LOCATION));
        EntityModel<ThrownSpear> thrownMightyHolySpearModel = new ThrownMightyHolySpearModel<>(entityModelSet.bakeLayer(ThrownMightyHolySpearModel.LAYER_LOCATION));
        EntityModel<? extends ThrownSpear> thrownSpearOfLonginusModel = new ThrownSpearOfLonginusModel(entityModelSet.bakeLayer(ThrownSpearOfLonginusModel.LAYER_LOCATION));
        modelMap = Map.of(
                YummyItems.SPEAR.get(), thrownSpearModel,
                YummyItems.MIGHTY_HOLY_SPEAR.get(), thrownMightyHolySpearModel,
                YummyItems.SPEAR_OF_LONGINUS.get(), thrownSpearOfLonginusModel
        );
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        boolean renderAsItem = transformType == ItemTransforms.TransformType.GUI || transformType == ItemTransforms.TransformType.GROUND || transformType == ItemTransforms.TransformType.FIXED;
        if (renderAsItem) {
            renderSpearAsItem(itemStack, transformType, poseStack, buffer, packedLight, packedOverlay);
        } else {
            renderSpearInHand(itemStack, transformType, poseStack, buffer, packedLight, packedOverlay);
        }
    }

    private void renderSpearAsItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Item item = itemStack.getItem();
        if (MODEL_RESOURCE_LOCATIONS.containsKey(item)) {
            ModelResourceLocation modelResourceLocation = MODEL_RESOURCE_LOCATIONS.get(item);
            BakedModel model = this.itemModelShaper.getModelManager().getModel(modelResourceLocation);
            itemRenderer.render(itemStack, transformType, false, poseStack, buffer, packedLight, packedOverlay, model);
        }
    }

    private void renderSpearInHand(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Item item = itemStack.getItem();
        if (MODEL_TEXTURE_LOCATIONS.containsKey(item)) {
            EntityModel<? extends ThrownSpear> spearModel = modelMap.get(item);
            ResourceLocation textureLocation = MODEL_TEXTURE_LOCATIONS.get(item);

            poseStack.pushPose();
            Player player = Minecraft.getInstance().player;
            boolean notFirstPerson = transformType != ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND && transformType != ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;
            if (player != null && player.isUsingItem() && notFirstPerson) {
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
                poseStack.translate(0, 1, 0);
            }
            poseStack.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(buffer, spearModel.renderType(textureLocation), false, itemStack.hasFoil());
            spearModel.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
            poseStack.popPose();
        }
    }
}
