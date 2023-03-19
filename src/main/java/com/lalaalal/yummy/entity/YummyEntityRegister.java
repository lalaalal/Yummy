package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyEntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, YummyMod.MOD_ID);

    public static final RegistryObject<EntityType<HerobrineEntity>> HEROBRINE = ENTITIES.register("herobrine",
            () -> EntityType.Builder.of(HerobrineEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 2.0f)
                    .fireImmune()
                    .build(YummyMod.MOD_ID + ":herobrine"));
}
