package com.lalaalal.yummy.client.model;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.BunnyChest;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class BunnyChestModel extends EntityModel<BunnyChest> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(YummyMod.MOD_ID, "bunny_chest"), "main");
    public final ModelPart body;
    public final ModelPart head;

    public BunnyChestModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(30, 53).addBox(-7.0F, -10.0F, -8.0F, 14.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 53).addBox(-7.0F, -10.0F, 7.0F, 14.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 30).addBox(7.0F, -10.0F, -7.0F, 1.0F, 9.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-8.0F, -10.0F, -7.0F, 1.0F, 9.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(8, 30).addBox(-1.0F, -10.0F, -9.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 30).addBox(-1.0F, -4.0F, 8.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -15.0F, 14.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(58, 0).addBox(-7.0F, -5.0F, -1.0F, 14.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(46, 20).addBox(7.0F, -5.0F, -15.0F, 1.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(42, 1).addBox(-8.0F, -5.0F, -15.0F, 1.0F, 5.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(58, 6).addBox(-7.0F, -5.0F, -16.0F, 14.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-1.0F, -1.0F, -17.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 8.0F));

        PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create().texOffs(0, 0).addBox(2.0F, -27.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 22).addBox(-5.0F, -22.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, -6.0F));

        PartDefinition cube_r1 = ears.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 15).addBox(-3.0F, -5.0F, -2.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -22.0F, 1.0F, 0.48F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(BunnyChest entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}