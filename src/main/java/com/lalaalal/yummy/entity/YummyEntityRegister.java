package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyEntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITIES
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, YummyMod.MOD_ID);

    public static final RegistryObject<EntityType<Herobrine>> HEROBRINE = ENTITIES.register("herobrine",
            () -> EntityType.Builder.of(Herobrine::new, MobCategory.CREATURE)
                    .sized(1.0f, 2.0f)
                    .fireImmune()
                    .build(YummyMod.MOD_ID + ":herobrine"));

    public static final RegistryObject<EntityType<ThrownSpearOfLonginus>> THROWN_SPEAR_OF_LONGINUS = ENTITIES.register("thrown_spear_of_longinus",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ThrownSpearOfLonginus>) ThrownSpearOfLonginus::new, MobCategory.MISC)
                    .fireImmune()
                    .build(YummyMod.MOD_ID + ":thrown_spear_of_longinus"));
}
