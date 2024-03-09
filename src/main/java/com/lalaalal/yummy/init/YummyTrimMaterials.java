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
import org.checkerframework.checker.units.qual.A;


import java.util.Map;

public class YummyTrimMaterials {
    public static final ResourceKey<TrimMaterial> REDSTONE = registerKey("soul_infused_redstone");
    public static final ResourceKey<TrimMaterial> COPPER = registerKey("soul_infused_copper");
    public static final ResourceKey<TrimMaterial> GOLD = registerKey("soul_infused_gold");
    public static final ResourceKey<TrimMaterial> EMERALD = registerKey("soul_infused_emerald");
    public static final ResourceKey<TrimMaterial> DIAMOND = registerKey("soul_infused_diamond");
    public static final ResourceKey<TrimMaterial> LAPIS = registerKey("soul_infused_lapis");
    public static final ResourceKey<TrimMaterial> AMETHYST = registerKey("soul_infused_amethyst");
    public static final ResourceKey<TrimMaterial> FANCY_DIAMOND = registerKey("soul_infused_fancy_diamond");
    private static ResourceKey<TrimMaterial> registerKey(String name) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(YummyMod.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<TrimMaterial> context){
        register(context, REDSTONE, YummyItems.SOUL_INFUSED_REDSTONE.getHolder().get(), Style.EMPTY.withColor(11823181), 0.5F);
        register(context, COPPER, YummyItems.SOUL_INFUSED_COPPER.getHolder().get(), Style.EMPTY.withColor(14594349), 0.6F);
        register(context, GOLD, YummyItems.SOUL_INFUSED_GOLD.getHolder().get(), Style.EMPTY.withColor(9901575), 0.4F);
        register(context, EMERALD, YummyItems.SOUL_INFUSED_EMERALD.getHolder().get(), Style.EMPTY.withColor(7269586), 0.8F);
        register(context, DIAMOND, YummyItems.SOUL_INFUSED_DIAMOND.getHolder().get(), Style.EMPTY.withColor(1155126), 0.7F);
        register(context, LAPIS, YummyItems.SOUL_INFUSED_LAPIS.getHolder().get(), Style.EMPTY.withColor(10116294), 1.0F);
        register(context, AMETHYST, YummyItems.SOUL_INFUSED_AMETHYST.getHolder().get(), Style.EMPTY.withColor(4288151), 0.9F);
        register(context, FANCY_DIAMOND, YummyItems.SOUL_INFUSED_FANCY_DIAMOND.getHolder().get(), Style.EMPTY.withColor(15527148), 0.2F);

    }


    private static void register(BootstapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Holder<Item> trimItem, Style color, float itemModelIndex) {
        TrimMaterial material = new TrimMaterial(trimKey.location().getPath(), trimItem, itemModelIndex, Map.of(), Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(color));
        context.register(trimKey, material);
    }
}

