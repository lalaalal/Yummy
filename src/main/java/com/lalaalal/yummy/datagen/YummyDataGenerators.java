package com.lalaalal.yummy.datagen;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.tags.YummyBlockTags;
import com.lalaalal.yummy.tags.YummyItemTags;
import com.lalaalal.yummy.tags.YummyTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = YummyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class YummyDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = event.getGenerator().getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        YummyBlockTags blocktags = new YummyBlockTags(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blocktags);
        generator.addProvider(true, new YummyItemTags(packOutput, lookupProvider, blocktags.contentsGetter(), existingFileHelper));
        generator.addProvider(true, new YummyRecipeProvider(packOutput));
        generator.addProvider(true, YummyLootTableProvider.create(packOutput));
        generator.addProvider(true, new YummyBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new YummyItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new YummyWorldGenProvider(packOutput, lookupProvider));
    }
}
