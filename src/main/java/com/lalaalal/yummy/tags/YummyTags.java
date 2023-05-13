package com.lalaalal.yummy.tags;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("unused")
public class YummyTags {
    public static final TagKey<EntityType<?>> HEROBRINE = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(YummyMod.MOD_ID, "herobrine"));
    public static final TagKey<Item> EBONY_LOGS_ITEM_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(YummyMod.MOD_ID, "ebony_logs"));
    public static final TagKey<Block> EBONY_LOGS_BLOCK_TAG = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(YummyMod.MOD_ID, "ebony_logs"));
    public static final TagKey<Item> STEEL_ARMORS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(YummyMod.MOD_ID, "steel_armors"));
}
