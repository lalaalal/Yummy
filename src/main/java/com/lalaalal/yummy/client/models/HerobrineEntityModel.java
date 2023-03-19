package com.lalaalal.yummy.client.models;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.entity.HerobrineEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;

public class HerobrineEntityModel extends HumanoidModel<HerobrineEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(YummyMod.MOD_ID, "herobrine"), "main");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = createMesh(new CubeDeformation(0.0f), 0);
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public HerobrineEntityModel(ModelPart pRoot) {
        super(pRoot);
    }
}