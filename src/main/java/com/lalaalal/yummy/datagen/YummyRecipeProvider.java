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
        soulinfusedSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.SOUL_INFUSED_REDSTONE.get(), RecipeCategory.MISC, YummyItems.SOUL_INFUSED_REDSTONE_SWORD.get());
        soulinfusedSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.SOUL_INFUSED_COPPER.get(), RecipeCategory.MISC, YummyItems.SOUL_INFUSED_COPPER_SWORD.get());
        soulinfusedSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.SOUL_INFUSED_GOLD.get(), RecipeCategory.MISC, YummyItems.SOUL_INFUSED_GOLDEN_SWORD.get());
        soulinfusedSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.SOUL_INFUSED_EMERALD.get(), RecipeCategory.MISC, YummyItems.SOUL_INFUSED_EMERALD_SWORD.get());
        soulinfusedSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.SOUL_INFUSED_DIAMOND.get(), RecipeCategory.MISC, YummyItems.SOUL_INFUSED_DIAMOND_SWORD.get());
        soulinfusedSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.SOUL_INFUSED_LAPIS.get(), RecipeCategory.MISC, YummyItems.SOUL_INFUSED_LAPIS_SWORD.get());
        soulinfusedSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.SOUL_INFUSED_AMETHYST.get(), RecipeCategory.MISC, YummyItems.SOUL_INFUSED_AMETHYST_SWORD.get());
        soulinfusedSmithing(consumer, YummyItems.PURIFIED_SOUL_SWORD.get(), YummyItems.SOUL_INFUSED_FANCY_DIAMOND.get(), RecipeCategory.MISC, YummyItems.SOUL_INFUSED_FANCY_DIAMOND_SWORD.get());
        planksFromLog(consumer, YummyBlocks.EBONY_PLANKS.get(), YummyTags.EBONY_LOGS_ITEM_TAG, 4);
        woodFromLogs(consumer, YummyBlocks.EBONY_WOOD.get(), YummyBlocks.EBONY_LOG.get());
        woodenBoat(consumer, YummyItems.EBONY_BOAT_ITEM.get(), YummyBlocks.EBONY_PLANKS.get());
        chestBoat(consumer, YummyItems.EBONY_CHEST_BOAT_ITEM.get(), YummyItems.EBONY_BOAT_ITEM.get());
        hangingSign(consumer, YummyBlocks.EBONY_HANGING_SIGN.get(), YummyBlocks.STRIPPED_EBONY_LOG.get());
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked) {
        nineBlockStorageRecipes(pFinishedRecipeConsumer, pUnpackedCategory, pUnpacked, pPackedCategory, pPacked, getSimpleRecipeName(pPacked), null, getSimpleRecipeName(pUnpacked), null);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeCategory pUnpackedCategory, ItemLike pUnpacked, RecipeCategory pPackedCategory, ItemLike pPacked, String pPackedName, @Nullable String pPackedGroup, String pUnpackedName, @Nullable String pUnpackedGroup) {
        ShapelessRecipeBuilder.shapeless(pUnpackedCategory, pUnpacked, 9).requires(pPacked).group(pUnpackedGroup).unlockedBy(getHasName(pPacked), has(pPacked)).save(pFinishedRecipeConsumer, new ResourceLocation(YummyMod.MOD_ID, pUnpackedName));
        ShapedRecipeBuilder.shaped(pPackedCategory, pPacked).define('#', pUnpacked).pattern("###").pattern("###").pattern("###").group(pPackedGroup).unlockedBy(getHasName(pUnpacked), has(pUnpacked)).save(pFinishedRecipeConsumer, new ResourceLocation(YummyMod.MOD_ID, pPackedName));
    }

    protected static void soulinfusedSmithing(Consumer<FinishedRecipe> consumer, Item ingredientItem, Item additionItem, RecipeCategory category, Item resultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(ingredientItem), Ingredient.of(additionItem), category, resultItem).unlocks("has_essence", has(additionItem)).save(consumer, new ResourceLocation(YummyMod.MOD_ID, getItemName(resultItem) + "_smithing"));
    }
}
