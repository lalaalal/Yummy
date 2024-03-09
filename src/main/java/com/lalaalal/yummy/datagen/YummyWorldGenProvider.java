package com.lalaalal.yummy.datagen;

import com.lalaalal.yummy.YummyMod;
import com.lalaalal.yummy.init.YummyTrimMaterials;
import com.lalaalal.yummy.world.damagesource.YummyDamageTypes;
import com.lalaalal.yummy.world.feature.YummyConfiguredFeatures;
import com.lalaalal.yummy.world.feature.YummyPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import org.openjdk.nashorn.internal.runtime.linker.Bootstrap;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class YummyWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, YummyConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, YummyPlacedFeatures::bootstrap)
            .add(Registries.DAMAGE_TYPE, YummyDamageTypes::bootstrap)
            .add(Registries.TRIM_MATERIAL, YummyTrimMaterials::bootstrap);

    public YummyWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of("minecraft", YummyMod.MOD_ID));
    }
}
