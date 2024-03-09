package com.lalaalal.yummy.datagen;

import com.lalaalal.yummy.block.YummyBlocks;
import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class YummyBlockLootTables extends BlockLootSubProvider {

    protected YummyBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(YummyBlocks.SOUL_CRAFTER.get());
        add(YummyBlocks.DEEPSLATE_FANCY_DIAMOND_ORE.get(), block -> createOreDrop(block, YummyItems.FANCY_DIAMOND.get()));
        dropSelf(YummyBlocks.EBONY_BUTTON.get());
        add(YummyBlocks.EBONY_DOOR.get(), this::createDoorTable);
        dropSelf(YummyBlocks.EBONY_FENCE.get());
        dropSelf(YummyBlocks.EBONY_FENCE_GATE.get());
        add(YummyBlocks.EBONY_LEAVES.get(), silkAndFruit(YummyBlocks.EBONY_LEAVES.get(), YummyBlocks.EBONY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        dropSelf(YummyBlocks.EBONY_LOG.get());
        dropSelf(YummyBlocks.EBONY_PLANKS.get());
        dropSelf(YummyBlocks.EBONY_PRESSURE_PLATE.get());
        dropSelf(YummyBlocks.EBONY_SAPLING.get());
        dropOther(YummyBlocks.EBONY_SIGN.get(), YummyItems.EBONY_SIGN.get());
        dropSelf(YummyBlocks.EBONY_SLAB.get());
        dropSelf(YummyBlocks.EBONY_STAIRS.get());
        dropSelf(YummyBlocks.EBONY_TRAPDOOR.get());
        dropOther(YummyBlocks.EBONY_WALL_SIGN.get(), YummyItems.EBONY_SIGN.get());
        dropSelf(YummyBlocks.EBONY_WOOD.get());
        dropSelf(YummyBlocks.HARD_EBONY_PLANKS.get());
        dropSelf(YummyBlocks.PURIFIED_SOUL_BLOCK.get());
        dropSelf(YummyBlocks.FANCY_DIAMOND_BLOCK.get());
        dropSelf(YummyBlocks.FAKE_GOLD_BLOCK.get());
        dropSelf(YummyBlocks.SOUL_INFUSED_REDSTONE_BLOCK.get());
        dropSelf(YummyBlocks.SOUL_INFUSED_COPPER_BLOCK.get());
        dropSelf(YummyBlocks.SOUL_INFUSED_GOLD_BLOCK.get());
        dropSelf(YummyBlocks.SOUL_INFUSED_EMERALD_BLOCK.get());
        dropSelf(YummyBlocks.SOUL_INFUSED_DIAMOND_BLOCK.get());
        dropSelf(YummyBlocks.SOUL_INFUSED_LAPIS_BLOCK.get());
        dropSelf(YummyBlocks.SOUL_INFUSED_AMETHYST_BLOCK.get());
        dropSelf(YummyBlocks.SOUL_INFUSED_FANCY_DIAMOND_BLOCK.get());
        dropSelf(YummyBlocks.PURIFIED_SOUL_METAL_BLOCK.get());
        add(YummyBlocks.FANCY_DIAMOND_ORE.get(), block -> createOreDrop(block, YummyItems.FANCY_DIAMOND.get()));
        dropSelf(YummyBlocks.STRIPPED_EBONY_LOG.get());
        dropSelf(YummyBlocks.STRIPPED_EBONY_WOOD.get());
        dropOther(YummyBlocks.EBONY_HANGING_SIGN.get(), YummyItems.EBONY_HANGING_SIGN.get());
        dropOther(YummyBlocks.EBONY_WALL_HANGING_SIGN.get(), YummyItems.EBONY_HANGING_SIGN.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return YummyBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    protected static LootTable.Builder localcreateSilkTouchOrShearsDispatchTable(Block block, LootPoolEntryContainer.Builder<?> builder) {
        return createSelfDropDispatchTable(block, HAS_SHEARS.or(HAS_SILK_TOUCH), builder);
    }
    private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS));
    protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(
            ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
    );
    private LootTable.Builder silkAndFruit(Block block, ItemLike nonSilk, float... nonSilkFortune) {
        return localcreateSilkTouchOrShearsDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(nonSilk.asItem()))
                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, nonSilkFortune))).withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1)).when((HAS_SHEARS.or(HAS_SILK_TOUCH)).invert())
                .add(applyExplosionDecay(block, LootItem.lootTableItem(YummyItems.EBONY_FRUIT.get()).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition
                        .bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }
}

/*
block -> createLeavesDrops(block, YummyBlocks.EBONY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(this.applyExplosionCondition(block, LootItem.lootTableItem(YummyItems.EBONY_FRUIT.get())).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F)))));
 */