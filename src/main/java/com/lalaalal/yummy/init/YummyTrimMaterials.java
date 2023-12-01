package com.lalaalal.yummy.init;

import com.lalaalal.yummy.item.YummyItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import com.lalaalal.yummy.YummyMod;


import java.util.Map;

public class YummyTrimMaterials {
    public static final ResourceKey<TrimMaterial> EARTH = registerKey("essence_of_earth");
    public static final ResourceKey<TrimMaterial> ELECTRICITY = registerKey("essence_of_electricity");
    public static final ResourceKey<TrimMaterial> FIRE = registerKey("essence_of_fire");
    public static final ResourceKey<TrimMaterial> ICE = registerKey("essence_of_ice");
    public static final ResourceKey<TrimMaterial> LIFE = registerKey("essence_of_life");
    public static final ResourceKey<TrimMaterial> SOUND = registerKey("essence_of_sound");
    public static final ResourceKey<TrimMaterial> WATER = registerKey("essence_of_water");
    public static final ResourceKey<TrimMaterial> WIND = registerKey("essence_of_wind");
    private static ResourceKey<TrimMaterial> registerKey(String name) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(YummyMod.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<TrimMaterial> context){
        register(context, EARTH, YummyItems.ESSENCE_OF_EARTH.getHolder().get(), Style.EMPTY.withColor(11823181), 0.5F);
        register(context, ELECTRICITY, YummyItems.ESSENCE_OF_ELECTRICITY.getHolder().get(), Style.EMPTY.withColor(14594349), 0.6F);
        register(context, FIRE, YummyItems.ESSENCE_OF_FIRE.getHolder().get(), Style.EMPTY.withColor(9901575), 0.4F);
        register(context, ICE, YummyItems.ESSENCE_OF_ICE.getHolder().get(), Style.EMPTY.withColor(7269586), 0.8F);
        register(context, LIFE, YummyItems.ESSENCE_OF_LIFE.getHolder().get(), Style.EMPTY.withColor(1155126), 0.7F);
        register(context, SOUND, YummyItems.ESSENCE_OF_SOUND.getHolder().get(), Style.EMPTY.withColor(10116294), 1.0F);
        register(context, WATER, YummyItems.ESSENCE_OF_WATER.getHolder().get(), Style.EMPTY.withColor(4288151), 0.9F);
        register(context, WIND, YummyItems.ESSENCE_OF_WIND.getHolder().get(), Style.EMPTY.withColor(15527148), 0.2F);

    }


    private static void register(BootstapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Holder<Item> trimItem, Style color, float itemModelIndex) {
        TrimMaterial material = new TrimMaterial(trimKey.location().getPath(), trimItem, itemModelIndex, Map.of(), Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(color));
        context.register(trimKey, material);
    }
}

