package com.lalaalal.yummy.client.models;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.Herobrine;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HerobrineModel extends HumanoidModel<Herobrine> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(YummyMod.MOD_ID, "herobrine"), "main");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = createMesh(new CubeDeformation(0.0f), 0);
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public HerobrineModel(ModelPart pRoot) {
        super(pRoot);
    }

    @Override
    public void setupAnim(Herobrine herobrine, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(herobrine, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (herobrine.getArmPose() == Herobrine.ArmPose.RAISE_BOTH) {
            this.rightArm.z = 0.0F;
            this.rightArm.x = -5.0F;
            this.leftArm.z = 0.0F;
            this.leftArm.x = 5.0F;
            this.rightArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
            this.leftArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
            this.rightArm.zRot = 2.3561945F;
            this.leftArm.zRot = -2.3561945F;
            this.rightArm.yRot = 0.0F;
            this.leftArm.yRot = 0.0F;
        }
    }
}