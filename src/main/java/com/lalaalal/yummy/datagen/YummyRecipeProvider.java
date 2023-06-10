package com.lalaalal.yummy.datagen;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.item.YummyItems;
import com.lalaalal.yummy.tags.YummyTags;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class YummyRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public YummyRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, Items.AMETHYST_SHARD, RecipeCategory.BUILDING_BLOCKS, YummyBlocks.AMETHYST_BLOCK.get());
        generateRecipes(consumer, new BlockFamily.Builder(YummyBlocks.EBONY_PLANKS.get())
                .button(YummyBlocks.EBONY_BUTTON.get())
                .door(YummyBlocks.EBONY_DOOR.get())
                .fence(YummyBlocks.EBONY_FENCE.get())
                .fenceGate(YummyBlocks.EBONY_FENCE_GATE.get())
                .pressurePlate(YummyBlocks.EBONY_PRESSURE_PLATE.get())
                .sign(YummyBlocks.EBONY_SIGN.get(), YummyBlocks.EBONY_WALL_SIGN.get())
                .slab(YummyBlocks.EBONY_SLAB.get())
                .stairs(YummyBlocks.EBONY_STAIRS.get())
                .trapdoor(YummyBlocks.EBONY_TRAPDOOR.get())
                .getFamily());
        cut(consumer, RecipeCategory.BUILDING_BLOCKS, YummyBlocks.HARD_EBONY_PLANKS.get(), YummyBlocks.EBONY_WOOD.get());
        essenceSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.ESSENCE_OF_EARTH.get(), RecipeCategory.MISC, YummyItems.SWORD_OF_EARTH.get());
        essenceSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.ESSENCE_OF_ELECTRICITY.get(), RecipeCategory.MISC, YummyItems.SWORD_OF_ELECTRICITY.get());
        essenceSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.ESSENCE_OF_FIRE.get(), RecipeCategory.MISC, YummyItems.SWORD_OF_FIRE.get());
        essenceSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.ESSENCE_OF_ICE.get(), RecipeCategory.MISC, YummyItems.SWORD_OF_ICE.get());
        essenceSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.ESSENCE_OF_LIFE.get(), RecipeCategory.MISC, YummyItems.SWORD_OF_LIFE.get());
        essenceSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.ESSENCE_OF_SOUND.get(), RecipeCategory.MISC, YummyItems.SWORD_OF_SOUND.get());
        essenceSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.ESSENCE_OF_WATER.get(), RecipeCategory.MISC, YummyItems.SWORD_OF_WATER.get());
        essenceSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.ESSENCE_OF_WIND.get(), RecipeCategory.MISC, YummyItems.SWORD_OF_WIND.get());
        planksFromLog(consumer, YummyBlocks.EBONY_LOG.get(), YummyTags.EBONY_LOGS_ITEM_TAG, 4);
        woodFromLogs(consumer, YummyBlocks.EBONY_WOOD.get(), YummyBlocks.EBONY_LOG.get());
        woodenBoat(consumer, YummyItems.EBONY_BOAT_ITEM.get(), YummyBlocks.EBONY_PLANKS.get());
        chestBoat(consumer, YummyItems.EBONY_CHEST_BOAT_ITEM.get(), YummyBlocks.EBONY_PLANKS.get());
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpackedCategory, pUnpacked, pPackedCategory, pPacked, getSimpleRecipeName(pPacked), null, getSimpleRecipeName(pUnpacked), null);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked, String pPackedName, @Nullable String pPackedGroup, String pUnpackedName, @Nullable String pUnpackedGroup) {
        ShapelessRecipeBuilder.shapeless(pUnpackedCategory, pUnpacked, 9).requires(pPacked).group(pUnpackedGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pFinishedRecipeConsumer, new ResourceLocation(YummyMod.MOD_ID, pUnpackedName));
        ShapedRecipeBuilder.shaped(pPackedCategory, pPacked).define('#', pUnpacked).pattern("###").pattern("###").pattern("###").group(pPackedGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, new ResourceLocation(YummyMod.MOD_ID, pPackedName));
    }

    protected static void essenceSmithing(Consumer<FinishedRecipe> consumer, Item ingredientItem, Item additionItem, RecipeCategory category, Item resultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ingredientItem), Ingredient.of(additionItem), category, resultItem).unlocks("has_essence", has(additionItem)).save(consumer, new ResourceLocation(YummyMod.MOD_ID, getItemName(resultItem) + "_smithing"));
    }
}
