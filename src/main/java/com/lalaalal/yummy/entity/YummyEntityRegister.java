package com.lalaalal.yummy.entity;

import com.lalaalal.yummy.YummyMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class YummyEntityRegister {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, YummyMod.MOD_ID);

    public static final RegistryObject<EntityType<Herobrine>> HEROBRINE = ENTITY_TYPES.register("herobrine",
            () -> EntityType.Builder.of(Herobrine::new, MobCategory.CREATURE)
                    .sized(0.6f, 2.0f)
                    .fireImmune()
                    .build(YummyMod.MOD_ID + ":herobrine"));

    public static final RegistryObject<EntityType<ShadowHerobrine>> SHADOW_HEROBRINE = ENTITY_TYPES.register("shadow_herobrine",
            () -> EntityType.Builder.of(ShadowHerobrine::new, MobCategory.CREATURE)
                    .sized(0.6f, 2.0f)
                    .fireImmune()
                    .build(YummyMod.MOD_ID + ":shadow_herobrine"));


    public static final RegistryObject<EntityType<ThrownSpearOfLonginus>> THROWN_SPEAR_OF_LONGINUS = ENTITY_TYPES.register("thrown_spear_of_longinus",
            () -> EntityType.Builder.of((EntityType.EntityFactory<ThrownSpearOfLonginus>) ThrownSpearOfLonginus::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .fireImmune()
                    .build(YummyMod.MOD_ID + ":thrown_spear_of_longinus"));

    public static final RegistryObject<EntityType<MarkFireball>> MARK_FIREBALL = ENTITY_TYPES.register("mark_fireball",
            () -> EntityType.Builder.of((EntityType.EntityFactory<MarkFireball>) MarkFireball::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .build(YummyMod.MOD_ID + ":mark_fireball"));

    public static final RegistryObject<EntityType<Meteor>> METEOR = ENTITY_TYPES.register("meteor",
            () -> EntityType.Builder.of((EntityType.EntityFactory<Meteor>) Meteor::new, MobCategory.MISC)
                    .sized(2f, 2f)
                    .build(YummyMod.MOD_ID + ":meteor"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
