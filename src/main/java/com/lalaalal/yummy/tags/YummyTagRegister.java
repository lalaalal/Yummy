package com.lalaalal.yummy.tags;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class YummyTagRegister {
    public static final TagKey<EntityType<?>> HEROBRINE = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(YummyMod.MOD_ID, "herobrine"));

}
