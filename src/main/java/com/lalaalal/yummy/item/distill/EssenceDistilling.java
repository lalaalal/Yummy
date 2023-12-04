package com.lalaalal.yummy.item.distill;

import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class EssenceDistilling {
    private static final Map<Item, DistillingEntry> DISTILLING_MAP = new HashMap<>();

    public static void init() {
        addDistillRecipe(Items.GOLD_BLOCK, 16, YummyItems.SOUL_INFUSED_GOLD.get());
        addDistillRecipe(Items.REDSTONE_BLOCK, 32, YummyItems.SOUL_INFUSED_REDSTONE.get());
        addDistillRecipe(Items.MAGMA_BLOCK, 32, YummyItems.SOUL_INFUSED_COPPER.get());
        addDistillRecipe(Items.BLUE_ICE, 32, YummyItems.SOUL_INFUSED_DIAMOND.get());
        addDistillRecipe(Items.MOSS_BLOCK, 64, YummyItems.SOUL_INFUSED_EMERALD.get());
        addDistillRecipe(Items.AMETHYST_BLOCK, 64, YummyItems.SOUL_INFUSED_AMETHYST.get());
        addDistillRecipe(Items.HEART_OF_THE_SEA, 1, YummyItems.SOUL_INFUSED_LAPIS.get());
        addDistillRecipe(Items.GOAT_HORN, 1, YummyItems.SOUL_INFUSED_FANCY_DIAMOND.get());
    }

    public static void addDistillRecipe(Item ingredient, int requiredCount, Item result) {
        DISTILLING_MAP.put(ingredient, new DistillingEntry(result, requiredCount));
    }

    public static boolean isIngredient(Item item) {
        return DISTILLING_MAP.containsKey(item);
    }

    public static int getRequiredCount(Item ingredient) {
        return DISTILLING_MAP.getOrDefault(ingredient, DistillingEntry.EMPTY).requiredCount;
    }

    public static Item getResult(Item ingredient) {
        return DISTILLING_MAP.getOrDefault(ingredient, DistillingEntry.EMPTY).result;
    }

    private record DistillingEntry(Item result, int requiredCount) {
        public static final DistillingEntry EMPTY = new DistillingEntry(Items.AIR, 0);
    }
}
