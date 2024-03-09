package com.lalaalal.yummy.tags;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;



@SuppressWarnings("unused")
public class YummyTags{
    public static final TagKey<EntityType<?>> HEROBRINE = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(YummyMod.MOD_ID, "herobrine"));
    public static final TagKey<Item> EBONY_LOGS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(YummyMod.MOD_ID, "ebony_logs"));
    public static final TagKey<Block> EBONY_LOGS_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(YummyMod.MOD_ID, "ebony_logs"));

}
